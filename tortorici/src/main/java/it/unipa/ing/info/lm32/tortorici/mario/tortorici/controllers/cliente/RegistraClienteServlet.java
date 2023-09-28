package it.unipa.ing.info.lm32.tortorici.mario.tortorici.controllers.cliente;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils.SHA256;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "RegistraClienteServlet", value = "/registra-cliente")
public class RegistraClienteServlet extends HttpServlet {
    private static final String regexEmail = "^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$",
                                regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",     //password di almeno 8 caratteri, di cui almeno 1 maiuscolo e almeno 1 numero
                                regexData = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";    //yyyy-mm-dd

    public RegistraClienteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String percorso = "/WEB-INF/view/public/registerForm.jsp";
        req.getRequestDispatcher(percorso).forward(req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Effettuo la raccolta dei parametri provenienti dal form di iscrizione

        String
                nome = req.getParameter("nome"),
                cognome = req.getParameter("cognome"),
                dataNascita = req.getParameter("dataNascita"),
                email = req.getParameter("email"),
                password = req.getParameter("password"),
                message = "",
                jsp="";

        // Fornisco un pattern specifico per email, password e data

        Pattern
                patternEmail = Pattern.compile(regexEmail),
                patternPassword = Pattern.compile(regexPassword),
                patternData = Pattern.compile(regexData);

        // Verifico che le stringhe rispettino tale formato e, in caso positivo, procedo alla registrazione

        Matcher
                emailMatcher = patternEmail.matcher(email),
                passwordMatcher = patternPassword.matcher(password),
                dataMatcher = patternData.matcher(dataNascita);

        Boolean
                emailValida = emailMatcher.find(),
                passwordValida = passwordMatcher.find(),
                dataValida = dataMatcher.find();

        DBQueries dbQueries = new DBQueries();

        try {

            if (emailValida && passwordValida && dataValida && dbQueries.signIn(email, SHA256.getHash(password), nome, cognome, dataNascita)) {

                // Se i formati sono validi e non si creano eccezioni nella procedura di registrazione,
                // essa avverrà con successo ed il CLIENTE verrà ricondotto alla pagina di login

                message = "Registrazione avvenuta con successo. Stai per essere reindirizzato alla pagina di login!";
                jsp = "loginForm.jsp";


            } else {

                // Altrimenti il CLIENTE verrà ricondotto alla pagina di registrazione

                message = "Errore durante la registrazione. Riprova!";

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
