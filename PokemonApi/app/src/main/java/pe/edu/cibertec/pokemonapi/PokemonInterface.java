package pe.edu.cibertec.pokemonapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonInterface {

        @GET("pokemon/{id}")
        Call<Pokemon> getPokemon(@Path("id") String id);
    }

