package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

import java.util.List;

public class Cliente extends Utente {
    private String nome, cognome, dataNascita;
    private List<Ordine> ordiniEffettuati;
    private double saldoDisponibile;

    // Costruttore

    public Cliente() {
        super();
    }

    // Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setOrdiniEffettuati(List<Ordine> ordiniEffettuati) {
        this.ordiniEffettuati = ordiniEffettuati;
    }

    public void setSaldoDisponibile(double saldoDisponibile) {
        this.saldoDisponibile = saldoDisponibile;
    }

    // Getters

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public List<Ordine> getOrdiniEffettuati() {
        return ordiniEffettuati;
    }

    public double getSaldoDisponibile() {
        return saldoDisponibile;
    }

}
