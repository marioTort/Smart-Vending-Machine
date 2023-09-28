package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

import java.util.List;

public class Distributore extends Utente {
    private String posizione;
    private List<Prodotto> prodottiInseriti;

    public Distributore() {
        super();
    }

    // Setters

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void setProdottiInseriti(List<Prodotto> prodottiInseriti) {
        this.prodottiInseriti = prodottiInseriti;
    }


    // Getters

    public String getPosizione() {
        return posizione;
    }

    public List<Prodotto> getProdottiInseriti() {
        return prodottiInseriti;
    }

}
