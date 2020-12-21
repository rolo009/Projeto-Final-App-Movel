package helloworld.amsi.ipleiria.cultravel.modelos;

public class PontoTuristico {
    private int id,status;
    private String nome, anoConstrucao, descricao, localidade, tipoMonumento,estiloConstrucao, foto, ranking, latitude, longitude;
    //Comum a todas as instâncias da classe

    public PontoTuristico(int id, String nome, String anoConstrucao, String descricao, String localidade, String tipoMonumento, String estiloConstrucao, String ranking, String foto, String latitude, String longitude, int status) {
        this.id = id;
        this.nome = nome;
        this.anoConstrucao = anoConstrucao;
        this.descricao = descricao;
        this.localidade = localidade;
        this.tipoMonumento = tipoMonumento;
        this.estiloConstrucao = estiloConstrucao;
        this.ranking = ranking;
        this.foto = foto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTipoMonumento() {
        return tipoMonumento;
    }

    public void setTipoMonumento(String tipoMonumento) {
        this.tipoMonumento = tipoMonumento;
    }

    public String getEstiloConstrucao() {
        return estiloConstrucao;
    }

    public void setEstiloConstrucao(String estiloConstrucao) {
        this.estiloConstrucao = estiloConstrucao;
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
