package pe.edu.cibertec.pokemonapi;

import com.google.gson.annotations.SerializedName;

    public class Pokemon {

        @SerializedName("name")
        private String name;

        @SerializedName("weight")
        private int weight;

        @SerializedName("height")
        private int height;


        private String url;

        @SerializedName("id")
        private int number;


        //contructor

        public Pokemon() {
        }


        // getter y setter


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl (String url) {
            url = url;
        }

        /*public int getNumber() {
            String [] urlPartes = url.split("/");

            return Integer.parseInt(urlPartes[urlPartes.length -1]);
        }*/

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }