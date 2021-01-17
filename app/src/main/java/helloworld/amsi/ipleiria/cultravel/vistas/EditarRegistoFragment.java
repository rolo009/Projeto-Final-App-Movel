package helloworld.amsi.ipleiria.cultravel.vistas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.UserListener;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;
import helloworld.amsi.ipleiria.cultravel.utils.GenericoParserJson;


public class EditarRegistoFragment extends Fragment implements UserListener {

    private EditText etPrimeiroNome, etUltimoNome, etDtaNascimento, etNomeUtilizador, etEmail, etPassword, etConfirmarPassword, etMorada, etDistrito, etLocalidade, etOldPassword;
    private RadioButton rbtnMasculino, rbtnFeminino;
    private FragmentManager fragmentManager;
    private Utilizador utilizador;

    private Pattern pattern;
    private Matcher matcher;

    public EditarRegistoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        fragmentManager = getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_editar_registo, container, false);
        SingletonGestorCultravel.getInstance(getContext()).setUserListener(this);

        final Calendar myCalendar = Calendar.getInstance();

        etPrimeiroNome = view.findViewById(R.id.etPrimeiroNome);
        etUltimoNome = view.findViewById(R.id.etUltimoNome);
        etDtaNascimento = view.findViewById(R.id.etDtaNascimento);
        etNomeUtilizador = view.findViewById(R.id.etNomeUtilizador);
        etEmail = view.findViewById(R.id.etEmail);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmarPassword = view.findViewById(R.id.etConfirmarPassword);
        etDistrito = view.findViewById(R.id.etDistrito);
        etMorada = view.findViewById(R.id.etMorada);
        etLocalidade = view.findViewById(R.id.etLocalidade);
        rbtnMasculino = view.findViewById(R.id.rbtnMasculino);
        rbtnFeminino = view.findViewById(R.id.rbtnFeminino);

        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);

        SingletonGestorCultravel.getInstance(getContext()).getUserAPI(getContext(), token);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        etDtaNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button button = view.findViewById(R.id.btn_editarRegisto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GenericoParserJson.isConnectionInternet(getContext())) {

                    String primeiroNome = etPrimeiroNome.getText().toString();
                    String ultimoNome = etUltimoNome.getText().toString();
                    String dtaNascimento = etDtaNascimento.getText().toString();
                    String username = etNomeUtilizador.getText().toString();
                    String email = etEmail.getText().toString();
                    String oldPassword = etOldPassword.getText().toString();
                    String password = etPassword.getText().toString();
                    String confirmPassword = etConfirmarPassword.getText().toString();
                    String localidade = etLocalidade.getText().toString();
                    String morada = etMorada.getText().toString();
                    String distrito = etDistrito.getText().toString();
                    String sexo = "Masculino";

                    if (rbtnMasculino.isChecked()) {
                        sexo = "Masculino";
                    } else if (rbtnFeminino.isChecked()) {
                        sexo = "Feminino";
                    }


                    if (!isPrimeiroNomeValido(primeiroNome)) {
                        etPrimeiroNome.setError(getString(R.string.primNomeInvalido));
                        return;
                    }

                    if (!isUltimoNomeValido(ultimoNome)) {
                        etUltimoNome.setError(getString(R.string.ultimoNomeInvalido));
                        return;
                    }
                    if (!isDtaNascimentoValida(dtaNascimento)) {
                        etDtaNascimento.setError(getString(R.string.dtaNascInvalida));
                        return;
                    }

                    if (!isUsernameValido(username)) {
                        etNomeUtilizador.setError("Nome de Utilizador Inválido");
                        return;
                    }

                    if (!isEmailValido(email)) {
                        etEmail.setError(getString(R.string.emailInvalido));
                        return;
                    }

                    if (isOldPasswordValida(oldPassword)) {
                        if (!isPasswordValida(password)) {
                            etPassword.setError("Palavra Passe Inválida, é necessário ter mais de 8 caractéres");
                            return;
                        }

                        if (!isConfirmPasswordValido(confirmPassword)) {
                            etConfirmarPassword.setError("Palavra Passe Inválida, é necessário ter mais de 8 caractéres");
                            return;
                        }

                        if (!isEqualsPasswordValida(password, confirmPassword)) {
                            etConfirmarPassword.setError("As Palavras Passe não coincidem");
                            return;
                        }
                    } else {
                        oldPassword = null;
                        password = null;
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
                    if (oldPassword != null) {
                        utilizador = new Utilizador(0, primeiroNome, ultimoNome, dtaNascimento, username, email, password, localidade, morada, distrito, sexo);
                    } else {
                        utilizador = new Utilizador(0, primeiroNome, ultimoNome, dtaNascimento, username, email, null, localidade, morada, distrito, sexo);
                    }
                    SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
                    SingletonGestorCultravel.getInstance(getContext()).editarUtilizadorAPI(utilizador, getContext(), oldPassword, token);

                } else {
                    Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        etDtaNascimento.setText(sdf.format(myCalendar.getTime()));
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
        if (dtaNascimento == null) {
            return false;
        }
        return dtaNascimento.length() > 0;
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

    private boolean isOldPasswordValida(String password) {
        if (password == null) {
            return true;
        }
        return password.length() > 8;

    }

    private boolean isPasswordValida(String password) {
        if (password == null) {
            return true;
        }
        return password.length() > 8;

    }

    private boolean isConfirmPasswordValido(String confirmPassword) {
        if (confirmPassword == null) {
            return true;
        }
        return confirmPassword.length() > 8;

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
    public void onUserRegistado(String response) {
        Log.e("resposta", response);

        switch (response) {
            case "0":
                Log.e("eee", "1111");
                etEmail.setError("Este email já se encontra registado!");
                break;
            case "1":
                etNomeUtilizador.setError("Este nome de utilizador já se encontra registado!");
                break;
            case "true":
                Fragment fragment = new LoginFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
                Toast.makeText(getContext(), "Bem Vindo(a), a sua conta foi registada com sucesso!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onValidateLogin(String token, String email) {

    }

    @Override
    public void onRefreshDetalhes(String resposta) {
        switch (resposta) {
            case "0":
                Log.e("eee", "1111");
                etOldPassword.setError("A Palavra-Passe introduzida não corresponde a este utilizador!");
                break;
            case "true":
                Fragment fragment = new UserProfileFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
                Toast.makeText(getContext(), "A sua conta foi atualizada com sucesso!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onApagarConta() {

    }

    @Override
    public void onErroLogin() {

    }

    @Override
    public void onLoadEditarRegisto(Utilizador utilizador) {
        etPrimeiroNome.setText(utilizador.getPrimeiroNome());
        etUltimoNome.setText(utilizador.getUltimoNome());
        etDtaNascimento.setText(utilizador.getDtaNascimento());
        etNomeUtilizador.setText(utilizador.getUsername());
        etEmail.setText(utilizador.getEmail());
        etDistrito.setText(utilizador.getDistrito());
        etMorada.setText(utilizador.getMorada());
        etLocalidade.setText(utilizador.getLocalidade());

        if(utilizador.getSexo().equals("Masculino")){
            rbtnMasculino.setChecked(true);
        }else if (utilizador.getSexo().equals("Feminino")){
            rbtnFeminino.setChecked(true);
        }
    }
}