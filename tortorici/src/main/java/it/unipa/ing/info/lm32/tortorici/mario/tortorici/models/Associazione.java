package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

public class Associazione {
    private int idAssociazione, idDistributore, idCliente;
    private boolean stato;

    // Costruttore

    public Associazione() {}

    // Setters

    public void setIdAssociazione(int idAssociazione) {
        this.idAssociazione = idAssociazione;
    }

    public void setIdDistributore(int idDistributore) {
        this.idDistributore = idDistributore;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    // Getters

    public int getIdAssociazione() {
        return idAssociazione;
    }

    public int getIdDistributore() {
        return idDistributore;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public boolean getStato() {
        return stato;
    }

}
