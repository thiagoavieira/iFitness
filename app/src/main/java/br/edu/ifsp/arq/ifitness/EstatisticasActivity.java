package br.edu.ifsp.arq.ifitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class EstatisticasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextView txtLogin;
    private Button btnLeaderboard;
    private ImageView imagePerfil;
    private UsuarioViewModel usuarioViewModel;

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

        /*
        btnLeaderboard = findViewById(R.id.btn_activity_estatisticas_leaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(EstatisticasActivity.this,
                        LeaderboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        */
        txtLogin = findViewById(R.id.estatisticas_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener(){
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
                if(usuario != null){
                    txtLogin.setText(usuario.getNome()
                            + " " + usuario.getSobrenome());
                }
                String perfilImage = PreferenceManager
                        .getDefaultSharedPreferences(EstatisticasActivity.this)
                        .getString(MediaStore.EXTRA_OUTPUT, null);
                if(perfilImage != null){
                    imagePerfil.setImageURI(Uri.parse(perfilImage));
                }else{
                    imagePerfil.setImageResource(R.drawable.profile_icon);
                }
            }
        });{

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
