package br.edu.ifsp.arq.ifitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class UsuarioCadastroActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtNome;
    private TextInputEditText txtEmail;
    private TextInputEditText txtSenha;
    private Button btnCadastrar;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastro);

        usuarioViewModel =
                new ViewModelProvider(this)
                        .get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_cadastro_titulo);
        txtNome = findViewById(R.id.txt_edit_nome);
        txtEmail = findViewById(R.id.txt_edit_email);
        txtSenha = findViewById(R.id.txt_edit_password);

        btnCadastrar = findViewById(R.id.btn_user_register);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = new Usuario(
                        txtEmail.getText().toString(),
                        txtNome.getText().toString(),
                        "",
                        txtSenha.getText().toString(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        0,
                        0.0,
                        0,
                        0.0);

                if(usuario.getSenha().length() >= 6){
                    usuarioViewModel.createUsuario(usuario);
                    usuarioViewModel.login(usuario.getEmail(), usuario.getSenha())
                            .observe(UsuarioCadastroActivity.this, new Observer<Usuario>() {
                                        @Override
                                        public void onChanged(Usuario usuario) {
                                            finish();
                                        }
                                    }
                            );
                }else{
                    Toast.makeText(UsuarioCadastroActivity.this, R.string.msg_erro_senha, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        if (txtNome.getText().toString().trim().isEmpty()) {
            txtNome.setError("Preencha o campo nome");
            isValid = false;
        } else {
            txtNome.setError(null);
        }
        if (txtSenha.getText().toString().trim().isEmpty()) {
            txtSenha.setError("Preencha o campo senha");
            isValid = false;
        } else {
            txtSenha.setError(null);
        }
        if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError("Preencha o campo email");
            isValid = false;
        } else {
            txtEmail.setError(null);
        }
        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
