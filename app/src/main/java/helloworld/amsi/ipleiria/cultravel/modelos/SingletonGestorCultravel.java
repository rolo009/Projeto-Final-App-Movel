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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helloworld.amsi.ipleiria.cultravel.listeners.ContactosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.FavoritosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.SearchListener;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;
import helloworld.amsi.ipleiria.cultravel.utils.PontoTuristicoParserJson;
import helloworld.amsi.ipleiria.cultravel.utils.UtilizadoresParserJson;

public class SingletonGestorCultravel {

    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD = 2;
    private static final int REMOVER_BD = 3;

    private static SingletonGestorCultravel instance = null;
    private ArrayList<PontoTuristico> pontosTuristicos;
    private PontosTuristicosFavoritosBDHelper pontosTuristicosFavoritosBD;
    private static RequestQueue volleyQueue = null; //static para ser fila unica
    private static final String mUrlAPISearchPontosTuristicos = "http://10.0.2.2:8888/pontosturisticos/search/";

    private static final String mUrlAPIPontosTuristicosFavoritos = "http://10.0.2.2:8888/favoritos/info/";
    private static final String mUrlAPIAddPontosTuristicosFavoritos = "http://10.0.2.2:8888/favoritos/add/";
    private static final String mUrlAPIRemoverPontosTuristicosFavoritos = "http://10.0.2.2:8888/favoritos/remover";
    private static final String mUrlAPICheckFavoritos = "http://10.0.2.2:8888/favoritos/check";

    private static final String mUrlAPISearchEmail = "http://10.0.2.2:8888/userprofile/email/";
    private static final String mUrlAPIUserLogin = "http://10.0.2.2:8888/userprofile/login";
    private static final String mUrlAPIRegistarUser = "http://10.0.2.2:8888/userprofile/registo";
    private static final String mUrlAPIEditarRegistoUser = "http://10.0.2.2:8888/userprofile/editar/";
    private static final String mUrlAPIApagarUser = "http://10.0.2.2:8888/userprofile/apagaruser/";
    private static final String mUrlAPIUserInfo = "http://10.0.2.2:8888/userprofile/user/";

    private static final String mUrlAPIContacto = "http://10.0.2.2:8888/contactos/registo";
    public PontosTuristicosListener pontosTuristicosListener;
    public SearchListener searchListener;
    public UserListener userListener;
    public FavoritosListener favoritosListener;


    public ContactosListener contactosListener;

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

