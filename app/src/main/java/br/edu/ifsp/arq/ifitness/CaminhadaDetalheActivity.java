package br.edu.ifsp.arq.ifitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifsp.arq.ifitness.model.Atividade;
import br.edu.ifsp.arq.ifitness.model.Categoria;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class CaminhadaDetalheActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private TextInputEditText txtDistancia;
    private TextInputEditText txtDuracao;

    private Button btnConfirmar;
    private Button btnAtualizar;
    private Button btnRemover;

    private Atividade atividade;
    private Usuario usuario;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caminhada_detalhe);

        usuarioViewModel =
                new ViewModelProvider(this)
                        .get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Registrar Atividade: Caminhada");

        txtDistancia = findViewById(R.id.txt_edit_distancia);
        txtDuracao = findViewById(R.id.txt_edit_duracao);

        btnConfirmar = findViewById(R.id.activity_detalhe_btn_confirmar);
        btnAtualizar = findViewById(R.id.activity_detalhe_btn_atualizar);
        btnRemover = findViewById(R.id.activity_detalhe_btn_remover);

        btnAtualizar.setVisibility(View.INVISIBLE);
        btnRemover.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        atividade = (Atividade) intent.getSerializableExtra("ativSelecionada");
        if (atividade != null) {
            btnAtualizar.setVisibility(View.VISIBLE);
            btnRemover.setVisibility(View.VISIBLE);
            btnConfirmar.setVisibility(View.INVISIBLE);

            txtDistancia.setText(atividade.getDistancia().toString());
            txtDuracao.setText(String.valueOf(atividade.getDuracao()));
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(CaminhadaDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if(usuario != null){
                            if(validate())
                            {
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar cal = Calendar.getInstance();
                                Date date = cal.getTime();

                                Atividade atividade = new Atividade(
                                        usuario.getId(),
                                        Categoria.CAMINHADA,
                                        Double.parseDouble(txtDistancia.getText().toString()),
                                        Integer.parseInt(txtDuracao.getText().toString()),
                                        dateFormat.format(date));

                                usuarioViewModel.createAtividade(atividade);


                                usuario.setDistanciaTotal(usuario.getDistanciaTotal()+atividade.getDistancia());
                                usuario.setDuracaoTotal(usuario.getDuracaoTotal()+atividade.getDuracao());
                                Double caloriasAux = atividade.getDuracao() * atividade.getDistancia();
                                usuario.setCaloriasTotal(usuario.getCaloriasTotal()+caloriasAux);

                                usuario.setPontuacao(usuario.getDistanciaTotal().intValue());

                                usuarioViewModel.update(usuario);

                                Toast.makeText(CaminhadaDetalheActivity.this, R.string.msg_atividade_sucesso, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Intent intent = new Intent(CaminhadaDetalheActivity.this,
                                    UsuarioLoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(CaminhadaDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if (usuario != null) {
                            CaminhadaDetalheActivity.this.usuario = usuario;

                            if (validate()) {

                                int pontuacaoAntiga = atividade.getDistancia().intValue();
                                Double distanciaAntiga = atividade.getDistancia();
                                int duracaoAntiga = atividade.getDuracao();
                                Double caloriasAntiga = atividade.getDuracao() * atividade.getDistancia();

                                atividade.setDistancia(Double.parseDouble(txtDistancia.getText().toString()));
                                atividade.setDuracao(Integer.parseInt(String.valueOf(txtDuracao.getText())));

                                usuarioViewModel.updateAtividade(atividade);


                                Double caloriasAtual = atividade.getDuracao() * atividade.getDistancia();

                                usuario.setDistanciaTotal(usuario.getDistanciaTotal() - distanciaAntiga + atividade.getDistancia());
                                usuario.setDuracaoTotal(usuario.getDuracaoTotal() - duracaoAntiga + atividade.getDuracao());
                                usuario.setCaloriasTotal(usuario.getCaloriasTotal()-caloriasAntiga+caloriasAtual);
                                usuario.setPontuacao(usuario.getPontuacao() - pontuacaoAntiga + atividade.getDistancia().intValue());

                                usuarioViewModel.update(usuario);

                                Toast.makeText(CaminhadaDetalheActivity.this, "Caminhada atualizada!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CaminhadaDetalheActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(CaminhadaDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if (usuario != null) {
                            CaminhadaDetalheActivity.this.usuario = usuario;

                            usuarioViewModel.delete(atividade);

                            Double caloriasAtual = atividade.getDuracao() * atividade.getDistancia();

                            usuario.setDistanciaTotal(usuario.getDistanciaTotal() - atividade.getDistancia());
                            usuario.setDuracaoTotal(usuario.getDuracaoTotal() - atividade.getDuracao());
                            usuario.setCaloriasTotal(usuario.getCaloriasTotal()-caloriasAtual);
                            usuario.setPontuacao(usuario.getPontuacao() - atividade.getDistancia().intValue());

                            usuarioViewModel.update(usuario);

                            Toast.makeText(CaminhadaDetalheActivity.this, "Caminhada removida!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CaminhadaDetalheActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }

    private boolean validate() {

        boolean isValid = true;

        if (txtDistancia.getText().toString().trim().isEmpty()) {
            txtDistancia.setError("Preencha o campo Distância");
            isValid = false;
        } else {
            txtDistancia.setError(null);
        }

        if (txtDuracao.getText().toString().trim().isEmpty()) {
            txtDuracao.setError("Preencha o campo Duração");
            isValid = false;
        } else {
            txtDuracao.setError(null);
        }
        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}