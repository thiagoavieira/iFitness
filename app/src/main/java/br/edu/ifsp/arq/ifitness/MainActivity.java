package br.edu.ifsp.arq.ifitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextView txtLogin;
    private LinearLayout caminhadaDetalhe;
    private LinearLayout ciclismoDetalhe;
    private LinearLayout corridaDetalhe;
    private LinearLayout natacaoDetalhe;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private UsuarioViewModel usuarioViewModel;
    private ImageView imagePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("iFitness");

        drawerLayout = findViewById(R.id.nav_drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.toogle_open, R.string.toogle_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch(item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, UsuarioPerfilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_my_progress:
                        intent = new Intent(MainActivity.this, EstatisticasActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_leaderboard:
                        intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        usuarioViewModel.logout();
                        finish();
                        startActivity(getIntent());
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        caminhadaDetalhe = findViewById(R.id.ll_caminhada_item);
        caminhadaDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        CaminhadaDetalheActivity.class);
                startActivity(intent);
            }
        });

        ciclismoDetalhe = findViewById(R.id.ll_ciclismo_item);
        ciclismoDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        CiclismoDetalheActivity.class);
                startActivity(intent);
            }
        });

        corridaDetalhe = findViewById(R.id.ll_corrida_item);
        corridaDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        CorridaDetalheActivity.class);
                startActivity(intent);
            }
        });

        natacaoDetalhe = findViewById(R.id.ll_natacao_item);
        natacaoDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        NatacaoDetalheActivity.class);
                startActivity(intent);
            }
        });

        txtLogin = navigationView.getHeaderView(0)
                .findViewById(R.id.header_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        UsuarioLoginActivity.class);
                startActivity(intent);
            }
        });

        imagePerfil = navigationView.getHeaderView(0).findViewById(R.id.header_profile_icon);
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
                        .getDefaultSharedPreferences(MainActivity.this)
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}