package br.edu.ifsp.arq.ifitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import br.edu.ifsp.arq.ifitness.customspinner.CustomAdapter;
import br.edu.ifsp.arq.ifitness.customspinner.CustomItem;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class EstatisticasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private ImageView imagePerfil;

    private TextView txtTitulo;
    private TextView txtLogin;
    private TextView txtDistancia;
    private TextView txtDuracao;
    private TextView txtCalorias;
    private TextView txtPontuacao;
    private TextView txtEmblemas;

    private Spinner customSpinner;
    ArrayList<CustomItem> customList;

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

        customSpinner = findViewById(R.id.sp_lista_emblemas);

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
                    if (usuario.getDistanciaTotal() >= 150) {
                        customList = getCustomListPlatinum();
                        CustomAdapter adapter = new CustomAdapter(EstatisticasActivity.this, customList);
                        if(customSpinner != null){
                            customSpinner.setAdapter(adapter);
                            customSpinner.setOnItemSelectedListener(EstatisticasActivity.this);
                        }
                        usuario.setEmblema("Troféu Platinum (150km)");
                        usuarioViewModel.update(usuario);
                    } else if (usuario.getDistanciaTotal() >= 100) {
                        customList = getCustomListOuro();
                        CustomAdapter adapter = new CustomAdapter(EstatisticasActivity.this, customList);
                        if(customSpinner != null){
                            customSpinner.setAdapter(adapter);
                            customSpinner.setOnItemSelectedListener(EstatisticasActivity.this);
                        }
                        usuario.setEmblema("Troféu Ouro (100km)");
                        usuarioViewModel.update(usuario);
                    } else if (usuario.getDistanciaTotal() >= 50) {
                        customList = getCustomListPrata();
                        CustomAdapter adapter = new CustomAdapter(EstatisticasActivity.this, customList);
                        if(customSpinner != null){
                            customSpinner.setAdapter(adapter);
                            customSpinner.setOnItemSelectedListener(EstatisticasActivity.this);
                        }
                        usuario.setEmblema("Troféu Prata (50km)");
                        usuarioViewModel.update(usuario);
                    } else if (usuario.getDistanciaTotal() >= 25) {
                        customList = getCustomListBronze();
                        CustomAdapter adapter = new CustomAdapter(EstatisticasActivity.this, customList);
                        if(customSpinner != null){
                            customSpinner.setAdapter(adapter);
                            customSpinner.setOnItemSelectedListener(EstatisticasActivity.this);
                        }
                        usuario.setEmblema("Troféu Bronze (25km)");
                        usuarioViewModel.update(usuario);
                    } else if (usuario.getDistanciaTotal() >= 15) {
                        customList = getCustomListIniciante();
                        CustomAdapter adapter = new CustomAdapter(EstatisticasActivity.this, customList);
                        if(customSpinner != null){
                            customSpinner.setAdapter(adapter);
                            customSpinner.setOnItemSelectedListener(EstatisticasActivity.this);
                        }
                        usuario.setEmblema("Troféu Iniciante (15km)");
                        usuarioViewModel.update(usuario);
                    } else {
                        txtEmblemas.setText("Você ainda não conquistou um emblema!");
                        txtEmblemas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        customSpinner.setVisibility(View.INVISIBLE);
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

    private ArrayList<CustomItem> getCustomListPlatinum() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Troféu Platinum (150km)", R.drawable.ic_trofeu_platinum));
        customList.add(new CustomItem("Troféu Ouro (100km)", R.drawable.ic_trofeu_ouro));
        customList.add(new CustomItem("Troféu Prata (50km)", R.drawable.ic_trofeu_prata));
        customList.add(new CustomItem("Troféu Bronze (25km)", R.drawable.ic_trofeu_bronze));
        customList.add(new CustomItem("Troféu Iniciante (15km)", R.drawable.ic_trofeu_iniciante));
        return customList;
    }

    private ArrayList<CustomItem> getCustomListOuro() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Troféu Ouro (100km)", R.drawable.ic_trofeu_ouro));
        customList.add(new CustomItem("Troféu Prata (50km)", R.drawable.ic_trofeu_prata));
        customList.add(new CustomItem("Troféu Bronze (25km)", R.drawable.ic_trofeu_bronze));
        customList.add(new CustomItem("Troféu Iniciante (15km)", R.drawable.ic_trofeu_iniciante));;
        return customList;
    }
    private ArrayList<CustomItem> getCustomListPrata() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Troféu Prata (50km)", R.drawable.ic_trofeu_prata));
        customList.add(new CustomItem("Troféu Bronze (25km)", R.drawable.ic_trofeu_bronze));
        customList.add(new CustomItem("Troféu Iniciante (15km)", R.drawable.ic_trofeu_iniciante));;
        return customList;
    }
    private ArrayList<CustomItem> getCustomListBronze() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Troféu Bronze (25km)", R.drawable.ic_trofeu_bronze));
        customList.add(new CustomItem("Troféu Iniciante (15km)", R.drawable.ic_trofeu_iniciante));;
        return customList;
    }
    private ArrayList<CustomItem> getCustomListIniciante() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Troféu Iniciante (15km)", R.drawable.ic_trofeu_iniciante));;
        return customList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CustomItem item = (CustomItem) adapterView.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
