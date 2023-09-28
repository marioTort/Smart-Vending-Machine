package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.utente;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.*;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Utente;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginUtenteServlet", value = "/login")
public class LoginUtenteServlet extends HttpServlet {

    public LoginUtenteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("risposta", "Errore durante il login. Riprova.");

        String percorso = "/WEB-INF/view/public/loginForm.jsp";

        req.getRequestDispatcher(percorso).forward(req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String  email = req.getParameter("email"),
                password = req.getParameter("password"),
                message = "",
                jsp = "";

        // Connessione al DB e verifica effettiva presenza dell'utente nel DB

        DBQueries dbQueries = new DBQueries();

        HttpSession httpSession = req.getSession(false);

        try {

            Utente utente = dbQueries.login(email, SHA256.getHash(password));

            if(utente != null) {

                httpSession.setAttribute("isLogged", true);
                httpSession.setAttribute("utente", utente);

                switch (utente.getTipoUtente()) {

                    case 0:

                        // L'utente loggato è un DISTRIBUTORE e viene reindirizzato alla propria home dedicata

                        httpSession.setAttribute("isLoggedDistributore", true);

                        message = "Login Distributore avvenuto con successo!";
                        jsp = "homeDistributore.jsp";

                        break;

                    case 1:

                        // L'utente loggato è un CLIENTE e viene reindirizzato alla propria home dedicata

                        httpSession.setAttribute("isLoggedDistributore", false);

                        message = "Login Cliente avvenuto con successo!";
                        jsp = "homeCliente.jsp";

                        break;

                    default:

                        // Il tipo di utente non è ammesso. Reindirizza l'utente alla pagina di errore

                        httpSession.setAttribute("isLogged", false);

                        message = "Tipo utente non ammesso!";
                        jsp = "loginForm.jsp";

                }

            } else {

                httpSession.setAttribute("isLogged", false);

                message = "Errore immissione credenziali!";
                jsp = "loginForm.jsp";

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