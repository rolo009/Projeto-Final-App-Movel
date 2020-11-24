package helloworld.amsi.ipleiria.cultravel.modelos;

public class PontoTuristico {
    private int id;
    private String nome, anoConstrucao, descricao, foto;
    //Comum a todas as instâncias da classe
    private static int autoIncrementId = 1;

    public PontoTuristico(String nome, String anoConstrucao, String descricao, String foto) {
        this.id = autoIncrementId++;
        this.nome = nome;
        this.anoConstrucao = anoConstrucao;
        this.descricao = descricao;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getAnoConstrucao() {
        return anoConstrucao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnoConstrucao(String anoConstrucao) {
        this.anoConstrucao = anoConstrucao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
