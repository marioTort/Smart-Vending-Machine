package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.utente;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutUtenteServlet", value = "/logout")
public class LogoutUtenteServlet extends HttpServlet {

    // Costruttore

    public LogoutUtenteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);

        httpSession.invalidate();

        String  message = "Logout avvenuto con successo. Stai per essere indirizzato alla pagina di login!",
                jsp = "loginForm.jsp";

        // Setto il MIME type e scrivo il messaggio nel buffer

        res.setContentType("text/plain");
        res.getWriter().write(message);
        res.getWriter().write(jsp);

        // Ripulisco il buffer

        res.getWriter().flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
