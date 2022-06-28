package br.edu.ifsp.arq.ifitness.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "atividades")
public class Atividades implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String usuarioId;
    private Categoria categoria;
    private Double distancia;
    private int duracao;
    private String data;

    public Atividades(String usuarioId, Categoria categoria, Double distancia, int duracao, String data) {
        this.id = UUID.randomUUID().toString();
        this.usuarioId = usuarioId;
        this.categoria = categoria;
        this.distancia = distancia;
        this.duracao = duracao;
        this.data = data;
    }

    @Ignore
    public Atividades() {
        this("", null, 0.0, 0, null);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividades atividades = (Atividades) o;
        return Objects.equals(id, atividades.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
