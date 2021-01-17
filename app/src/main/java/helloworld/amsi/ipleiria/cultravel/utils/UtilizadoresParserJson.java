package helloworld.amsi.ipleiria.cultravel.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;

public class UtilizadoresParserJson {
    public static ArrayList<Utilizador> parserJsonUtilizadores(JSONArray response) {

        ArrayList<Utilizador> utilizadores = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject utilizador = (JSONObject) response.get(i);
                int id = utilizador.getInt("id_pontoTuristico");
                String primeiroNome = utilizador.getString("primeiroNome");
                String ultimoNome = utilizador.getString("ultimoNome");
                String dtaNascimento = utilizador.getString("dtaNascimento");
                String username = utilizador.getString("username");
                String email = utilizador.getString("email");
                String password = utilizador.getString("password");
                String morada = utilizador.getString("morada");
                String localidade = utilizador.getString("localidade");
                String distrito = utilizador.getString("distrito");
                String sexo = utilizador.getString("sexo");

                Utilizador auxUtilizador = new Utilizador(id, primeiroNome, ultimoNome, dtaNascimento,username, email, password, morada, localidade, distrito, sexo);
                utilizadores.add(auxUtilizador);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return utilizadores;
    }

    public static Utilizador parserJsonUtilizador(String response) {
        Utilizador auxUtilizador = null;

        try {
            JSONObject utilizador = new JSONObject(response);
            String primeiroNome = utilizador.getString("primeiroNome");
            String ultimoNome = utilizador.getString("ultimoNome");
            String username = utilizador.getString("username");
            String email = utilizador.getString("email");
            String dtaNascimento = utilizador.getString("dtaNascimento");
            String morada = utilizador.getString("morada");
            String localidade = utilizador.getString("localidade");
            String distrito = utilizador.getString("distrito");
            String sexo = utilizador.getString("sexo");

            auxUtilizador = new Utilizador(0, primeiroNome, ultimoNome, dtaNascimento,username, email, null, morada, localidade, distrito, sexo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxUtilizador;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject login = new JSONObject(response);
                token = login.getString("verification_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }
}
