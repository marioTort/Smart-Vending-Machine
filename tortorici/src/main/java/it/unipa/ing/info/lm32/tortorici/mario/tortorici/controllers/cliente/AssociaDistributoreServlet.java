package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.cliente;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.*;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Distributore;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AssociaDistributoreServlet", value = "/associa-distributore")
public class AssociaDistributoreServlet extends HttpServlet {

    public AssociaDistributoreServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, NumberFormatException {

        DBQueries dbQueries = new DBQueries();

        HttpSession httpSession = req.getSession(false);

        // Dal form prelevo l'id del distributore a cui il cliente vuole collegarsi
        // Poiché l'id è un intero, uso il metodo Integer.parseInt() che mi converte la stringa nel tipo primitivo int.
        // Se la stringa non contiene un numero intero valido, allora l'eccezione NumberFormatException verrà lanciata.

        int idDistributore = Integer.parseInt(req.getParameter("idDistributore"));

        // Dalla http session recupero anche gli attributi del cliente che vuole collegarsi al distributore
        // ed effettuo un opportuno casting per ottenere l'oggetto "Cliente"

        Cliente cliente = (Cliente) httpSession.getAttribute("utente");

        String  message = "",
                jsp = "";



        try {

            Distributore distributore = dbQueries.selectDistributoreById(idDistributore);

            // Controllo che gli stati di cliente e distributore siano entrambi disponibili

            if(cliente.getStato() && distributore.getStato()) {

                // Il controllo ha dato esito positivo: cambio lo stato del cliente

                if(dbQueries.associaDispositivi(idDistributore, cliente.getIdUtente())) {

                    message =   "Associazione avvenuta correttamente. Puoi procedere all'ordine usando l'interfaccia " +
                                "del distributore!";

                    cliente.setStato(false);

                    httpSession.setAttribute("idDistributore", idDistributore);

                    jsp = "homeCliente.jsp";

                }

            } else {

                message =   "Associazione negata. Verifica di non essere associato ad un altro distributore" +
                            " o che il distributore selezionato sia disponibile!";

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
