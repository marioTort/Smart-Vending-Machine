package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.utente;

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

@WebServlet(name = "StatoUtenteServlet", value = "/stato-utente")
public class StatoUtenteServlet extends HttpServlet {

    public StatoUtenteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String message = "";

        HttpSession httpSession = req.getSession(false);

        DBQueries dbQueries = new DBQueries();

        // SONO DAL DISTRIBUTORE E NON HO NESSUNA INFO SUL CLIENTE CHE VI E' CONNESSO

        // DALLA HTTP SESSION MI PRELEVO IL TIPO DI UTENTE

        boolean isLoggedDistributore = (Boolean) httpSession.getAttribute("isLoggedDistributore");

        try {

            if (isLoggedDistributore) {

                Distributore distributore = (Distributore) httpSession.getAttribute("utente");

                boolean stato = dbQueries.selectStatoUtente(distributore.getIdUtente());

                distributore.setStato(stato);

                if(stato) {

                    message = "true";

                } else {

                    message = "false";

                }

            } else {

                Cliente cliente = (Cliente) httpSession.getAttribute("utente");

                boolean stato = dbQueries.selectStatoUtente(cliente.getIdUtente());

                double vecchioSaldo = cliente.getSaldoDisponibile();

                double saldo = dbQueries.selectSaldoCliente(cliente.getIdUtente());

                cliente.setStato(stato);
                cliente.setSaldoDisponibile(saldo);

                if(stato) {

                    message = "true";

                    httpSession.removeAttribute("idDistributore");

                } else if(vecchioSaldo > saldo){

                    message = "true";

                } else {

                    message = "false";

                }

            }

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

        // Setto il MIME type e scrivo il messaggio nel buffer

        res.setContentType("text/plain");
        res.getWriter().write(message);

        // Ripulisco il buffer

        res.getWriter().flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

}