package helloworld.amsi.ipleiria.cultravel.vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;

public class UserProfileFragment extends Fragment implements UserListener {

    private FragmentManager fragmentManager;
    private TextView tv_Email;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String email = sharedPreferencesUser.getString(MenuMainActivity.EMAIL, null);
        fragmentManager = getFragmentManager();
        tv_Email = view.findViewById(R.id.tv_Email);
        tv_Email.setText(email);

        SingletonGestorCultravel.getInstance(getContext()).setUserListener(this);

        Button btnLogout = view.findViewById(R.id.btn_terminarSessao);
        Button btn_apagarConta = view.findViewById(R.id.btn_apagarConta);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarSessao();

                Toast.makeText(getContext(), "Terminou a sessão com sucesso!", Toast.LENGTH_LONG).show();
            }
        });

        btn_apagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);

                dialogRemoverPontoTuristico(token);
            }
        });


        return view;
    }

    public void terminarSessao(){
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();

        editor.clear().apply();

        Fragment fragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();

    }

    private void dialogRemoverPontoTuristico(final String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Apagar Conta")
                .setMessage("Tem a certeza que pretende apagar a conta?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonGestorCultravel.getInstance(getContext()).apagarContaAPI(token, getContext());

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }


    @Override
    public void onUserRegistado(String response) {

    }

    @Override
    public void onValidateLogin(String token, String email) {

    }

    @Override
    public void onRefreshDetalhes() {

    }

    @Override
    public void onApagarConta() {
        terminarSessao();
        Toast.makeText(getContext(), "A sua conta foi apagada com sucesso!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onErroLogin() {

    }
}