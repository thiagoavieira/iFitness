package br.edu.ifsp.arq.ifitness.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "usuario")
public class Usuario implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String email;
    private String nome;
    private String sobrenome;
    private String senha;
    private String dataNasc;
    private String sexo;
    private String telefone;
    private String imagem;
    private String emblema;
    private String pontuacao;

    public Usuario(String email, String nome, String sobrenome, String senha, String dataNasc,
                   String sexo, String telefone, String imagem, String emblema, String pontuacao) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.senha = senha;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.telefone = telefone;
        this.imagem = imagem;
        this.emblema = emblema;
        this.pontuacao = pontuacao;

    }

    @Ignore
    public Usuario() {
        this("", "", "", "", "", "", "", "", "", "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getEmblema() {
        return emblema;
    }

    public void setEmblema(String emblema) {
        this.emblema = emblema;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}