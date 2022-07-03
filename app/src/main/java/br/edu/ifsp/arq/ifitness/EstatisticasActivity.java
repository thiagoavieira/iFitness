package br.edu.ifsp.arq.ifitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class EstatisticasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imagePerfil;

    private TextView txtTitulo;
    private TextView txtLogin;
    private TextView txtDistancia;
    private TextView txtDuracao;
    private TextView txtCalorias;
    private TextView txtPontuacao;
    private TextView txtEmblemas;
    private Spinner spnEmblemas;

    private Button btnLeaderboard;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Estatísticas");

        txtDistancia = findViewById(R.id.activity_estatisticas_distancia_total_numero);
        txtDuracao = findViewById(R.id.activity_estatisticas_duracao_total_numero);
        txtCalorias = findViewById(R.id.activity_estatisticas_calorias_total_numero);
        txtPontuacao = findViewById(R.id.activity_estatisticas_pontuacao_total_numero);
        txtEmblemas = findViewById(R.id.txt_view_estatisticas_emblemas);
        spnEmblemas = findViewById(R.id.sp_lista_emblemas);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    EstatisticasActivity.this.usuario = usuario;
                    txtDistancia.setText(usuario.getDistanciaTotal().toString());
                    txtDuracao.setText(String.valueOf(usuario.getDuracaoTotal()));
                    txtCalorias.setText(usuario.getCaloriasTotal().toString());
                    txtPontuacao.setText(String.valueOf(usuario.getPontuacao()));
                    String[] emblema = getResources().getStringArray(R.array.spEmblemas);
                    if (usuario.getDistanciaTotal() > 150) {
                        for (int i = 0; i < emblema.length; i++) {
                            if (emblema[i].equals(usuario.getEmblema())) {
                                spnEmblemas.setSelection(i);
                            }
                        }
                    } else if (usuario.getDistanciaTotal() > 100) {
                        for (int i = 0; i < emblema.length - 1; i++) {
                            if (emblema[i].equals(usuario.getEmblema())) {
                                spnEmblemas.setSelection(i);
                            }
                        }
                    } else if (usuario.getDistanciaTotal() > 50) {
                        for (int i = 0; i < emblema.length - 2; i++) {
                            if (emblema[i].equals(usuario.getEmblema())) {
                                spnEmblemas.setSelection(i);
                            }
                        }
                    } else if (usuario.getDistanciaTotal() > 25) {
                        for (int i = 0; i < emblema.length - 3; i++) {
                            if (emblema[i].equals(usuario.getEmblema())) {
                                spnEmblemas.setSelection(i);
                            }
                        }
                    } else if (usuario.getDistanciaTotal() > 15) {
                        for (int i = 0; i < emblema.length - 4; i++) {
                            if (emblema[i].equals(usuario.getEmblema())) {
                                spnEmblemas.setSelection(i);
                            }
                        }
                    } else {
                        txtEmblemas.setText("Você ainda não conquistou um emblema!");
                        txtEmblemas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        spnEmblemas.setVisibility(View.INVISIBLE);
                    }

                } else {
                    startActivity(new Intent(EstatisticasActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

        btnLeaderboard = findViewById(R.id.btn_activity_estatisticas_leaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstatisticasActivity.this,
                        LeaderboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtLogin = findViewById(R.id.estatisticas_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstatisticasActivity.this,
                        UsuarioLoginActivity.class);
                startActivity(intent);
            }
        });

        imagePerfil = findViewById(R.id.estastisticas_profile_image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    txtLogin.setText(usuario.getNome()
                            + " " + usuario.getSobrenome());
                }
                String perfilImage = PreferenceManager
                        .getDefaultSharedPreferences(EstatisticasActivity.this)
                        .getString(MediaStore.EXTRA_OUTPUT, null);
                if (perfilImage != null) {
                    imagePerfil.setImageURI(Uri.parse(perfilImage));
                } else {
                    imagePerfil.setImageResource(R.drawable.profile_icon);
                }
            }
        });
        {
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
