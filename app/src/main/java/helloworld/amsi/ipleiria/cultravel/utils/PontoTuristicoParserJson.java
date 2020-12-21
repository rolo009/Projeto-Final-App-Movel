package helloworld.amsi.ipleiria.cultravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public class PontoTuristicoParserJson {
    public static ArrayList<PontoTuristico> parserJsonPontosTuristicos(JSONArray response) {

        ArrayList<PontoTuristico> pontosTuristicos = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject pontoturistico = (JSONObject) response.get(i);
                int id = pontoturistico.getInt("id_pontoTuristico");
                String nome = pontoturistico.getString("nome");
                String localidade = pontoturistico.getString("localidade_idLocalidade");
                String foto = pontoturistico.getString("foto");
                int status = pontoturistico.getInt("status");

                PontoTuristico auxPontoTuristico = new PontoTuristico(id, nome,null,null, localidade,null,null,null, foto, null, null, status);
                pontosTuristicos.add(auxPontoTuristico);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pontosTuristicos;
    }

    public static PontoTuristico parserJsonPontoTuristico(String response) {
        PontoTuristico auxPontoTuristico = null;

        try {
            JSONObject pontoturistico = new JSONObject(response);
            int id = pontoturistico.getInt("id");
            String nome = pontoturistico.getString("nome");
            String anoConstrucao = pontoturistico.getString("anoConstrucao");
            String descricao = pontoturistico.getString("descricao");
            String localidade = pontoturistico.getString("localidade");
            String estiloConstrucao = pontoturistico.getString("estiloConstrucao");
            String tipoMonumento = pontoturistico.getString("tipoMonumento");
            String ranking = pontoturistico.getString("tipoMonumento");
            String foto = pontoturistico.getString("foto");
            String latitude = pontoturistico.getString("latitude");
            String longitude = pontoturistico.getString("longitude");
            int status = pontoturistico.getInt("status");

            auxPontoTuristico = new PontoTuristico(id, nome, anoConstrucao, descricao, localidade, estiloConstrucao, tipoMonumento, ranking, foto, latitude, longitude, status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPontoTuristico;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success")) {
                token = login.getString("token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }


}
