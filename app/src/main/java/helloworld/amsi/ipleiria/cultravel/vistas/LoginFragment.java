package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements UserListener {

    private EditText etEmailLogin, etPasswordLogin;
    private FragmentManager fragmentManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        SingletonGestorCultravel.getInstance(getContext()).setUserListener(this);

        fragmentManager = getFragmentManager();
        etEmailLogin = view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GenericoParserJson.isConnectionInternet(getContext())) {
                    String email = etEmailLogin.getText().toString();
                    String password = etPasswordLogin.getText().toString();

                    if (!isEmailValido(email)) {
                        etEmailLogin.setError("Email Inválido");
                        return;
                    }

                    if (!isPasswordValida(password)) {
                        etPasswordLogin.setError("Palavra Passe Inválida");
                        return;
                    }

                    SingletonGestorCultravel.getInstance(getContext()).loginUserAPI(email, password, getContext());

                }
            }
        });

        Button btnRegistar = view.findViewById(R.id.btnRegistar);
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistoFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    private boolean isEmailValido(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String password) {
        if (password == null) {
            return true;
        }
        return password.length() > 0;

    }

    @Override
    public void onUserRegistado(String response) {

    }

    public void onValidateLogin(String token, String email) {

        if (token != null) {
            guardarInfoSharedPref(token, email);
            Fragment fragment = new SearchFragment();
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
            Toast.makeText(getContext(), "Bem Vindo!", Toast.LENGTH_LONG).show();
        } else {
            etPasswordLogin.setError("Utilizador ou Palavra-Passe Incorretos!");
        }
    }

    @Override
    public void onRefreshDetalhes(String response) {

    }

    @Override
    public void onApagarConta() {

    }

    @Override
    public void onErroLogin() {
        Toast.makeText(getContext(), "A sua conta não cumpre os requisitos para que seja possivel iniciar sessão! Para mais informações contacto o suporte.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLoadEditarRegisto(Utilizador utilizador) {

    }

    private void guardarInfoSharedPref(String token, String email) {
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();

        editor.putString(MenuMainActivity.EMAIL, email);
        editor.putString(MenuMainActivity.TOKEN, token);

        editor.apply();
    }
}