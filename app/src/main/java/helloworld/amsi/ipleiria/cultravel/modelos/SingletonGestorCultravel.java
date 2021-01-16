package helloworld.amsi.ipleiria.cultravel.modelos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.SearchListener;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;
import helloworld.amsi.ipleiria.cultravel.utils.PontoTuristicoParserJson;
import helloworld.amsi.ipleiria.cultravel.utils.UtilizadoresParserJson;
import helloworld.amsi.ipleiria.cultravel.vistas.ListaPontosTuristicosFragment;

public class SingletonGestorCultravel {

    private static SingletonGestorCultravel instance = null;
    private ArrayList<PontoTuristico> pontosTuristicos;
    private ArrayList<Utilizador> utilizadores;
    private PontosTuristicosFavoritosBDHelper pontosTuristicosFavoritosBD;
    private static RequestQueue volleyQueue = null; //static para ser fila unica
    private static final String mUrlAPIPontosTuristicos = "http://10.0.2.2:8888/pontosturisticos/info"; //API pontos turisticos
    private static final String mUrlAPISearchPontosTuristicos = "http://10.0.2.2:8888/pontosturisticos/search/"; //API pontos turisticos
    private static final String mUrlAPIPontosTuristicosFavoritos = "http://10.0.2.2:8888/userprofile/favoritos/"; //API pontos turisticos

    private static final String mUrlAPIUtilizadores = "http://10.0.2.2:8888/userprofile"; //API pontos turisticos
    private static final String mUrlAPISearchUsername = "http://10.0.2.2:8888/userprofile/username/"; //API pontos turisticos
    private static final String mUrlAPIUserLogin = "http://10.0.2.2:8888/userprofile/login/"; //API pontos turisticos
    private static final String mUrlAPIRegistarUser = "http://10.0.2.2:8888/userprofile/registo"; //API pontos turisticos
    public PontosTuristicosListener pontosTuristicosListener;
    public SearchListener searchListener;
    public UserListener userListener;

    public static synchronized SingletonGestorCultravel getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorCultravel(context);
            volleyQueue = Volley.newRequestQueue(context); //Cria apenas uma fila de pedidos
        }
        return instance;
    }

    private SingletonGestorCultravel(Context context) {
        pontosTuristicos = new ArrayList<>();
        pontosTuristicosFavoritosBD = new PontosTuristicosFavoritosBDHelper(context);
    }

    public PontoTuristico getPontoTuristico(int id) {
        for (PontoTuristico pt : pontosTuristicos) {
            if (pt.getId() == id) {
                return pt;
            }
        }
        return null;
    }

    public void setPontosTuristicosListener(PontosTuristicosListener pontosTuristicosListener) {
        this.pontosTuristicosListener = pontosTuristicosListener;
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }


    //********* Métodos de acesso à BD Pontos Turisticos Favoritos ****//

    public ArrayList<PontoTuristico> getPontosTuristicosFavoritosBD() {
        pontosTuristicos = pontosTuristicosFavoritosBD.getAllPontosTuristicosFavoritosBD();
        return pontosTuristicos;
    }

    public void adicionarPontoTuristicoFavoritoBD(PontoTuristico pontoTuristicoFavorito) {
        pontosTuristicosFavoritosBD.adicionarPontoTuristicoFavoritoBD(pontoTuristicoFavorito);
    }

    public void adicionarPontosTuristicosFavoritosBD(ArrayList<PontoTuristico> pontosTuristicos) {
        pontosTuristicosFavoritosBD.removerAllPontosTuristicosFavoritosBD();

        for (PontoTuristico pt : pontosTuristicos) {
            adicionarPontoTuristicoFavoritoBD(pt);
        }
    }

    public void removerPontoTuristicoFavoritoBD(int id) {
        PontoTuristico pt = getPontoTuristico(id);

        if (pt != null)
            if (pontosTuristicosFavoritosBD.removerPontoTuristicoFavoritoBD(id)) {
                pontosTuristicos.remove(id);
            }
    }

    /********* Métodos de acesso à API ****/
