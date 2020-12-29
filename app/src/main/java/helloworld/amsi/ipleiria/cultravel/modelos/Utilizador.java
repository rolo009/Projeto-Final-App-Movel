package helloworld.amsi.ipleiria.cultravel.modelos;

import java.util.Date;

public class Utilizador {

    private int id;
    private String primeiroNome;
    private String ultimoNome;
    private String username;
    private String email;
    private String password;
    private String morada;
    private String localidade;
    private String distrito;
    private String sexo;
    private String dtaNascimento;

    //Comum a todas as instâncias da classe
    public Utilizador(int id, String primeiroNome,String ultimoNome, String dtaNascimento, String username, String email, String password, String localidade, String morada, String distrito, String sexo) {
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.dtaNascimento = dtaNascimento;
        this.username = username;
        this.email = email;
        this.password = password;
        this.morada = morada;
        this.localidade = localidade;
        this.distrito = distrito;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDtaNascimento() {
        return dtaNascimento;
    }

    public void setDtaNascimento(String dtaNascimento) {
        this.dtaNascimento = dtaNascimento;
    }
}
