package helloworld.amsi.ipleiria.cultravel.modelos;

public class PontoTuristico {
    private int id;
    private String nome, anoConstrucao, descricao, localidade, foto;
    //Comum a todas as instâncias da classe

    public PontoTuristico(int id, String nome, String anoConstrucao, String descricao, String localidade, String foto) {
        this.id = id;
        this.nome = nome;
        this.anoConstrucao = anoConstrucao;
        this.descricao = descricao;
        this.localidade = localidade;
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

    public String getLocalidade() {
        return localidade;
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

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }


    public void setFoto(String foto) {
        this.foto = foto;
    }
}
