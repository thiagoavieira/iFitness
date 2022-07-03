package br.edu.ifsp.arq.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import br.edu.ifsp.arq.ifitness.model.Atividade;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class ListaAtividadesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;
    private Atividade atividade;

    private LinearLayout llAtividade1, llAtividade2, llAtividade3, llAtividade4, llAtividade5;
    private LinearLayout llAtividade6, llAtividade7, llAtividade8, llAtividade9, llAtividade10;

    private TextView txtAtividadesTitulo, txtAtividadesDefault;

    private TextView txtCategoria1, txtCategoria2, txtCategoria3, txtCategoria4, txtCategoria5;
    private TextView txtCategoria6, txtCategoria7, txtCategoria8, txtCategoria9, txtCategoria10;

    private TextView txtDistancia1, txtDistancia2, txtDistancia3, txtDistancia4, txtDistancia5;
    private TextView txtDistancia6, txtDistancia7, txtDistancia8, txtDistancia9, txtDistancia10;

    private TextView txtDuracao1, txtDuracao2, txtDuracao3, txtDuracao4, txtDuracao5;
    private TextView txtDuracao6, txtDuracao7, txtDuracao8, txtDuracao9, txtDuracao10;

    private TextView txtData1, txtData2, txtData3, txtData4, txtData5;
    private TextView txtData6, txtData7, txtData8, txtData9, txtData10;

    private ImageView imgAtividade1, imgAtividade2, imgAtividade3, imgAtividade4, imgAtividade5;
    private ImageView imgAtividade6, imgAtividade7, imgAtividade8, imgAtividade9, imgAtividade10;

    int indice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_atividades);

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
                    ListaAtividadesActivity.this.usuario = usuario;
                    txtAtividadesTitulo.setText(String.format("Atividade - %s", usuario.getNome()));
                    usuarioViewModel.getAtividadesById().observe(ListaAtividadesActivity.this, new Observer<List<Atividade>>() {
                        @Override
                        public void onChanged(List<Atividade> listAtividades) {
                            for (Atividade ativ: listAtividades){
                                if(ativ!=null){
                                    ListaAtividadesActivity.this.atividade = ativ;
                                    apresentarHistorico(atividade);
                                }
                            }
                        }
                    });
                }else{
                    startActivity(new Intent(ListaAtividadesActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void configurarTela() {
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Histórico de Atividades");
        txtAtividadesTitulo = findViewById(R.id.txt_lista_atividades_nome);
        txtAtividadesDefault = findViewById(R.id.txt_lista_atividades_default);

        llAtividade1 = findViewById(R.id.ll_lista_atividades_1); llAtividade2 = findViewById(R.id.ll_lista_atividades_2);
        llAtividade3 = findViewById(R.id.ll_lista_atividades_3); llAtividade4 = findViewById(R.id.ll_lista_atividades_4);
        llAtividade5 = findViewById(R.id.ll_lista_atividades_5); llAtividade6 = findViewById(R.id.ll_lista_atividades_6);
        llAtividade7 = findViewById(R.id.ll_lista_atividades_7); llAtividade8 = findViewById(R.id.ll_lista_atividades_8);
        llAtividade9 = findViewById(R.id.ll_lista_atividades_9); llAtividade10 = findViewById(R.id.ll_lista_atividades_10);

        llAtividade1.setVisibility(View.INVISIBLE); llAtividade2.setVisibility(View.INVISIBLE);
        llAtividade3.setVisibility(View.INVISIBLE); llAtividade4.setVisibility(View.INVISIBLE);
        llAtividade5.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
        llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
        llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);

        txtDuracao1 = findViewById(R.id.txt_lista_duracao_1); txtDuracao2 = findViewById(R.id.txt_lista_duracao_2);
        txtDuracao3 = findViewById(R.id.txt_lista_duracao_3); txtDuracao4 = findViewById(R.id.txt_lista_duracao_4);
        txtDuracao5 = findViewById(R.id.txt_lista_duracao_5); txtDuracao6 = findViewById(R.id.txt_lista_duracao_6);
        txtDuracao7 = findViewById(R.id.txt_lista_duracao_7); txtDuracao8 = findViewById(R.id.txt_lista_duracao_8);
        txtDuracao9 = findViewById(R.id.txt_lista_duracao_9); txtDuracao10 = findViewById(R.id.txt_lista_duracao_10);

        txtDistancia1 = findViewById(R.id.txt_lista_distancia_1); txtDistancia2 = findViewById(R.id.txt_lista_distancia_2);
        txtDistancia3 = findViewById(R.id.txt_lista_distancia_3); txtDistancia4 = findViewById(R.id.txt_lista_distancia_4);
        txtDistancia5 = findViewById(R.id.txt_lista_distancia_5); txtDistancia6 = findViewById(R.id.txt_lista_distancia_6);
        txtDistancia7 = findViewById(R.id.txt_lista_distancia_7); txtDistancia8 = findViewById(R.id.txt_lista_distancia_8);
        txtDistancia9 = findViewById(R.id.txt_lista_distancia_9); txtDistancia10 = findViewById(R.id.txt_lista_distancia_10);

        txtCategoria1 = findViewById(R.id.txt_lista_categoria_1); txtCategoria2 = findViewById(R.id.txt_lista_categoria_2);
        txtCategoria3 = findViewById(R.id.txt_lista_categoria_3); txtCategoria4 = findViewById(R.id.txt_lista_categoria_4);
        txtCategoria5 = findViewById(R.id.txt_lista_categoria_5); txtCategoria6 = findViewById(R.id.txt_lista_categoria_6);
        txtCategoria7 = findViewById(R.id.txt_lista_categoria_7); txtCategoria8 = findViewById(R.id.txt_lista_categoria_8);
        txtCategoria9 = findViewById(R.id.txt_lista_categoria_9); txtCategoria10 = findViewById(R.id.txt_lista_categoria_10);

        txtData1 = findViewById(R.id.txt_lista_data_1); txtData2 = findViewById(R.id.txt_lista_data_2);
        txtData3 = findViewById(R.id.txt_lista_data_3); txtData4 = findViewById(R.id.txt_lista_data_4);
        txtData5 = findViewById(R.id.txt_lista_data_5); txtData6 = findViewById(R.id.txt_lista_data_6);
        txtData7 = findViewById(R.id.txt_lista_data_7); txtData8 = findViewById(R.id.txt_lista_data_8);
        txtData9 = findViewById(R.id.txt_lista_data_9); txtData10 = findViewById(R.id.txt_lista_data_10);

        imgAtividade1 = findViewById(R.id.lista_atividades_img_1); imgAtividade2 = findViewById(R.id.lista_atividades_img_2);
        imgAtividade3 = findViewById(R.id.lista_atividades_img_3); imgAtividade4 = findViewById(R.id.lista_atividades_img_4);
        imgAtividade5 = findViewById(R.id.lista_atividades_img_5); imgAtividade6 = findViewById(R.id.lista_atividades_img_6);
        imgAtividade7 = findViewById(R.id.lista_atividades_img_7); imgAtividade8 = findViewById(R.id.lista_atividades_img_8);
        imgAtividade9 = findViewById(R.id.lista_atividades_img_9); imgAtividade10 = findViewById(R.id.lista_atividades_img_10);
    }

    private void apresentarHistorico(Atividade atividade) {
        switch (indice){
            case 0:
                txtAtividadesDefault.setVisibility(View.INVISIBLE); llAtividade2.setVisibility(View.INVISIBLE);
                llAtividade3.setVisibility(View.INVISIBLE); llAtividade4.setVisibility(View.INVISIBLE);
                llAtividade5.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade1.setVisibility(View.VISIBLE);
                txtDuracao1.setText(String.valueOf(atividade.getDuracao())); txtDistancia1.setText(atividade.getDistancia().toString());
                txtCategoria1.setText(atividade.getCategoria().name()); txtData1.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade1.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade1.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade1.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade1.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 1:
                txtAtividadesDefault.setVisibility(View.INVISIBLE);
                llAtividade3.setVisibility(View.INVISIBLE); llAtividade4.setVisibility(View.INVISIBLE);
                llAtividade5.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade2.setVisibility(View.VISIBLE);
                txtDuracao2.setText(String.valueOf(atividade.getDuracao())); txtDistancia2.setText(atividade.getDistancia().toString());
                txtCategoria2.setText(atividade.getCategoria().name()); txtData2.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade2.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade2.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade2.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade2.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 2:
                txtAtividadesDefault.setVisibility(View.INVISIBLE); llAtividade4.setVisibility(View.INVISIBLE);
                llAtividade5.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade3.setVisibility(View.VISIBLE);
                txtDuracao3.setText(String.valueOf(atividade.getDuracao())); txtDistancia3.setText(atividade.getDistancia().toString());
                txtCategoria3.setText(atividade.getCategoria().name()); txtData3.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade3.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade3.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade3.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade3.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 3:
                txtAtividadesDefault.setVisibility(View.INVISIBLE);
                llAtividade5.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade4.setVisibility(View.VISIBLE);
                txtDuracao4.setText(String.valueOf(atividade.getDuracao())); txtDistancia4.setText(atividade.getDistancia().toString());
                txtCategoria4.setText(atividade.getCategoria().name()); txtData4.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade4.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade4.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade4.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade4.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 4:
                txtAtividadesDefault.setVisibility(View.INVISIBLE); llAtividade6.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade5.setVisibility(View.VISIBLE);
                txtDuracao5.setText(String.valueOf(atividade.getDuracao())); txtDistancia5.setText(atividade.getDistancia().toString());
                txtCategoria5.setText(atividade.getCategoria().name()); txtData5.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade5.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade5.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade5.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade5.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 5:
                txtAtividadesDefault.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade6.setVisibility(View.VISIBLE);
                txtDuracao6.setText(String.valueOf(atividade.getDuracao())); txtDistancia6.setText(atividade.getDistancia().toString());
                txtCategoria6.setText(atividade.getCategoria().name()); txtData6.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade6.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade6.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade6.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade6.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 6:
                txtAtividadesDefault.setVisibility(View.INVISIBLE); llAtividade8.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade7.setVisibility(View.VISIBLE);
                txtDuracao7.setText(String.valueOf(atividade.getDuracao())); txtDistancia7.setText(atividade.getDistancia().toString());
                txtCategoria7.setText(atividade.getCategoria().name()); txtData7.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade7.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade7.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade7.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade7.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 7:
                txtAtividadesDefault.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade8.setVisibility(View.VISIBLE);
                txtDuracao8.setText(String.valueOf(atividade.getDuracao())); txtDistancia8.setText(atividade.getDistancia().toString());
                txtCategoria8.setText(atividade.getCategoria().name()); txtData8.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade8.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade8.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade8.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade8.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 8:
                txtAtividadesDefault.setVisibility(View.INVISIBLE); llAtividade10.setVisibility(View.INVISIBLE);
                llAtividade9.setVisibility(View.VISIBLE);
                txtDuracao9.setText(String.valueOf(atividade.getDuracao())); txtDistancia9.setText(atividade.getDistancia().toString());
                txtCategoria9.setText(atividade.getCategoria().name()); txtData9.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade9.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade9.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade9.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade9.setImageResource(R.drawable.ic_natacao);
                }
                break;
            case 9:
                txtAtividadesDefault.setVisibility(View.INVISIBLE);
                llAtividade10.setVisibility(View.VISIBLE);
                txtDuracao10.setText(String.valueOf(atividade.getDuracao())); txtDistancia10.setText(atividade.getDistancia().toString());
                txtCategoria10.setText(atividade.getCategoria().name()); txtData10.setText(atividade.getData());

                if(atividade.getCategoria().name().equals("CAMINHADA")){
                    imgAtividade10.setImageResource(R.drawable.ic_caminhada);
                }else if(atividade.getCategoria().name().equals("CORRIDA")){
                    imgAtividade10.setImageResource(R.drawable.ic_corrida);
                }else if(atividade.getCategoria().name().equals("CICLISMO")){
                    imgAtividade10.setImageResource(R.drawable.ic_ciclismo);
                }else{
                    imgAtividade10.setImageResource(R.drawable.ic_natacao);
                }
                break;
        }
        indice++;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
