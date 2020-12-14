package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Intent;
import android.os.Bundle;
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
import helloworld.amsi.ipleiria.cultravel.adaptadores.ListaPontoTuristicoAdaptador;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;


public class FavouriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lvListaFavoritos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<PontoTuristico> listaFavoritos;
    private PontoTuristico pontoTuristico;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        lvListaFavoritos = view.findViewById(R.id.lvListaFavoritos);

        listaFavoritos = SingletonGestorCultravel.getInstance(getContext()).getPontosTuristicosFavoritosBD();
        lvListaFavoritos.setAdapter(new ListaPontoTuristicoAdaptador(getContext(), listaFavoritos));

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


        return view;
    }

    @Override
    public void onRefresh() {
        listaFavoritos = SingletonGestorCultravel.getInstance(getContext()).getPontosTuristicosFavoritosBD();
        lvListaFavoritos.setAdapter(new ListaPontoTuristicoAdaptador(getContext(), listaFavoritos));
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onClickRemoverPtFavoritos(View view) {

    }

}