package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.adaptadores.ListaPontoTuristicoAdaptador;
import helloworld.amsi.ipleiria.cultravel.listeners.PontosTuristicosListener;
import helloworld.amsi.ipleiria.cultravel.listeners.SearchListener;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;

public class ListaPontosTuristicosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PontosTuristicosListener, SearchListener {

    private ListView lvListaPT;
    private ArrayList<PontoTuristico> pontosTuristicos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvResultadoProcura;

    public ListaPontosTuristicosFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_lista_pontos_turisticos, container, false);

        lvListaPT = view.findViewById(R.id.lvListaPontosTuristicos);
        tvResultadoProcura = view.findViewById(R.id.tvResultadoProcura);

        SingletonGestorCultravel.getInstance(getContext()).setPontosTuristicosListener(this);
        SingletonGestorCultravel.getInstance(getContext()).setSearchListener(this);
        /*SingletonGestorCultravel.getInstance(getContext()).getAllPontosTuristicosAPI(getContext());*/

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        lvListaPT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onResume() {
        super.onResume();

        SingletonGestorCultravel.getInstance(getContext()).setPontosTuristicosListener(this);
    }

    @Override
    public void onRefresh() {
        SingletonGestorCultravel.getInstance(getContext()).getPontosTuristicosFavoritosBD();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshListaPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos) {
        if (pontoTuristicos != null) {
            lvListaPT.setAdapter(new ListaPontoTuristicoAdaptador(getContext(), pontoTuristicos));
        }
    }

    @Override
    public void onRefreshDetalhes() {

    }

    @Override
    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos) {

    }

    @Override
    public void onRefreshSearchPT(ArrayList<PontoTuristico> pontosTuristicos, String pesquisa) {
        tvResultadoProcura.setText(pesquisa);
        lvListaPT.setAdapter(new ListaPontoTuristicoAdaptador(getContext(), pontosTuristicos));
    }
}
