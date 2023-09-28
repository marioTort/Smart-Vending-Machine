package it.unipa.ing.info.lm32.tortorici.mario.tortorici.models;

public class Prodotto {
    private int idProdotto, tipo;
    private String nome;
    private double prezzo;

    // Costruttore

    public Prodotto() {}

    // Setters

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    // Getters

    public int getIdProdotto() {
        return idProdotto;
    }

    public int getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

}
