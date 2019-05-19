package pe.edu.cibertec;

import com.google.gson.annotations.SerializedName;


public class Movie {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Poster")
    private String Poster;


    //contructores


    public Movie(String title, String year, String plot, String poster) {
        this.title = title;
        this.year = year;
        this.plot = plot;
        Poster = poster;
    }

    public Movie() {

    }

    //getter y setter


    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
