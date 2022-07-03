package br.edu.ifsp.arq.ifitness.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.edu.ifsp.arq.ifitness.model.Atividade;
import br.edu.ifsp.arq.ifitness.model.Usuario;
import br.edu.ifsp.arq.ifitness.viewmodel.UsuarioViewModel;


public class UsuariosRepository {
    private static final String BASE_URL = "https://identitytoolkit.googleapis.com/v1/";
    private static final String SIGNUP = "accounts:signUp";
    private static final String SIGNIN = "accounts:signInWithPassword";
    private static final String PASSWORD_RESET = "accounts:sendOobCode";
    private static final String KEY = "?key=AIzaSyAK2BeRgzsasgAKPEYrvND0_GzK90W68Z0";

    private SharedPreferences preference;

    private FirebaseFirestore firestore;

    private RequestQueue queue;

    public UsuariosRepository(Application application) {
        firestore = FirebaseFirestore.getInstance();
        queue = Volley.newRequestQueue(application);
        preference = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public LiveData<Usuario> login(String email, String senha){
        MutableLiveData<Usuario> liveData = new MutableLiveData<>();

        email.trim();

        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("password", senha);
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNIN + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String localId = response.getString("localId");
                            String idToken = response.getString("idToken");

                            firestore.collection("usuario").document(localId).get().addOnSuccessListener(snapshot -> {
                                Usuario usuario = snapshot.toObject(Usuario.class);

                                usuario.setId(localId);
                                usuario.setSenha(idToken);

                                liveData.setValue(usuario);

                                preference.edit().putString(UsuarioViewModel.USUARIO_ID, localId).apply();

                                firestore.collection("usuario").document(localId).set(usuario);
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        liveData.setValue(null);
                    }
                });

        queue.add(request);

        return liveData;
    }

    public void createUsuario(Usuario usuario)  {
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", usuario.getEmail());
            parametros.put("password", usuario.getSenha());
            parametros.put("returnSecureToken", true);
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + SIGNUP + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            usuario.setId(response.getString("localId"));
                            usuario.setSenha(response.getString("idToken"));

                            firestore.collection("usuario").document(usuario.getId()).set(usuario).addOnSuccessListener(unused -> {
                                Log.d(this.toString(), "Usuário " + usuario.getEmail() + "cadastrado com scuesso.");
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(this.toString(), "onErrorResponse: ");
                    }
                });

        queue.add(request);
    }

    public void createAtividade(Atividade atividade)  {
        firestore.collection("atividade").document(atividade.getId()).set(atividade).addOnSuccessListener(unused -> {
            Log.d(this.toString(), "Atividade de " + atividade.getCategoria() + " cadastrada com scuesso.");
        });
    }


    public LiveData<Usuario> load(String usuarioId){
        MutableLiveData<Usuario> liveData = new MutableLiveData<>();

        DocumentReference userRef = firestore.collection("usuario").document(usuarioId);

        userRef.get().addOnSuccessListener(snapshot -> {
            Usuario usuario = snapshot.toObject(Usuario.class);

            usuario.setId(usuario.getId())  ;

            liveData.setValue(usuario);

        });

        return liveData;
    }


    public MutableLiveData<List<Atividade>> getAtividadesById(String usuarioId) {
        MutableLiveData<List<Atividade>> liveData = new MutableLiveData<>();

        Query atividadeRef = firestore.collection("atividade").whereEqualTo("usuarioId", usuarioId).limit(5);

        atividadeRef.get().addOnSuccessListener(snapshot -> {
            List<Atividade> atividades = snapshot.toObjects(Atividade.class);

            liveData.setValue(atividades);
        });

        return liveData;
    }



    public MutableLiveData<List<Usuario>> getUsuarios() {
        MutableLiveData<List<Usuario>> liveData = new MutableLiveData<>();

        Query atividadeRef = firestore.collection("usuario").limit(5).orderBy("pontuacao", Query.Direction.DESCENDING);

        atividadeRef.get().addOnSuccessListener(snapshot -> {
            System.out.println("entrou no repository /n" + snapshot.getDocuments());
            List<Usuario> usuarios = snapshot.toObjects(Usuario.class);

            liveData.setValue(usuarios);
        });

        return liveData;
    }

    public Boolean update(Usuario usuario){
        final Boolean[] atualizado = {false};

        DocumentReference usuarioRef = firestore.collection("usuario").document(usuario.getId());

        usuarioRef.set(usuario).addOnSuccessListener(unused -> {
            atualizado[0] = true;
        });

        return atualizado[0];
    }

    public Boolean updateAtividade(Atividade atividade){
        final Boolean[] atualizado = {false};

        Map<String, Object> updateMap = new ConcurrentHashMap();
        updateMap.put("distancia", atividade.getDistancia());
        updateMap.put("duracao", atividade.getDuracao());

        Task<Void> atividadeRef = firestore.collection("atividade").document(atividade.
                getId()).update(updateMap).addOnSuccessListener(unused -> {
                            atualizado[0] = true;
                        });
        return atualizado[0];
    }

    public Boolean delete(Atividade atividade) {
        final Boolean[] deletado = {false};

        Task<Void> atividadeRef = firestore.collection("atividade").document(atividade.
                getId()).delete().addOnSuccessListener(unused -> {
                            deletado[0] = true;
                        });
        return deletado[0];
    }

    public void resetPassword(String email){
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("email", email);
            parametros.put("requestType", "PASSWORD_RESET");
            parametros.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + PASSWORD_RESET + KEY, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(this.toString(), response.keys().toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Log.d(this.toString(), obj.toString());
                            } catch ( UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                });

        queue.add(request);
    }
}
