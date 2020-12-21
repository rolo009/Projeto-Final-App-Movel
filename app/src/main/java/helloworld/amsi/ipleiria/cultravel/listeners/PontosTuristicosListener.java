package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public interface PontosTuristicosListener {
    public void onRefreshListaLivros(ArrayList<PontoTuristico> pontoTuristicos);

    void onRefreshDetalhes();


}
