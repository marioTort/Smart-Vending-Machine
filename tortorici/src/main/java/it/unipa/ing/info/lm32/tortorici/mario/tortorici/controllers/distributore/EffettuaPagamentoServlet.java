package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.distributore;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Distributore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;



@WebServlet(name = "EffettuaPagamentoServlet", value = "/effettua-pagamento")
public class EffettuaPagamentoServlet extends HttpServlet {

    public EffettuaPagamentoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DBQueries dbQueries = new DBQueries();

        HttpSession httpSession = req.getSession(false);

        String  message = "",
                jsp = "";

        Distributore distributore = (Distributore) httpSession.getAttribute("utente");

        int idProdotto = Integer.parseInt(req.getParameter("idProdotto"));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataOrdine = simpleDateFormat.format(new Date());

        try {

            double prezzoProdotto = dbQueries.selectPrezzoProdotto(idProdotto);

            if(prezzoProdotto > -1) {

                if(distributore.getStato()) {

                    // Il CLIENTE (non autenticato) ha selezionato il pagamento in contanti, in quanto tale tipologia
                    // è ammessa solo quando lo stato del distributore è true

                    double creditoInserito = Double.parseDouble(req.getParameter("creditoInserito"));

                    double resto = creditoInserito - prezzoProdotto;

                    if(resto >= 0) {

                        dbQueries.effettuaOrdine(dataOrdine, null, distributore.getIdUtente(), idProdotto, prezzoProdotto);

                        message = "Pagamento effettuato con successo. Verrà erogato un resto pari a " +resto +" €!";

                        jsp = "erogazioneProdotto.jsp";

                    } else {

                        // Se il credito è insufficiente, verrà mostrato a schermo un messaggio di errore

                        message = "Credito inserito insufficiente. Inserisci altri " +(resto*-1) +" € per ordinare!";

                    }

                } else {

                    // Il CLIENTE ha selezionato il pagamento con wallet, in quanto tale tipologia è ammessa
                    // solo quando lo stato del distributore è false

                    Cliente cliente = dbQueries.selectClienteFromAssociazione(distributore.getIdUtente());

                    if(cliente.getSaldoDisponibile() >= prezzoProdotto) {

                        double nuovoSaldo = cliente.getSaldoDisponibile() - prezzoProdotto;

                        if(dbQueries.updateSaldoDisponibile(cliente.getIdUtente(), nuovoSaldo) && dbQueries.effettuaOrdine(dataOrdine, cliente.getEmail(), distributore.getIdUtente(), idProdotto, prezzoProdotto)) {

                            message = "Pagamento effettuato con successo. Il tuo saldo disponibile è di " +nuovoSaldo +" €!";

                            cliente.setSaldoDisponibile(nuovoSaldo);

                            jsp = "erogazioneProdotto.jsp";

                        }
                    } else {

                        message = "Saldo disponibile insufficiente. Effettua una ricarica per ordinare!";

                    }

                }

            } else {

                message = "Prodotto non presente o non disponibile. Prova con un altro prodotto!";

            }

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        // Setto il MIME type e scrivo il messaggio nel buffer

        res.setContentType("text/plain");
        res.getWriter().write(message);
        res.getWriter().write(jsp);

        // Ripulisco il buffer

        res.getWriter().flush();

    }
}
