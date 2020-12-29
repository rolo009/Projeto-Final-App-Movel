package helloworld.amsi.ipleiria.cultravel.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import helloworld.amsi.ipleiria.cultravel.R;

public class EditarRegistoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText etPrimeiroNome, etUltimoNome, etDtaNascimento, etNomeUtilizador, etEmail, etPassword, etConfirmarPassword, etMorada, etLocalidade;
    private RadioButton rbtnMasculino, rbtnFeminino;

    public EditarRegistoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registo, container, false);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}