    public void setContactosListener(ContactosListener contactosListener) {
        this.contactosListener = contactosListener;
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public void setFavoritosListener(FavoritosListener favoritosListener) {
        this.favoritosListener = favoritosListener;
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }


    //********* Métodos de acesso à BD Pontos Turisticos Favoritos ****//

    public ArrayList<PontoTuristico> getPontosTuristicosFavoritosBD() {
        pontosTuristicos = pontosTuristicosFavoritosBD.getAllPontosTuristicosFavoritosBD();
        Log.e("PT", pontosTuristicos+"");
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

    public void editarPontoTuristicoFavoritoBD(PontoTuristico pontoTuristico) {
        PontoTuristico pt = getPontoTuristico(pontoTuristico.getId());

        if (pt != null) {
            if (pontosTuristicosFavoritosBD.editarPontoTuristicoFavoritoBD(pontoTuristico)) {
                pt.setNome(pontoTuristico.getNome());
                pt.setAnoConstrucao(pontoTuristico.getAnoConstrucao());
                pt.setLocalidade(pontoTuristico.getLocalidade());
                pt.setDescricao(pontoTuristico.getDescricao());
                pt.setEstiloConstrucao(pontoTuristico.getEstiloConstrucao());
                pt.setTipoMonumento(pontoTuristico.getTipoMonumento());
                pt.setLatitude(pontoTuristico.getLatitude());
                pt.setLongitude(pontoTuristico.getLongitude());
                pt.setFoto(pontoTuristico.getFoto());
            }
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

    public void getAllPontosTuristicosFavoritosAPI(final Context context, String token) {
        if (!GenericoParserJson.isConnectionInternet(context)) {

            if (favoritosListener != null) {
                favoritosListener.onRefreshListaFavoritosPontosTuristicos(pontosTuristicosFavoritosBD.getAllPontosTuristicosFavoritosBD());
            }

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPontosTuristicosFavoritos + token, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    pontosTuristicos = PontoTuristicoParserJson.parserJsonPontosTuristicosFavoritos(response);
                    adicionarPontosTuristicosFavoritosBD(pontosTuristicos);
                    if (favoritosListener != null) {
                        favoritosListener.onRefreshListaFavoritosPontosTuristicos(pontosTuristicos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Não tem nenhum Ponto Turistico adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void adicionarPontoTuristicoFavoritoAPI(final Context context, final PontoTuristico pontoTuristico, final String token) {
        if (!GenericoParserJson.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet!", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIAddPontosTuristicosFavoritos, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarPontoTuristicoFavoritoBD(pontoTuristico);
                    if (favoritosListener != null) {
                        favoritosListener.onAddPTFavoritos();
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_pontoTuristico", pontoTuristico.getId() + "");
                    params.put("token", token);
                    /*
                    JSONObject param = new JSONObject(params);
                    Log.e("MAP:", param+"");*/

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void removerPontoTuristicoFavoritoAPI(final Context applicationContext, PontoTuristico pontoTuristico, String token) {
        if (!GenericoParserJson.isConnectionInternet(applicationContext)) {
            Toast.makeText(applicationContext, "Sem internet!", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIRemoverPontosTuristicosFavoritos + "/" + pontoTuristico.getId() + "/" + token, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (favoritosListener != null) {
                        favoritosListener.onRemoverPTFavoritos();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void checkFavoritoAPI(final Context applicationContext, final PontoTuristico pontoTuristico, String token) {
        if (!GenericoParserJson.isConnectionInternet(applicationContext)) {


        } else {
            StringRequest req = new StringRequest(Request.Method.GET, mUrlAPICheckFavoritos + "/" + pontoTuristico.getId() + "/" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean fav = response.equals("true") ? true : false;

                    if (favoritosListener != null)
                        favoritosListener.oncheckPtFavorito(fav);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        userListener.onUserRegistado(response);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("primeiroNome", utilizador.getPrimeiroNome());
                params.put("ultimoNome", utilizador.getUltimoNome());
                params.put("dtaNascimento", utilizador.getDtaNascimento() + "");
                params.put("morada", utilizador.getMorada());
                params.put("localidade", utilizador.getLocalidade());
                params.put("distrito", utilizador.getDistrito());
                params.put("sexo", utilizador.getSexo());
                params.put("username", utilizador.getUsername());
                params.put("email", utilizador.getEmail());
                params.put("password", utilizador.getPassword());

                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void editarUtilizadorAPI(final Utilizador utilizador, final Context context, final String token) {
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPISearchEmail + "/" + utilizador.getEmail(), new Response.Listener<String>() {

            public void onResponse(String response) {

                if (userListener != null) {
                    userListener.onRefreshDetalhes();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("primeiroNome", utilizador.getPrimeiroNome());
                params.put("ultimoNome", utilizador.getUltimoNome());
                params.put("dtaNascimento", utilizador.getDtaNascimento() + "");
                params.put("morada", utilizador.getMorada());
                params.put("localidade", utilizador.getLocalidade());
                params.put("distrito", utilizador.getDistrito());
                params.put("sexo", utilizador.getSexo());
                params.put("username", utilizador.getUsername());
                params.put("email", utilizador.getEmail());
                params.put("password", utilizador.getPassword());
                params.put("token", token);
                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void loginUserAPI(final String email, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIUserLogin, new Response.Listener<String>() {

            public void onResponse(String response) {
                if (userListener != null) {
                    userListener.onValidateLogin(UtilizadoresParserJson.parserJsonLogin(response), email);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (userListener != null) {
                    userListener.onErroLogin();
                }
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

    public void registarContactoAPI(final Contacto contacto, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIContacto, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (contactosListener != null) {
                    contactosListener.onContactoRegistado();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", contacto.getNome());
                params.put("email", contacto.getEmail());
                params.put("assunto", contacto.getAssunto() + "");
                params.put("mensagem", contacto.getMensagem());

                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void apagarContaAPI(String token, final Context context) {
        StringRequest req = new StringRequest(Request.Method.PATCH, mUrlAPIApagarUser + token, new Response.Listener<String>() {

            public void onResponse(String response) {
                if (userListener != null) {
                    userListener.onApagarConta();
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

    public void onUpdateListaFavoritosBD(PontoTuristico pontoTuristico, int operacao) {
        switch (operacao) {
            case ADICIONAR_BD:
                adicionarPontoTuristicoFavoritoBD(pontoTuristico);
                break;
            case EDITAR_BD:
                editarPontoTuristicoFavoritoBD(pontoTuristico);
                break;
            case REMOVER_BD:
                removerPontoTuristicoFavoritoBD(pontoTuristico.getId());
                break;
        }
    }



}