/*
    public void getAllPontosTuristicosAPI(final Context context) {
        if (!PontoTuristicoParserJson.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPontosTuristicos, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    pontosTuristicos = PontoTuristicoParserJson.parserJsonPontosTuristicos(response);
                    if (pontosTuristicosListener != null) {
                        pontosTuristicosListener.onRefreshListaPontosTuristicos(pontosTuristicos);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            volleyQueue.add(req);
        }
    }
*/
    public void searchPontosTuristicosAPI(final String pesquisa, final Context context) {
        if (!GenericoParserJson.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet!", Toast.LENGTH_SHORT).show();
        } else {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPISearchPontosTuristicos + pesquisa, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                pontosTuristicos = PontoTuristicoParserJson.parserJsonSearchPontosTuristicos(response);

                if (searchListener != null) {
                    searchListener.onRefreshSearchPT(pontosTuristicos, pesquisa);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        volleyQueue.add(req);
    }
    }

    public void searchPontosTuristicosFavoritosAPI(final String pesquisa, final Context context, final String token) {

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPISearchPontosTuristicos + pesquisa, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                pontosTuristicos = PontoTuristicoParserJson.parserJsonSearchPontosTuristicos(response);

                if (searchListener != null) {
                    searchListener.onRefreshSearchPT(pontosTuristicos, pesquisa);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        volleyQueue.add(req);
    }

    public void getAllPontosTuristicosFavoritosAPI(final Context context, String token) {
        if (!GenericoParserJson.isConnectionInternet(context)) {

            if (pontosTuristicosListener != null) {
                pontosTuristicosListener.onRefreshListaFavoritosPontosTuristicos(pontosTuristicosFavoritosBD.getAllPontosTuristicosFavoritosBD());
            }

        } else {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPontosTuristicosFavoritos + token, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                pontosTuristicos = PontoTuristicoParserJson.parserJsonPontosTuristicosFavoritos(response);
                Log.e("Pontos", pontosTuristicos.toString());

                adicionarPontosTuristicosFavoritosBD(pontosTuristicos);

               if (pontosTuristicosListener != null) {
                   pontosTuristicosListener.onRefreshListaFavoritosPontosTuristicos(pontosTuristicos);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        volleyQueue.add(req);
    }
    }



    /********* Métodos de acesso à API - Utilizador****/
    /**
     * Registar User API
     */

    public void registarUserAPI(final Utilizador utilizador, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIRegistarUser, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (userListener != null) {
                    userListener.onUserRegistado();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("primeiroNome", utilizador.getPrimeiroNome());
                params.put("ultimoNome", utilizador.getUltimoNome());
                params.put("dtaNascimento", utilizador.getDtaNascimento()+"");
                params.put("morada", utilizador.getMorada());
                params.put("localidade", utilizador.getLocalidade());
                params.put("distrito", utilizador.getDistrito());
                params.put("sexo", utilizador.getSexo());
                params.put("username", utilizador.getUsername());
                params.put("email", utilizador.getEmail());
                params.put("password", utilizador.getPassword());

                JSONObject userJSON = new JSONObject(params);

                Log.e("USERJSON",userJSON.toString());

                return params;
            }
        };
        volleyQueue.add(req);
    }



    public void getUsernameAPI(final Context context, String username) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPISearchUsername + username, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                utilizadores = UtilizadoresParserJson.parserJsonUtilizadores(response);

                    Log.e("USERJSON",utilizadores.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        volleyQueue.add(req);
    }

    public void loginUserAPI(final String email, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST,  mUrlAPIUserLogin, new Response.Listener<String>() {

            public void onResponse(String response) {
                if(userListener != null){
                    userListener.onValidateLogin(UtilizadoresParserJson.parserJsonLogin(response), email);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        volleyQueue.add(req);
    }
}


