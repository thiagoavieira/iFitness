package br.edu.ifsp.arq.ifitness.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class UsuarioComAtividades {

    @Embedded
    private Usuario usuario;

    @Relation(
            parentColumn = "id",
            entityColumn = "usuarioId"
    )
    private List<Atividades> atividades;

    public UsuarioComAtividades() {
        atividades = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Atividades> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividades> atividades) {
        this.atividades = atividades;
    }
}
