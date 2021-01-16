package helloworld.amsi.ipleiria.cultravel.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.ContactosListener;
import helloworld.amsi.ipleiria.cultravel.modelos.Contacto;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;

public class ContactosFragment extends Fragment implements ContactosListener {

    private EditText etNome, etEmail, etAssunto, etMensagem;
    private FragmentManager fragmentManager;
    private Contacto contacto;

    public ContactosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_contactos, container, false);

        SingletonGestorCultravel.getInstance(getContext()).setContactosListener(this);

        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmail);
        etAssunto = view.findViewById(R.id.etAssunto);
        etMensagem = view.findViewById(R.id.etMensagem);

        Button button = view.findViewById(R.id.btnContactar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GenericoParserJson.isConnectionInternet(getContext())) {

                    String nome = etNome.getText().toString();
                    String email = etEmail.getText().toString();
                    String assunto = etAssunto.getText().toString();
                    String mensagem = etMensagem.getText().toString();

                    if (!isNomeValido(nome)) {
                        etNome.setError(getString(R.string.nomeInvalido));
                        return;
                    }
                    if (!isEmailValido(email)) {
                        etEmail.setError(getString(R.string.emailInvalido));
                        return;
                    }
                    if (!isAssuntoValido(assunto)) {
                        etAssunto.setError(getString(R.string.assuntoInvalido));
                        return;
                    }

                    if (!isMensagemValida(mensagem)) {
                        etAssunto.setError(getString(R.string.mensagemInvalida));
                        return;
                    }

                    contacto = new Contacto(nome, email, assunto, mensagem);
                    SingletonGestorCultravel.getInstance(getContext()).registarContactoAPI(contacto, getContext());

                } else {
                    Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    private boolean isNomeValido(String username) {
        if (username == null) {
            return true;
        }
        return username.length() > 0;
    }

    private boolean isEmailValido(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isAssuntoValido(String assunto) {
        if (assunto == null) {
            return true;
        }
        return assunto.length() > 0;
    }

    private boolean isMensagemValida(String mensagem) {
        if (mensagem == null) {
            return true;
        }
        return mensagem.length() > 0;
    }

    @Override
    public void onContactoRegistado() {
        Fragment fragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
        Toast.makeText(getContext(), "A sua mensagem foi enviada com sucesso!", Toast.LENGTH_LONG).show();

    }
}

