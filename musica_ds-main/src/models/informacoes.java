package models;

public class informacoes {
    private int id;
    private String nomeAlbum, nomeArtista, nomeGravadora, divulgacao;

    //Contrutures
    public informacoes() {
    }

    public informacoes(String nomeAlbum, String nomeArtista, String nomeGravadora, String divulgacao) {
        this.nomeAlbum = nomeAlbum;
        this.nomeArtista = nomeArtista;
        this.nomeGravadora = nomeGravadora;
        this.divulgacao = divulgacao;
    }

    public informacoes(int id, String nomeAlbum, String nomeArtista, String nomeGravadora, String divulgacao) {
        this.id = id;
        this.nomeAlbum = nomeAlbum;
        this.nomeArtista = nomeArtista;
        this.nomeGravadora = nomeGravadora;
        this.divulgacao = divulgacao;
    }

    //Getters e Setters

    public int getId() {
        return id;
    }

    //ID somente leitura, sem setter

    public String getNomeAlbum() {
        return nomeAlbum;
    }

    public void setNomeAlbum(String nomeAlbum){
        this.nomeAlbum = nomeAlbum;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista){
        this.nomeArtista = nomeArtista;
    }

    public String getNomeGravadora() {
        return nomeGravadora;
    }

    public void setNomeGravadora(String nomeGravadora){
        this.nomeGravadora = nomeGravadora;
    }

    //método pra conferir o radiobutton (divulgação) ativo
    public String getDivulgacao() {
        return divulgacao;
    }

    public void setDivulgacao(String divulgacao){
        this.divulgacao = divulgacao;
   }

    @Override
        public String toString(){
            return "informacoes [id=" + id + ", nome_album=" + nomeAlbum + ", nome_artista=" + nomeArtista + ", nome_gravadora=" + nomeGravadora + ", divulgacao=" + divulgacao +"]";
        }
}
