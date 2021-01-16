package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public interface FavoritosListener {

    public void onAddPTFavoritos();

    public void onRemoverPTFavoritos();

    public void oncheckPtFavorito(Boolean favorito);

    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos);


}
