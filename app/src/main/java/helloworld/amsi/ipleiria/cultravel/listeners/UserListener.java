package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;

public interface UserListener {
    public void onUserRegistado();

    public void onDatePickerSelected(String data);

    void onValidateLogin(String token, String email);

}
