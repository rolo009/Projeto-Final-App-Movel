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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.adaptadores.ListaFavoritoAdaptador;
import helloworld.amsi.ipleiria.cultravel.listeners.FavoritosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;

public class FavouriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavoritosListener {

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

        lvListaFavoritos = view.findViewById(R.id.lvListaFavoritos);

        SingletonGestorCultravel.getInstance(getContext()).setFavoritosListener(this);

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

        return view;
    }

    @Override
    public void onRefresh() {
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
        SingletonGestorCultravel.getInstance(getContext()).getAllPontosTuristicosFavoritosAPI(getContext(), token);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorCultravel.getInstance(getContext()).setFavoritosListener(this);
    }

    @Override
    public void onAddPTFavoritos() {

    }

    @Override
    public void onRemoverPTFavoritos() {
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
        SingletonGestorCultravel.getInstance(getContext()).getAllPontosTuristicosFavoritosAPI(getContext(), token);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void oncheckPtFavorito(Boolean favorito) {

    }

    @Override
    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> listaFavoritos) {
        Log.e("T L", listaFavoritos.size()+"");
        if (listaFavoritos != null) {
            lvListaFavoritos.setAdapter(new ListaFavoritoAdaptador(getContext(), listaFavoritos));
        }
    }
}