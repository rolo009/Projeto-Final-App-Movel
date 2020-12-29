package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.SearchListener;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;
import helloworld.amsi.ipleiria.cultravel.utils.PontoTuristicoParserJson;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private EditText etLocalidade;
    private FragmentManager fragmentManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etLocalidade = view.findViewById(R.id.etLocalidade);

        Button button = view.findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (GenericoParserJson.isConnectionInternet(getContext())) {

                    String pesquisa = etLocalidade.getText().toString();

                    SingletonGestorCultravel.getInstance(getContext()).searchPontosTuristicosAPI(pesquisa, getContext());

                    Fragment fragment = new ListaPontosTuristicosFragment();
                    fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();

                } else {
                    Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}