package br.edu.ifsp.arq.ifitness.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import br.edu.ifsp.arq.ifitness.model.Atividades;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.model.UsuarioComAtividades;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario WHERE email = :email AND senha = :senha")
    Usuario login(String email, String senha);

    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :usuarioId")
    LiveData<UsuarioComAtividades> loadUsuarioComAtividadesById(String usuarioId);

    @Insert
    void insert(Usuario usuario);

    @Insert
    void insert(Atividades atividades);

    @Update
    void update(Usuario usuario);

    @Update
    void update(Atividades atividades);
}
