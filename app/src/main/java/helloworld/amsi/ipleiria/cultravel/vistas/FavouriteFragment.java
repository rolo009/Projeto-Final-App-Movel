package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.adaptadores.ListaFavoritoAdaptador;
import helloworld.amsi.ipleiria.cultravel.adaptadores.ListaPontoTuristicoAdaptador;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;


public class FavouriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PontosTuristicosListener {

    private ListView lvListaFavoritos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<PontoTuristico> listaFavoritos;
    private PontoTuristico pontoTuristico;


    public FavouriteFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        SingletonGestorCultravel.getInstance(getContext()).setPontosTuristicosListener(this);

        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);

        SingletonGestorCultravel.getInstance(getContext()).getAllPontosTuristicosFavoritosAPI(getContext(), token);

        lvListaFavoritos = view.findViewById(R.id.lvListaFavoritos);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        lvListaFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PontoTuristicoDetailsActivity.class);
                intent.putExtra(PontoTuristicoDetailsActivity.ID, (int) id);
                startActivity(intent);
            }
        });
        if (listaFavoritos != null) {
            Button btnRemoverFavoritos = (Button) view.findViewById(R.id.btn_removerFavoritos);
            btnRemoverFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingletonGestorCultravel.getInstance(getContext()).removerPontoTuristicoFavoritoBD(pontoTuristico.getId());

                }
            });
        }

        lvListaFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PontoTuristicoDetailsActivity.class);
                intent.putExtra(PontoTuristicoDetailsActivity.ID, (int) id);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onRefresh() {
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
        SingletonGestorCultravel.getInstance(getContext()).getAllPontosTuristicosFavoritosAPI(getContext(), token);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onClickRemoverPtFavoritos(View view) {

    }

    @Override
    public void onRefreshListaPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos) {

    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorCultravel.getInstance(getContext()).setPontosTuristicosListener(this);
    }

    @Override
    public void onRefreshDetalhes() {

    }

    @Override
    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> listaFavoritos) {
        if (listaFavoritos != null) {
            lvListaFavoritos.setAdapter(new ListaFavoritoAdaptador(getContext(), listaFavoritos));
        }
    }
}