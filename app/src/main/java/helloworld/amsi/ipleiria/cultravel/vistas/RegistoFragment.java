package helloworld.amsi.ipleiria.cultravel.vistas;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;


public class RegistoFragment extends Fragment implements UserListener {

    private EditText etPrimeiroNome, etUltimoNome, etDtaNascimento, etNomeUtilizador, etEmail, etPassword, etConfirmarPassword, etMorada, etDistrito, etLocalidade;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private FragmentManager fragmentManager;
    private Utilizador utilizador;

    private Pattern pattern;
    private Matcher matcher;

    public RegistoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_registo, container, false);
        //SingletonGestorCultravel.getInstance(getContext()).setUserListener(this); -- corrigir

        etPrimeiroNome = view.findViewById(R.id.etPrimeiroNome);
        etUltimoNome = view.findViewById(R.id.etUltimoNome);
        etDtaNascimento = view.findViewById(R.id.etDtaNascimento);
        etNomeUtilizador = view.findViewById(R.id.etNomeUtilizador);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmarPassword = view.findViewById(R.id.etConfirmarPassword);
        etDistrito = view.findViewById(R.id.etDistrito);
        etMorada = view.findViewById(R.id.etMorada);
        etLocalidade = view.findViewById(R.id.etLocalidade);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);

        etDtaNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(fragmentManager, "datePicker");

            }
        });

        Button button = view.findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GenericoParserJson.isConnectionInternet(getContext())) {

                    String primeiroNome = etPrimeiroNome.getText().toString();
                    String ultimoNome = etUltimoNome.getText().toString();
                    String dtaNascimento = etDtaNascimento.getText().toString();
                    String username = etNomeUtilizador.getText().toString();
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    String confirmPassword = etConfirmarPassword.getText().toString();
                    String localidade = etLocalidade.getText().toString();
                    String morada = etMorada.getText().toString();
                    String distrito = etDistrito.getText().toString();
                    /*if (rbtnFeminino.isChecked() || rbtnMasculino.isChecked()) {
                        if (rbtnMasculino.isChecked()) {
                            String sexo = "Masculino";
                        } else if (rbtnFeminino.isChecked()) {
                            String sexo = "Feminino";

                        }
                    }
                    if (rbtnMasculino.isChecked()) {
                        String sexoRBtn = "Masculino";
                    }
*/

                    if (!isPrimeiroNomeValido(primeiroNome)) {
                        etPrimeiroNome.setError(getString(R.string.primNomeInvalido));
                        return;
                    }

                    if (!isUltimoNomeValido(ultimoNome)) {
                        etUltimoNome.setError(getString(R.string.ultimoNomeInvalido));
                        return;
                    }
                    /*if (!isDtaNascimentoValida(dtaNascimento)) {
                        etDtaNascimento.setError(getString(R.string.dtaNascInvalida));
                        return;
                    }*/

                    if (!isUsernameValido(username)) {
                        etNomeUtilizador.setError("Nome de Utilizador Inválido");
                        return;
                    }

                    if (!isEmailValido(email)) {
                        etEmail.setError("Email Inválido");
                        return;
                    }

                    if (!isPasswordValida(password)) {
                        etPassword.setError("Palavra Passe Inválida");
                        return;
                    }

                    if (!isConfirmPasswordValido(confirmPassword)) {
                        etConfirmarPassword.setError("Palavra Passe Inválida");
                        return;
                    }

                    if (!isEqualsPasswordValida(password, confirmPassword)) {
                        etConfirmarPassword.setError("As Palavras Passe não coincidem");
                        return;
                    }

                    if (!isLocalidadeValida(localidade)) {
                        etLocalidade.setError("Localidade inválida");
                        return;
                    }

                    if (!isMoradaValida(morada)) {
                        etLocalidade.setError("Morada inválida");
                        return;
                    }

                    if (!isDistritoValido(distrito)) {
                        etDistrito.setError("Distrito inválido");
                        return;
                    }
                    String sexo = "Masculino";
                    utilizador = new Utilizador(0, primeiroNome, ultimoNome, dtaNascimento, username, email, password, localidade, morada, distrito, sexo);
                    SingletonGestorCultravel.getInstance(getContext()).registarUserAPI(utilizador, getContext());

                } else {
                    Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean isPrimeiroNomeValido(String primeiroNome) {
        if (primeiroNome == null) {
            return false;
        }
        return primeiroNome.length() > 0;

    }

    private boolean isUltimoNomeValido(String ultimoNome) {
        if (ultimoNome == null) {
            return false;
        }
        return ultimoNome.length() > 0;

    }

    private boolean isDtaNascimentoValida(String dtaNascimento) {
        matcher = pattern.matcher(dtaNascimento);

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                String day = matcher.group(3);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(1));

                if (day.equals("31") &&
                        (month.equals("4") || month.equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month.equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isUsernameValido(String username) {
        if (username == null) {
            return true;
        }
        return username.length() > 0;

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

    private boolean isConfirmPasswordValido(String confirmPassword) {
        if (confirmPassword == null) {
            return true;
        }
        return confirmPassword.length() > 0;

    }

    private boolean isEqualsPasswordValida(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLocalidadeValida(String localidade) {
        if (localidade == null) {
            return true;
        }
        return localidade.length() > 0;

    }

    private boolean isMoradaValida(String morada) {
        if (morada == null) {
            return true;
        }
        return morada.length() > 0;

    }

    private boolean isDistritoValido(String distrito) {
        if (distrito == null) {
            return true;
        }
        return distrito.length() > 0;

    }

    @Override
    public void onUserRegistado() {

    }

    @Override
    public void onDatePickerSelected(String data) {
        etDtaNascimento.setText(data);
    }

    @Override
    public void onValidateLogin(String token, String email) {

    }
}