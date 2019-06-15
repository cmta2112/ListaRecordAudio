package pe.edu.cibertec.listarecordaudio;

public class Audio {

    //ATRIBUTOS

    String uri;
    String Name;
    boolean isPlaying = false;

    // CONSTRUCTORES

    public Audio(String uri, String name, boolean isPlaying) {
        this.uri = uri;
        Name = name;
        this.isPlaying = isPlaying;
    }

    public Audio() {
    }

    // GETTER Y SETTER


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
