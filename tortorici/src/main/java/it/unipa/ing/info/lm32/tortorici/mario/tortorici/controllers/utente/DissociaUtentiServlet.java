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

@WebServlet(name = "DissociaUtentiServlet", value = "/dissocia-utenti")
public class DissociaUtentiServlet extends HttpServlet {

    public DissociaUtentiServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DBQueries dbQueries = new DBQueries();

        HttpSession httpSession = req.getSession(false);

        // SONO DAL DISTRIBUTORE E NON HO NESSUNA INFO SUL CLIENTE CHE VI E' CONNESSO

        // DALLA HTTP SESSION MI PRELEVO IL TIPO DI UTENTE

        Boolean isLoggedDistributore = (Boolean) httpSession.getAttribute("isLoggedDistributore");

        String message = "";


        try {

            if (isLoggedDistributore) {

                // Sto dissociando gli utenti tramite DISTRIBUTORE

                // NELLA HTTP SESSION HO SOLO L'OGGETTO DISTRIBUTORE
                // TRAMITE LA selectClienteFromAssociazione OTTENGO L'OGGETTO CLIENTE
                // E POI POSSO EFFETTUARE LA DISCONNESSIONE

                Distributore distributore = (Distributore) httpSession.getAttribute("utente");

                Cliente cliente = dbQueries.selectClienteFromAssociazione(distributore.getIdUtente());

                if(dbQueries.dissociaDispositivi(distributore.getIdUtente(), cliente.getIdUtente())) {

                    // La dissociazione è correttamente avvenuta e invio un messaggio per notificare il
                    // successo dell'operazione, aggiorno la http session del distributore e vado alla home

                    message =   "Dissociazione avvenuta correttamente!";

                    distributore.setStato(true);

                } else {

                    message = "Dissociazione negata. Riprova!";

                }

            } else {

                // Sto dissociando gli utenti tramite CLIENTE

                // NELLA HTTP SESSION HO SOLO L'OGGETTO CLIENTE
                // TRAMITE LA selectClienteFromAssociazione OTTENGO L'OGGETTO DISTRIBUTORE
                // E POI POSSO EFFETTUARE LA DISCONNESSIONE

                Cliente cliente = (Cliente) httpSession.getAttribute("utente");

                int idDistributore = Integer.parseInt(httpSession.getAttribute("idDistributore").toString());

                Distributore distributore = dbQueries.selectDistributoreById(idDistributore);

                // Controllo che gli stati di cliente e distributore siano entrambi occupati

                if(dbQueries.dissociaDispositivi(distributore.getIdUtente(), cliente.getIdUtente())) {

                    // La dissociazione è correttamente avvenuta e invio un messaggio per notificare il
                    // successo dell'operazione, aggiorno la http session del cliente e vado alla home

                    message =   "Dissociazione avvenuta correttamente!";

                    cliente.setStato(true);

                    httpSession.removeAttribute("idDistributore");


                } else {

                    message = "Dissociazione negata. Riprova!";

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
