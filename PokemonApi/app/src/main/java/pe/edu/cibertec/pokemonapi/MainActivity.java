package pe.edu.cibertec.pokemonapi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText etPokemon;
    Button btSearch;
    TextView tvName, tvWeight, tvHeight, tvIdentify;
    ImageView ivUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPokemon = findViewById(R.id.etPokemon);
        btSearch = findViewById(R.id.btSearch);
        tvName = findViewById(R.id.tvName);
        tvWeight = findViewById(R.id.tvWeight);
        tvHeight = findViewById(R.id.tvHeight);
        ivUrl = findViewById(R.id.ivUrl);



        btSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String input = etPokemon.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                // retrofit es un servicio web  para llamar http , manejo de hilos
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonInterface pokemonInterface = retrofit.create(PokemonInterface.class);

        Call<Pokemon> searchMethod = pokemonInterface.getPokemon(input);

        searchMethod.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()){
                    Pokemon pokemon = response.body();
                    tvName.setText(pokemon.getName());
                    tvWeight.setText(String.valueOf(pokemon.getWeight()));
                    tvHeight.setText(String.valueOf(pokemon.getHeight()));
                    //dentify.setText(String.valueOf(pokemon.getNumber()));



                    //Para poder visualizar la imagen por url

                    Glide.with(MainActivity.this)
                         //  .load( "http://pokeapi.co/media/sprites/pokemon" + pokemon.getNumber() + ".png")
                            //.load(pokemon.getNumber() + ".png")
                            .load( "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png")
                            .into(ivUrl);

                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

    }
}