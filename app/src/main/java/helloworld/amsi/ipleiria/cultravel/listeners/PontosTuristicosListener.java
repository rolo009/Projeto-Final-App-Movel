package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public interface PontosTuristicosListener {

    public void onRefreshListaPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos);

    void onRefreshDetalhes();

    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos);


}
