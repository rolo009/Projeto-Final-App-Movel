package helloworld.amsi.ipleiria.cultravel.modelos;

import android.content.Context;

import java.util.ArrayList;

public class SingletonGestorCultravel {

    private static SingletonGestorCultravel instance = null;
    private ArrayList<PontoTuristico> pontosTuristicos;

    public static synchronized SingletonGestorCultravel getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorCultravel(context);
        }
        return instance;
    }

    private SingletonGestorCultravel(Context context) {
        gerarPontosTuristicos();
    }

    private void gerarPontosTuristicos() {
        pontosTuristicos = new ArrayList<>();
        pontosTuristicos.add(new PontoTuristico( "Castelo de Leiria", "1135", "Em Leiria", "castelo-de-leiria.jpg"));
    }
}
