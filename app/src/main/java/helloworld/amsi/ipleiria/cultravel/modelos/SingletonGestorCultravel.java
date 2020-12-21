package helloworld.amsi.ipleiria.cultravel.modelos;

import android.content.Context;
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

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.utils.PontoTuristicoParserJson;
import helloworld.amsi.ipleiria.cultravel.vistas.ListaPontosTuristicosFragment;

public class SingletonGestorCultravel {

    private static SingletonGestorCultravel instance = null;
    private ArrayList<PontoTuristico> pontosTuristicos;
    private PontosTuristicosFavoritosBDHelper pontosTuristicosFavoritosBD;
    private static RequestQueue volleyQueue = null; //static para ser fila unica
    private static final String mUrlAPIPontosTuristicos = "http://10.0.2.2:8888/pontosturisticos"; //API pontos turisticos
    private static final String mUrlAPIUtilizadores = "http://10.0.2.2:8888/"; //API pontos turisticos
    //private static final String TOKEN = "AMSI-TOKEN"; Token login
    //public PontosTuristicosListener pontosTuristicosListener;
    public PontosTuristicosListener pontosTuristicosListener;

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

    public void setLivrosListener(PontosTuristicosListener pontosTuristicosListener) {
        this.pontosTuristicosListener = pontosTuristicosListener;
    }

    //********* Métodos de acesso à BD Pontos Turisticos Favoritos ****//

    public ArrayList<PontoTuristico> getPontosTuristicosFavoritosBD() {
        pontosTuristicos = pontosTuristicosFavoritosBD.getAllPontosTuristicosFavoritosBD();
        return pontosTuristicos;
    }

    public void adicionarPontoTuristicoFavoritoBD(PontoTuristico pontoTuristicoFavorito, Utilizador utilizador) {
        PontoTuristico pt = pontosTuristicosFavoritosBD.adicionarPontoTuristicoFavoritoBD(pontoTuristicoFavorito, utilizador);
        if (pt != null) {
            pontosTuristicos.add(pt);
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

    public void getAllPontosTuristicosAPI(final Context context) {
        if (!PontoTuristicoParserJson.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPontosTuristicos, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    pontosTuristicos = PontoTuristicoParserJson.parserJsonPontosTuristicos(response);

                    if (pontosTuristicosListener != null) {
                        pontosTuristicosListener.onRefreshListaLivros(pontosTuristicos);
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

}
