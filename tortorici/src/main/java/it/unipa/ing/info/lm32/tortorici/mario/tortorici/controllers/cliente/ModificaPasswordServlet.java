package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.cliente;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils.SHA256;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ModificaPasswordServlet", value = "/modifica-password")
public class ModificaPasswordServlet extends HttpServlet {
    private static final String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    public ModificaPasswordServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Dal form prelevo la vecchia e la nuova password inserite del cliente

        String vecchiaPassword = req.getParameter("vecchiaPassword");
        String nuovaPassword = req.getParameter("nuovaPassword");

        // Fornisco un pattern specifico per la nuova password

        Pattern patternPassword = Pattern.compile(regexPassword);

        // Verifico che tale formato venga rispettato e, in caso positivo, procedo

        Matcher passwordMatcher = patternPassword.matcher(nuovaPassword);

        Boolean nuovaPasswordValida = passwordMatcher.find();

        HttpSession httpSession = req.getSession(false);

        // Dalla http session anche gli attributi del cliente ed effettuo un opportuno casting
        // per ottenere l'oggetto "Cliente"

        Cliente cliente = (Cliente) httpSession.getAttribute("utente");

        String  message = "",
                jsp = "";



        DBQueries dbQueries = new DBQueries();

        // Controllo che la nuova password rispetti il formato richiesto. Altrimenti invio un messaggio di errore.

        try {

            if(nuovaPasswordValida) {

                // Controllo se la vecchia password e la nuova coincidono e, in tal caso, invio un messaggio di errore.
                // Altrimenti, vado avanti nella procedura

                //String vecchiaPasswordFromDB = dbQueries

                if(vecchiaPassword.equals(nuovaPassword)) {

                    message = "La nuova password e la vecchia non possono coincidere. Riprova!";

                } else if(dbQueries.updatePasswordUtente(cliente.getIdUtente(), SHA256.getHash(vecchiaPassword), SHA256.getHash(nuovaPassword))){

                    message = "Password aggiornata correttamente. Stai per essere disconnesso per accedere con le nuove credenziali!";
                    httpSession.invalidate();
                    jsp = "loginForm.jsp";

                } else {

                    message = "Errore. Riprova!";

                }

            } else {

                message = "La nuova password non rispetta il formato richiesto. Riprova!";

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
