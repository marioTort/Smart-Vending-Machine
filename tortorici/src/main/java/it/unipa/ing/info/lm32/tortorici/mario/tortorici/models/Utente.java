package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

public class Utente {

    private int idUtente, tipoUtente;
    private String email, password;
    private boolean stato;

    // Costruttore

    public Utente() {}

    // Setters

    public void setId(int idUtente) {
        this.idUtente = idUtente;
    }

    public void setTipoUtente(int tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    // Getters

    public int getIdUtente() {
        return idUtente;
    }

    public int getTipoUtente() {
        return tipoUtente;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getStato() {
        return stato;
    }

}
