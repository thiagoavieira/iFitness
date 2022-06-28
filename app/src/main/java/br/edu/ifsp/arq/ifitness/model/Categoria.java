package br.edu.ifsp.arq.ifitness.model;

public enum Categoria {

    CAMINHADA("Caminhada"),
    CICLISMO("Ciclismo"),
    CORRIDA("Corrida"),
    NATACAO("Natação");

    private String descricao;

    Categoria(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
