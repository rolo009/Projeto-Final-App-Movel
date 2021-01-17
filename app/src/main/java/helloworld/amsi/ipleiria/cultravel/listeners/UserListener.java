package helloworld.amsi.ipleiria.cultravel.listeners;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.modelos.Utilizador;

public interface UserListener {
    public void onUserRegistado(String response);

    void onValidateLogin(String token, String email);

    void onRefreshDetalhes(String response);

    void onApagarConta();

    void onErroLogin();

    void onLoadEditarRegisto(Utilizador utilizador);
}
