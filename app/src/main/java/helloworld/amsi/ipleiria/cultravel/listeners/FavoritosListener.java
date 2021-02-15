package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public interface FavoritosListener {

    void onAddPTFavoritos();

    void onRemoverPTFavoritos();

    void oncheckPtFavorito(Boolean favorito);

    void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos);

    void onNoFavoritos();


}
