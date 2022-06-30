package br.edu.ifsp.arq.ifitness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.edu.ifsp.arq.ifitness.mask.MaskEditUtil;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;

public class UsuarioPerfilActivity extends AppCompatActivity {

    private final int REQUEST_TAKE_PHOTO = 1;

    private Toolbar toolbar;
    private TextView txtTitulo;

    private TextInputEditText txtNome;
    private TextInputEditText txtSobrenome;
    private TextInputEditText txtEmail;
    private TextInputEditText txtDataNasc;
    private Spinner spnSexo;
    private TextInputEditText txtTelefone;
    private Button btnAtualizar;
    private ImageView imagePerfil;

    private Uri photoURI;

    private UsuarioViewModel usuarioViewModel;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_perfil_titulo);
        txtNome = findViewById(R.id.txt_edit_perfil_nome);
        txtSobrenome = findViewById(R.id.txt_edit_perfil_sobrenome);
        txtEmail = findViewById(R.id.txt_edit_perfil_email);
        txtDataNasc = findViewById(R.id.txt_edit_perfil_dataNasc);
        txtDataNasc.addTextChangedListener(MaskEditUtil.mask(txtDataNasc, MaskEditUtil.FORMAT_DATE));
        spnSexo = findViewById(R.id.sp_sexo);
        txtTelefone = findViewById(R.id.txt_edit_perfil_telefone);
        txtTelefone.addTextChangedListener(MaskEditUtil.mask(txtTelefone, MaskEditUtil.FORMAT_FONE));
        imagePerfil = findViewById(R.id.iv_profile_image);
        imagePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        String perfilImage = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(MediaStore.EXTRA_OUTPUT, null);

        if(perfilImage != null){
            photoURI = Uri.parse(perfilImage);
            imagePerfil.setImageURI(photoURI);
        }else{
            imagePerfil.setImageResource(R.drawable.profile_icon);
        }

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if(usuario != null){
                    UsuarioPerfilActivity.this.usuario = usuario;
                    txtNome.setText(usuario.getNome());
                    txtSobrenome.setText(usuario.getSobrenome());
                    txtEmail.setText(usuario.getEmail());
                    txtDataNasc.setText(usuario.getDataNasc());
                    String[] sexo = getResources().getStringArray(R.array.spSexo);
                    for (int i = 0; i < sexo.length; i++){
                        if(sexo[i].equals(usuario.getSexo())){
                            spnSexo.setSelection(i);
                        }
                    }
                    txtTelefone.setText(usuario.getTelefone());

                }else{
                    startActivity(new Intent(UsuarioPerfilActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

        btnAtualizar = findViewById(R.id.btn_usuario_perfil_atualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            photoURI = FileProvider.getUriForFile(this,
                    "br.edu.ifsp.arq.ifitness.fileprovider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("PERFIL_" + timestamp, ".jpg", storageDir);
    }

    public void update(){
        if(!validate()){
            return;
        }

        usuario.setNome(txtNome.getText().toString());
        usuario.setSobrenome(txtSobrenome.getText().toString());
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setDataNasc(txtDataNasc.getText().toString());
        usuario.setSexo(getResources().getStringArray(R.array.spSexo)[spnSexo.getSelectedItemPosition()]);
        usuario.setTelefone(txtTelefone.getText().toString());

        usuarioViewModel.update(usuario);
        Toast.makeText(this, getString(R.string.msg_perfil_sucesso), Toast.LENGTH_SHORT).show();

    }

    private boolean validate(){
        boolean isValid = true;
        if(txtNome.getText().toString().trim().isEmpty()){
            txtNome.setError("Preencha o campo nome");
            isValid = false;
        }else{
            txtNome.setError(null);
        }
        if(txtSobrenome.getText().toString().trim().isEmpty()){
            txtSobrenome.setError("Preencha o campo sobrenome");
            isValid = false;
        }else{
            txtSobrenome.setError(null);
        }
        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmail.setError("Preencha o campo email");
            isValid = false;
        }else{
            txtEmail.setError(null);
        }
        if(txtDataNasc.getText().toString().trim().isEmpty()){
            txtDataNasc.setError("Preencha o campo data de nascimento");
            isValid = false;
        }else if(txtDataNasc.getText().toString().trim().length() != 10){
            txtDataNasc.setError("Data inválida");
            isValid = false;
        }
        else{
            txtDataNasc.setError(null);
        }
        if(txtTelefone.getText().toString().trim().isEmpty()){
            txtTelefone.setError("Preencha o campo telefone");
            isValid = false;
        }else if(txtTelefone.getText().toString().trim().length() != 14){
            txtTelefone.setError("Telefone inválido");
            isValid = false;
        }
        else{
            txtTelefone.setError(null);
        }
        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString(MediaStore.EXTRA_OUTPUT, photoURI.toString())
                .apply();
        imagePerfil.setImageURI(photoURI);
    }
}