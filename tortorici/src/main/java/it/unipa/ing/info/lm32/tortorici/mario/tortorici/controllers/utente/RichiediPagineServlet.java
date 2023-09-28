package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.utente;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RichiediPagineServlet", value = "/richiedi-pagine")
public class RichiediPagineServlet extends HttpServlet {

    public RichiediPagineServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String pagina = req.getParameter("content");

        switch (pagina) {

            case "associaDistributore.jsp":
            case "homeCliente.jsp":
            case "modificaPassword.jsp":
            case "ordiniEffettuati.jsp":
            case "ricaricaWallet.jsp":

                req.getRequestDispatcher("/WEB-INF/view/private/cliente/" +pagina).forward(req, res);

                break;

            case "elencoBevande.jsp":
            case "elencoSnacks.jsp":
            case "erogazioneProdotto.jsp":
            case "homeDistributore.jsp":
            case "prodottoErogato.jsp":
            case "sceltaProdotto.jsp":

                req.getRequestDispatcher("/WEB-INF/view/private/distributore/" +pagina).forward(req, res);

                break;

            default:

                req.getRequestDispatcher("/WEB-INF/view/public/" +pagina).forward(req, res);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}
}
