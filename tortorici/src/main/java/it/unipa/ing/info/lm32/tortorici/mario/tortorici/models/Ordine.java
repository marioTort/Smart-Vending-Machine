package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

public class Ordine {
    private int idOrdine, idDistributore, idProdotto;
    private String dataOrdine;
    private double prezzo;

    // Costruttore

    public Ordine() {}

    // Setters

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public void setIdDistributore(int idDistributore) {
        this.idDistributore = idDistributore;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public void setDataOrdine(String dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    // Getters

    public int getIdOrdine() {
        return idOrdine;
    }

    public int getIdDistributore() {
        return idDistributore;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public String getDataOrdine() {
        return dataOrdine;
    }

    public double getPrezzo() {
        return prezzo;
    }

}
