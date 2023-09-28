package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.cliente;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "RicaricaWalletServlet", value = "/ricarica-wallet")
public class RicaricaWalletServlet extends HttpServlet {

    private static final String regexNumeroCarta = "^[1-9]{16}$",
                                regexCvv = "^[1-9]{3}$";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, NumberFormatException {

        // Effettuo la raccolta dei parametri provenienti dal form

        String
                numeroCarta = req.getParameter("numeroCarta"),
                cvv = req.getParameter("cvv"),
                importo = req.getParameter("importoRicarica"),
                percorso;

        Cliente cliente = (Cliente) req.getSession(false).getAttribute("utente");

        double importoRicarica = Double.parseDouble(importo);

        double nuovoSaldo = cliente.getSaldoDisponibile() + importoRicarica;

        // Fornisco un pattern specifico per numeroCarta, cvv

        Pattern
                patternNumeroCarta = Pattern.compile(regexNumeroCarta),
                patternCvv = Pattern.compile(regexCvv);

        // Verifico che le stringhe rispettino tale formato e, in caso positivo, effettuo la ricarica

        Matcher
                numeroCartaMatcher = patternNumeroCarta.matcher(numeroCarta),
                cvvMatcher = patternCvv.matcher(cvv);

        Boolean
                numeroCartaValido = numeroCartaMatcher.find(),
                cvvValido = cvvMatcher.find();

        DBQueries dbQueries = new DBQueries();

        String  message = "",
                jsp = "";

        try {

            if(numeroCartaValido && cvvValido && dbQueries.updateSaldoDisponibile(cliente.getIdUtente(), nuovoSaldo)) {

                // La ricarica è correttamente avvenuta. Aggiorno il saldo disponibile.
                // Invio un messaggio per notificare il successo dell'operazione e riconduco
                // il CLIENTE alla propria home riservata.

                cliente.setSaldoDisponibile(nuovoSaldo);

                message = "Ricarica avvenuta con successo!";
                jsp = "homeCliente.jsp";

            } else {

                // C'è stato un errore durante l'operazione di ricarica.
                // Invio un messaggio per notificare l'avvenuto

                message = "Errore durante la ricarica. Riprova!";

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
