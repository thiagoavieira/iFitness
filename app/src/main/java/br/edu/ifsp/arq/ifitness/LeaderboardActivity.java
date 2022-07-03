package br.edu.ifsp.arq.ifitness;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class LeaderboardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTitulo;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;
    private ImageView imgLeaderboardIcon;

    private LinearLayout llLeaderboard1, llLeaderboard2, llLeaderboard3, llLeaderboard4, llLeaderboard5;
    private LinearLayout llLeaderboard6, llLeaderboard7, llLeaderboard8, llLeaderboard9, llLeaderboard10;

    private TextView txtDefault;
    private TextView txtLeaderboardNome1, txtLeaderboardNome2, txtLeaderboardNome3, txtLeaderboardNome4, txtLeaderboardNome5;
    private TextView txtLeaderboardNome6, txtLeaderboardNome7, txtLeaderboardNome8, txtLeaderboardNome9, txtLeaderboardNome10;

    private TextView txtLeaderboardPontuacao1, txtLeaderboardPontuacao2, txtLeaderboardPontuacao3, txtLeaderboardPontuacao4, txtLeaderboardPontuacao5;
    private TextView txtLeaderboardPontuacao6, txtLeaderboardPontuacao7, txtLeaderboardPontuacao8, txtLeaderboardPontuacao9, txtLeaderboardPontuacao10;

    int indice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        configurarTela();
        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);
        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if(usuario != null){
                    LeaderboardActivity.this.usuario = usuario;
                    usuarioViewModel.getUsuarios().observe(LeaderboardActivity.this, new Observer<List<Usuario>>() {
                        @Override
                        public void onChanged(List<Usuario> listUsuario) {
                            for (Usuario user: listUsuario){
                                if(user!=null){
                                    apresentarLeaderboard(user);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void configurarTela() {
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Leaderboard");
        imgLeaderboardIcon = findViewById(R.id.leaderboard_icon);
        txtDefault = findViewById(R.id.txt_leaderboard_default);

        llLeaderboard1 = findViewById(R.id.ll_leaderboard_1); llLeaderboard2 = findViewById(R.id.ll_leaderboard_2);
        llLeaderboard3 = findViewById(R.id.ll_leaderboard_3); llLeaderboard4 = findViewById(R.id.ll_leaderboard_4);
        llLeaderboard5 = findViewById(R.id.ll_leaderboard_5); llLeaderboard6 = findViewById(R.id.ll_leaderboard_6);
        llLeaderboard7 = findViewById(R.id.ll_leaderboard_7); llLeaderboard8 = findViewById(R.id.ll_leaderboard_8);
        llLeaderboard9 = findViewById(R.id.ll_leaderboard_9); llLeaderboard10 = findViewById(R.id.ll_leaderboard_10);
        llLeaderboard1.setVisibility(View.INVISIBLE); llLeaderboard2.setVisibility(View.INVISIBLE);
        llLeaderboard3.setVisibility(View.INVISIBLE); llLeaderboard4.setVisibility(View.INVISIBLE);
        llLeaderboard5.setVisibility(View.INVISIBLE); llLeaderboard6.setVisibility(View.INVISIBLE);
        llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
        llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

        txtLeaderboardNome1 = findViewById(R.id.txt_leaderboard_nome1); txtLeaderboardNome2 = findViewById(R.id.txt_leaderboard_nome2);
        txtLeaderboardNome3 = findViewById(R.id.txt_leaderboard_nome3); txtLeaderboardNome4 = findViewById(R.id.txt_leaderboard_nome4);
        txtLeaderboardNome5 = findViewById(R.id.txt_leaderboard_nome5); txtLeaderboardNome6 = findViewById(R.id.txt_leaderboard_nome6);
        txtLeaderboardNome7 = findViewById(R.id.txt_leaderboard_nome7); txtLeaderboardNome8 = findViewById(R.id.txt_leaderboard_nome8);
        txtLeaderboardNome9 = findViewById(R.id.txt_leaderboard_nome9); txtLeaderboardNome10 = findViewById(R.id.txt_leaderboard_nome10);

        txtLeaderboardPontuacao1 = findViewById(R.id.txt_leaderboard_pontuacao1); txtLeaderboardPontuacao2 = findViewById(R.id.txt_leaderboard_pontuacao2);
        txtLeaderboardPontuacao3 = findViewById(R.id.txt_leaderboard_pontuacao3); txtLeaderboardPontuacao4 = findViewById(R.id.txt_leaderboard_pontuacao4);
        txtLeaderboardPontuacao5 = findViewById(R.id.txt_leaderboard_pontuacao5); txtLeaderboardPontuacao6 = findViewById(R.id.txt_leaderboard_pontuacao6);
        txtLeaderboardPontuacao7 = findViewById(R.id.txt_leaderboard_pontuacao7); txtLeaderboardPontuacao8 = findViewById(R.id.txt_leaderboard_pontuacao8);
        txtLeaderboardPontuacao9 = findViewById(R.id.txt_leaderboard_pontuacao9); txtLeaderboardPontuacao10 = findViewById(R.id.txt_leaderboard_pontuacao10);

    }

    private void apresentarLeaderboard(Usuario usuario) {
        txtDefault.setText("Ranking por pontuação");
        switch (indice){
            case 0:
                llLeaderboard2.setVisibility(View.INVISIBLE); llLeaderboard3.setVisibility(View.INVISIBLE);
                llLeaderboard4.setVisibility(View.INVISIBLE); llLeaderboard5.setVisibility(View.INVISIBLE);
                llLeaderboard6.setVisibility(View.INVISIBLE); llLeaderboard7.setVisibility(View.INVISIBLE);
                llLeaderboard8.setVisibility(View.INVISIBLE); llLeaderboard9.setVisibility(View.INVISIBLE);
                llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard1.setVisibility(View.VISIBLE);
                txtLeaderboardNome1.setText(usuario.getNome()); txtLeaderboardPontuacao1.setText(String.valueOf(usuario.getPontuacao()));
                break;
            case 1:
                llLeaderboard3.setVisibility(View.INVISIBLE); llLeaderboard4.setVisibility(View.INVISIBLE);
                llLeaderboard5.setVisibility(View.INVISIBLE); llLeaderboard6.setVisibility(View.INVISIBLE);
                llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard2.setVisibility(View.VISIBLE);
                txtLeaderboardNome2.setText(usuario.getNome()); txtLeaderboardPontuacao2.setText(String.valueOf(usuario.getPontuacao()));
                break;
            case 2:
                llLeaderboard4.setVisibility(View.INVISIBLE);
                llLeaderboard5.setVisibility(View.INVISIBLE); llLeaderboard6.setVisibility(View.INVISIBLE);
                llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard3.setVisibility(View.VISIBLE);
                txtLeaderboardNome3.setText(usuario.getNome()); txtLeaderboardPontuacao3.setText(String.valueOf(usuario.getPontuacao()));
                break;
            case 3:
                llLeaderboard5.setVisibility(View.INVISIBLE); llLeaderboard6.setVisibility(View.INVISIBLE);
                llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard4.setVisibility(View.VISIBLE);
                txtLeaderboardNome4.setText(usuario.getNome()); txtLeaderboardPontuacao4.setText(String.valueOf(usuario.getPontuacao()));
                break;
            case 4:
                llLeaderboard6.setVisibility(View.INVISIBLE);
                llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard5.setVisibility(View.VISIBLE);
                txtLeaderboardNome5.setText(usuario.getNome()); txtLeaderboardPontuacao5.setText(String.valueOf(usuario.getPontuacao()));
                break;
            case 5:
                llLeaderboard7.setVisibility(View.INVISIBLE); llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard6.setVisibility(View.VISIBLE);
                txtLeaderboardNome6.setText(usuario.getNome()); txtLeaderboardPontuacao6.setText(String.valueOf(usuario.getPontuacao()));
            case 6:
                llLeaderboard8.setVisibility(View.INVISIBLE);
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard7.setVisibility(View.VISIBLE);
                txtLeaderboardNome7.setText(usuario.getNome()); txtLeaderboardPontuacao7.setText(String.valueOf(usuario.getPontuacao()));
            case 7:
                llLeaderboard9.setVisibility(View.INVISIBLE); llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard8.setVisibility(View.VISIBLE);
                txtLeaderboardNome8.setText(usuario.getNome()); txtLeaderboardPontuacao8.setText(String.valueOf(usuario.getPontuacao()));
            case 8:
                llLeaderboard10.setVisibility(View.INVISIBLE);

                llLeaderboard9.setVisibility(View.VISIBLE);
                txtLeaderboardNome9.setText(usuario.getNome()); txtLeaderboardPontuacao9.setText(String.valueOf(usuario.getPontuacao()));
            case 9:
                llLeaderboard10.setVisibility(View.VISIBLE);
                txtLeaderboardNome10.setText(usuario.getNome()); txtLeaderboardPontuacao10.setText(String.valueOf(usuario.getPontuacao()));
        }
        indice++;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
