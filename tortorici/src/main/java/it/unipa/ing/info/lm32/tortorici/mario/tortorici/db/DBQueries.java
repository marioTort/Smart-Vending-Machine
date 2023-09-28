package it.unipa.ing.info.lm32.tortorici.mario.tortorici.db;

import it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.*;
import it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils.ConversioniData;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DBQueries {
    private ConversioniData conversioniData;
    private DBConnector dbConnector;

    // Costruttore
    public DBQueries() {

        dbConnector = new DBConnector();

        conversioniData = new ConversioniData();

    }

    // *************************************** S E L E C T ***************************************

    // * * * * * * * * * * * * SELECT Utente * * * * * * * * * * * *
    public Utente selectUtenteByCredenziali(String email, String password) throws SQLException {

        String query = "SELECT * FROM utenti WHERE email = ? AND passw = ?";

        Utente utente = new Utente();

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    utente.setId(resultSet.getInt(1));
                    utente.setTipoUtente(resultSet.getInt(2));
                    utente.setEmail(resultSet.getString(3));
                    utente.setStato(resultSet.getBoolean(5));

                } else {

                    utente = null;

                }

            }

        }

        return utente;

    }

    public boolean selectUtenteRegistratoByEmail(String email) throws SQLException {

        String query = "SELECT * FROM utenti WHERE email=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)

        ) {

            preparedStatement.setString(1, email);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    // L'utente è presente nel DB
                    return true;

                }

            }

        }

        // L'utente non è presente nel DB
        return false;

    }

    public boolean selectStatoUtente(int idUtente) throws SQLException {

        String query = "SELECT stato FROM utenti WHERE id_utente=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, idUtente);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                // Controllo che la query sia avvenuta correttamente e, in caso positivo, ritorno lo stato effettivo

                if (resultSet.next()) {

                    return resultSet.getBoolean(1);

                }

            }

        }

        return false;

    }

    // * * * * * * * * * * * * SELECT Cliente * * * * * * * * * * * *
    public Cliente selectClienteById(int refIdUtente) throws SQLException {

        String campi = "id_utente, tipo_utente, email, passw, stato, nome, cognome, data_di_nascita, saldo_disponibile";

        String query = "SELECT " + campi + " FROM utenti u, clienti c WHERE u.id_utente = c.ref_id_utente AND u.id_utente=?";

        Cliente cliente = new Cliente();

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)

        ) {

            preparedStatement.setInt(1, refIdUtente);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    cliente.setId(refIdUtente);
                    cliente.setTipoUtente(resultSet.getInt(2));
                    cliente.setEmail(resultSet.getString(3));
                    cliente.setPassword(resultSet.getString(4));
                    cliente.setStato(resultSet.getBoolean(5));
                    cliente.setNome(resultSet.getString(6));
                    cliente.setCognome(resultSet.getString(7));
                    cliente.setDataNascita(resultSet.getString(8));
                    cliente.setSaldoDisponibile(resultSet.getDouble(9));

                    cliente.setOrdiniEffettuati(selectOrdiniEffettuati(resultSet.getString(3)));

                } else {

                    cliente = null;

                }

            }

        }

        return cliente;
    }

    public Cliente selectClienteFromAssociazione(int refIdDistributore) throws SQLException {

        String query = "SELECT * FROM associazioni WHERE ref_id_distributore=? AND stato=true";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, refIdDistributore);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    int idUtente = resultSet.getInt(3);

                    Cliente clienteAssociato = selectClienteById(idUtente);

                    return clienteAssociato;

                }

            }

        }

        return null;

    }

    public double selectSaldoCliente(int idCliente) throws SQLException {

        String query = "SELECT saldo_disponibile FROM clienti WHERE ref_id_utente=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, idCliente);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                // Controllo che la query sia avvenuta correttamente e, in caso positivo, ritorno lo stato effettivo

                if (resultSet.next()) {

                    return resultSet.getDouble(1);

                }

            }

        }

        return -1;

    }

    public List<Ordine> selectOrdiniEffettuati(String refEmail) throws SQLException {

        List<Ordine> elencoOrdiniCliente = new LinkedList<>();

        String campi = "id_ordine, data_ordine, ref_id_distributore, ref_id_prodotto, importo";
        String query = "SELECT " + campi + " FROM ordini WHERE ref_email=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)

        ) {

            preparedStatement.setString(1, refEmail);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                while (resultSet.next()) {

                    Ordine ordine = new Ordine();

                    ordine.setIdOrdine(resultSet.getInt(1));
                    ordine.setDataOrdine(resultSet.getString(2));
                    ordine.setIdDistributore(resultSet.getInt(3));
                    ordine.setIdProdotto(resultSet.getInt(4));
                    ordine.setPrezzo(resultSet.getDouble(5));

                    elencoOrdiniCliente.add(ordine);

                }

            }

        }

        return elencoOrdiniCliente;

    }

    // * * * * * * * * * * * * SELECT Distributore * * * * * * * * * * * *
    public Distributore selectDistributoreById(int refIdUtente) throws SQLException {

        String campi = "id_utente, tipo_utente, email, passw, stato, posizione";

        String query = "SELECT " + campi + " FROM utenti u, distributori d WHERE u.id_utente = d.ref_id_utente AND u.id_utente=?";

        Distributore distributore = new Distributore();

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, refIdUtente);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    distributore.setId(refIdUtente);
                    distributore.setTipoUtente(resultSet.getInt(2));
                    distributore.setEmail(resultSet.getString(3));
                    distributore.setPassword(resultSet.getString(4));
                    distributore.setStato(resultSet.getBoolean(5));
                    distributore.setPosizione(resultSet.getString(6));
                    distributore.setProdottiInseriti(this.listaProdottiDistributore());

                } else {

                    distributore =  null;

                }

            }

        }

        return distributore;

    }

    public List<Prodotto> listaProdottiDistributore() throws SQLException {

        List<Prodotto> lista = new LinkedList<>();

        String query = "SELECT * FROM prodotti";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                while (resultSet.next()) {

                    // Finchè ci saranno elementi (prodotti) in resultSet creo l'oggetto prodotto, ne setto gli attributi
                    // e lo inserisco nella lista

                    Prodotto prodotto = new Prodotto();

                    prodotto.setIdProdotto(resultSet.getInt(1));
                    prodotto.setNome(resultSet.getString(2));
                    prodotto.setPrezzo(resultSet.getDouble(3));
                    prodotto.setTipo(resultSet.getInt(4));

                    lista.add(prodotto);

                }

            }

        }

        return lista;

    }

    public double selectPrezzoProdotto(int idProdotto) throws SQLException {

        String query = "SELECT prezzo_prodotto FROM prodotti WHERE id_prodotto=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, idProdotto);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                // Controllo che la query sia avvenuta correttamente e, in caso positivo, ritorno il prezzo effettivo

                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }

            }

        }

        return -1;
    }

    public List<Integer> selectIDDistributoriDisponibili() throws SQLException {

        List<Integer> list = new LinkedList<>();

        String query = "SELECT id_utente FROM utenti WHERE stato=true AND tipo_utente=0";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
            ) {

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                while (resultSet.next()) {

                    list.add(resultSet.getInt(1));

                }

            }

        }

        return list;
    }

    // * * * * * * * * * * * * SELECT Associazione * * * * * * * * * * * *
    public Associazione selectAssociazione(int refIdDistributore, int refIdCliente) throws SQLException {

        String query = "SELECT * FROM associazioni WHERE ref_id_distributore=? AND ref_id_cliente=? AND stato=true";

        Associazione associazione = new Associazione();

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setInt(1, refIdDistributore);
            preparedStatement.setInt(2, refIdCliente);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                ) {

                if (resultSet.next()) {

                    associazione.setIdAssociazione(resultSet.getInt(1));
                    associazione.setIdDistributore(refIdDistributore);
                    associazione.setIdCliente(refIdCliente);
                    associazione.setStato(resultSet.getBoolean(4));

                } else {

                    associazione = null;

                }

            }

        }

        return associazione;

    }

    // *************************************** I N S E R T ***************************************

    // * * * * * * * * * * * * INSERT Cliente * * * * * * * * * * * *
    public boolean insertCliente(String email, String password, String nome, String cognome, String dataNascita) throws SQLException {

        // FARE LA ROLLBACK

        String query1 = "INSERT INTO utenti(tipo_utente, email, passw, stato) VALUES (?,?,?,?)";
        String query2 = "INSERT INTO clienti VALUES (?,?,?,?,?,?)";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement insertUtenteStatement = dbConnection.prepareStatement(query1);
        ) {

            insertUtenteStatement.setInt(1, 1);            // Dico che l'utente è di tipo Cliente
            insertUtenteStatement.setString(2, email);
            insertUtenteStatement.setString(3, password);
            insertUtenteStatement.setBoolean(4, true);     // Setto lo stato a true(disponibile)

            insertUtenteStatement.executeUpdate();

            // Con la seconda query inserisco il cliente...

            Utente utente = selectUtenteByCredenziali(email, password);

            try (
                    PreparedStatement insertClienteStatement = dbConnection.prepareStatement(query2);
            )  {
                if (utente != null) {

                    int idUtente = utente.getIdUtente();

                    insertClienteStatement.setInt(1, idUtente);
                    insertClienteStatement.setString(2, email);
                    insertClienteStatement.setString(3, nome);
                    insertClienteStatement.setString(4, cognome);
                    insertClienteStatement.setDate(5, conversioniData.fromStringToSqlDate(dataNascita));
                    insertClienteStatement.setDouble(6, 0.00);

                    insertClienteStatement.executeUpdate();

                    return true;

                } else {

                    dbConnection.rollback();
                    return false;

                }

            }

        }

    }

    // * * * * * * * * * * * * INSERT Distributore * * * * * * * * * * * *
    public boolean effettuaOrdine(String dataOrdine, String refEmail, int refIdDistributore, int refIdProdotto, double importo) throws SQLException {

        String query = "INSERT INTO ordini(data_ordine, ref_email, ref_id_distributore, ref_id_prodotto, importo) VALUES (?,?,?,?,?)";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, dataOrdine);
            preparedStatement.setString(2, refEmail);
            preparedStatement.setInt(3, refIdDistributore);
            preparedStatement.setInt(4, refIdProdotto);
            preparedStatement.setDouble(5, importo);

            preparedStatement.executeUpdate();

            return true;
        }

    }

    // * * * * * * * * * * * * INSERT Associazione * * * * * * * * * * * *
    public boolean insertAssociazione(int refIdDistributore, int refIdCliente) throws SQLException {

        String query = "INSERT INTO associazioni(ref_id_distributore, ref_id_cliente, stato) VALUES (?,?,?)";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ) {

            preparedStatement.setInt(1, refIdDistributore);
            preparedStatement.setInt(2, refIdCliente);
            preparedStatement.setBoolean(3, true);

            preparedStatement.executeUpdate();

            return true;

        }

    }

    // *************************************** U P D A T E ***************************************

    // * * * * * * * * * * * * UPDATE Utente * * * * * * * * * * * *
    public boolean updateStatoUtente(int idUtente, boolean statoNuovo) throws SQLException {

        String query = "UPDATE utenti SET stato=? WHERE id_utente=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setBoolean(1, statoNuovo);
            preparedStatement.setInt(2, idUtente);

            preparedStatement.executeUpdate();

            return true;

        }

    }

    public boolean updatePasswordUtente(int idUtente, String vecchiaPassword, String nuovaPassword) throws SQLException {

        String query = "UPDATE utenti SET passw=? WHERE id_utente=? AND passw=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, nuovaPassword);
            preparedStatement.setInt(2, idUtente);
            preparedStatement.setString(3, vecchiaPassword);

            int result = preparedStatement.executeUpdate();

            if (result == 1) {

                // L'update è avvenuto correttamente. Ritorno l'esito positivo

                return true;

            } else {

                return false;

            }

        }

    }

    // * * * * * * * * * * * * UPDATE Cliente * * * * * * * * * * * *
    public boolean updateSaldoDisponibile(int refIdUtente, double saldoNuovo) throws SQLException {

        String query = "UPDATE clienti SET saldo_disponibile=? WHERE ref_id_utente=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setDouble(1, saldoNuovo);
            preparedStatement.setInt(2, refIdUtente);

            preparedStatement.executeUpdate();

            return true;

        }

    }

    // * * * * * * * * * * * * UPDATE Associazione * * * * * * * * * * * *

    public boolean updateAssociazione(int idAssociazione, boolean statoNuovo) throws SQLException {

        String query = "UPDATE associazioni SET stato=? WHERE id_associazione=?";

        try (
                Connection dbConnection = this.dbConnector.connessioneDB();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)
        ) {

            preparedStatement.setBoolean(1, statoNuovo);
            preparedStatement.setInt(2, idAssociazione);

            preparedStatement.executeUpdate();

            return true;

        }


    }

    // *************************************** F U N Z I O N A L I T A' ***************************************

    // * * * * * * * * * * * * LOGIN Utente * * * * * * * * * * * *
    public Utente login(String email, String password) throws SQLException {

        Utente utente = selectUtenteByCredenziali(email, password);

            if (utente != null) {

                // Se la query avviene correttamente, conservo in delle variabili l'id, il tipo e lo stato dell'utente

                int idUtente = utente.getIdUtente();
                int tipoUtente = utente.getTipoUtente();
                boolean statoUtente = utente.getStato();

                if (tipoUtente == 0) {

                    // L'utente in questione è un Distributore: controllo che effettivamente l'id corrisponda
                    // ad un Distributore presente nel DB

                    Distributore distributore = selectDistributoreById(idUtente);

                    if (distributore != null) {

                        // La query ha dato l'esito aspettato:
                        // 1. Setto i parametri del Distributore

                        distributore.setId(idUtente);
                        distributore.setTipoUtente(tipoUtente);
                        distributore.setEmail(email);
                        distributore.setPassword(password);
                        distributore.setStato(statoUtente);

                        // 2. Restituisco il Distributore

                        return distributore;

                    }
                } else if (tipoUtente == 1) {

                    // L'utente in questione è un Cliente: controllo che effettivamente l'id corrisponda
                    // ad un Cliente presente nel DB

                    Cliente cliente = selectClienteById(idUtente);

                    if (cliente != null) {

                        // La query ha dato l'esito aspettato:
                        // 1. Setto i parametri del Cliente

                        cliente.setId(idUtente);
                        cliente.setTipoUtente(tipoUtente);
                        cliente.setEmail(email);
                        cliente.setPassword(password);
                        cliente.setStato(statoUtente);

                        // 2. Restituisco il Cliente

                        return cliente;

                    }

                }
            }

        return null;

    }

    // * * * * * * * * * * * * REGISTRA Cliente * * * * * * * * * * * *
    public boolean signIn(String email, String password, String nome, String cognome, String dataNascita) throws SQLException {

        // Controllo in primis che non vi sia già un utente registrato con la stessa email

        if (!this.selectUtenteRegistratoByEmail(email) && this.insertCliente(email, password, nome, cognome, dataNascita)) {

            return true;

        } else {

            return false;

        }

    }

    // * * * * * * * * * * * * ASSOCIA Dispositivi * * * * * * * * * * * *
    public boolean associaDispositivi(int refIdDistributore, int refIdCliente) throws SQLException {

        if (this.updateStatoUtente(refIdDistributore, false) && this.updateStatoUtente(refIdCliente, false) && insertAssociazione(refIdDistributore, refIdCliente)) {

            // L'aggiornamento degli stati di Cliente e Distributore nel DB ha dato esito positivo.
            // L'inserimento dell'associazione nel DB ha dato esito positivo.

            // 2. Restituisco l'esito positivo dell'operazione

            return true;

        } else {

            return false;

        }

    }

    // * * * * * * * * * * * * DISSOCIA Dispositivi * * * * * * * * * * * *
    public boolean dissociaDispositivi(int refIdDistributore, int refIdCliente) throws SQLException {

        // CONTROLLA LO STATO DELL'ASSOCIAZIONE: se è true, allora fa l'update dello stato di cliente e distributore
        // e setta a false lo stato dell'associazione per dire che la dissociazione è avvenuta

        Associazione associazione = selectAssociazione(refIdDistributore, refIdCliente);

        if (associazione.getStato()) {

            // Faccio l'update dello stato di cliente e distributore, settandolo a true
            // in modo da consentire al cliente di associarsi a un altro distributore
            // e al distributore di far associare altri clienti ad esso

            if (updateStatoUtente(refIdDistributore, true) && updateStatoUtente(refIdCliente, true) && updateAssociazione(associazione.getIdAssociazione(), false)) {

                // L'aggiornamento degli stati di Cliente e Distributore nel DB ha dato esito positivo.
                // L'aggiornamento dello stato dell'associazione nel DB ha dato esito positivo.

                // Restituisco l'esito positivo dell'operazione

                return true;

            }

        }

        return false;

    }

}