package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public interface SearchListener {
    public void onRefreshSearchPT(ArrayList<PontoTuristico> pontoTuristicos, String pesquisa);

}
