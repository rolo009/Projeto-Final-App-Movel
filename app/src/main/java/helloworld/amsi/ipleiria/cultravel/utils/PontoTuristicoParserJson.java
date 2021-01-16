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
    public static ArrayList<PontoTuristico> parserJsonPontosTuristicosFavoritos(JSONArray response) {

        ArrayList<PontoTuristico> pontosTuristicos = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject pontoturistico = (JSONObject) response.get(i);
                int id = pontoturistico.getInt("id_pontoTuristico");
                String nome = pontoturistico.getString("nome");
                String anoConstrucao = pontoturistico.getString("anoConstrucao");
                String descricao = pontoturistico.getString("descricao");
                String localidade = pontoturistico.getString("localidade_idLocalidade");
                String estiloConstrucao = pontoturistico.getString("ec_idEstiloConstrucao");
                String tipoMonumento = pontoturistico.getString("tm_idTipoMonumento");
                String foto = pontoturistico.getString("foto");
                String latitude = pontoturistico.getString("latitude");
                String longitude = pontoturistico.getString("longitude");

                PontoTuristico auxPontoTuristico = new PontoTuristico(id, nome, anoConstrucao, descricao, localidade, tipoMonumento, estiloConstrucao, foto, latitude, longitude, 0);
                pontosTuristicos.add(auxPontoTuristico);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pontosTuristicos;
    }

    public static ArrayList<PontoTuristico> parserJsonSearchPontosTuristicos(JSONArray response) {

            ArrayList<PontoTuristico> pontosTuristicosSearch = new ArrayList<>();

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject pontoturistico = (JSONObject) response.get(i);
                    int id = pontoturistico.getInt("id_pontoTuristico");
                    String nome = pontoturistico.getString("nome");
                    String anoConstrucao = pontoturistico.getString("anoConstrucao");
                    String descricao = pontoturistico.getString("descricao");
                    String localidade = pontoturistico.getString("localidade_idLocalidade");
                    String estiloConstrucao = pontoturistico.getString("ec_idEstiloConstrucao");
                    String tipoMonumento = pontoturistico.getString("tm_idTipoMonumento");
                    String foto = pontoturistico.getString("foto");
                    String latitude = pontoturistico.getString("latitude");
                    String longitude = pontoturistico.getString("longitude");
                    int status = pontoturistico.getInt("status");


                    PontoTuristico auxPontoTuristico = new PontoTuristico(id, nome,anoConstrucao,descricao, localidade,tipoMonumento,estiloConstrucao, foto, latitude, longitude, status);
                    pontosTuristicosSearch.add(auxPontoTuristico);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return pontosTuristicosSearch;
        }


}
