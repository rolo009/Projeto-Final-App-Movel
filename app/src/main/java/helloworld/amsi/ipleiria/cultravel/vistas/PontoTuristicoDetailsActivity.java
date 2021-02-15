package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.listeners.FavoritosListener;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;

public class PontoTuristicoDetailsActivity extends AppCompatActivity implements FavoritosListener {
    public static final String ID = "ID";
    private PontoTuristico pontoTuristico;

    private TextView tv_NomePT, tv_tipoMonumento, tv_estiloConstrucao, tv_anoConstrucao, tv_localidade, tv_descricao, tvEC, tvAC;
    private ImageView ivImgPT;
    private Button btnAddFavoritos, btnRemoverFavoritos, btnGoogleMaps;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_turistico_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SingletonGestorCultravel.getInstance(getApplicationContext()).setFavoritosListener(this);

        int id = getIntent().getIntExtra(ID, -1);
        pontoTuristico = SingletonGestorCultravel.getInstance(this).getPontoTuristico(id);

        btnGoogleMaps = findViewById(R.id.btnGoogleMaps);

        btnAddFavoritos = findViewById(R.id.btnAddFavoritos);
        btnRemoverFavoritos = findViewById(R.id.btnRemoverFavoritos);

        SharedPreferences sharedPreferencesUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);

        tv_NomePT = findViewById(R.id.tv_NomePT);
        tv_tipoMonumento = findViewById(R.id.tv_tipoMonumento);

        tvEC = findViewById(R.id.tvEC);
        tv_estiloConstrucao = findViewById(R.id.tv_estiloConstrucao);

        tvAC = findViewById(R.id.tvAC);
        tv_anoConstrucao = findViewById(R.id.tv_anoConstrucao);

        tv_localidade = findViewById(R.id.tv_localidade);
        tv_descricao = findViewById(R.id.tv_descricao);
        ivImgPT = findViewById(R.id.ivImgPT);


        btnGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+pontoTuristico.getLatitude()+","+pontoTuristico.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        if(token != null){
            SingletonGestorCultravel.getInstance(getApplicationContext()).checkFavoritoAPI(getApplicationContext(), pontoTuristico, token);

            btnAddFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPreferencesUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
                    SingletonGestorCultravel.getInstance(getApplicationContext()).adicionarPontoTuristicoFavoritoAPI(getApplicationContext(), pontoTuristico, token);
                }
            });

            btnRemoverFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferencesUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);

                    SingletonGestorCultravel.getInstance(getApplicationContext()).removerPontoTuristicoFavoritoAPI(getApplicationContext(), pontoTuristico, token);
                }
            });
        }else{
            btnAddFavoritos.setVisibility(View.INVISIBLE);
            btnRemoverFavoritos.setVisibility(View.GONE);
        }



        carregarPontoTuristico(pontoTuristico);
    }

    private void carregarPontoTuristico(PontoTuristico pontoTuristico) {

        tv_NomePT.setText(pontoTuristico.getNome());
        tv_tipoMonumento.setText(pontoTuristico.getTipoMonumento());
        Log.e("EC", pontoTuristico.getEstiloConstrucao());
        if(!pontoTuristico.getEstiloConstrucao().equals("null")){
            tv_estiloConstrucao.setText(pontoTuristico.getEstiloConstrucao());
        }else{
            tvEC.setVisibility(View.GONE);
            tv_estiloConstrucao.setVisibility(View.GONE);
        }

        if(!pontoTuristico.getAnoConstrucao().equals("")){
            tv_anoConstrucao.setText(pontoTuristico.getAnoConstrucao()+"");
        }else{
            tvAC.setVisibility(View.GONE);
            tv_anoConstrucao.setVisibility(View.GONE);
        }
        tv_localidade.setText(pontoTuristico.getLocalidade());
        tv_descricao.setText(pontoTuristico.getDescricao());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_descricao.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        Glide.with(this)
                .load(pontoTuristico.getFoto())
                .placeholder(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImgPT);
    }

    @Override
    public void onAddPTFavoritos() {
        Toast.makeText(getApplicationContext(), "Ponto Turístico Adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
        btnAddFavoritos.setVisibility(View.GONE);
        btnRemoverFavoritos.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRemoverPTFavoritos() {
        Toast.makeText(getApplicationContext(), "Ponto Turístico Removido dos favoritos!", Toast.LENGTH_SHORT).show();
        btnAddFavoritos.setVisibility(View.VISIBLE);
        btnRemoverFavoritos.setVisibility(View.GONE);
    }

    @Override
    public void oncheckPtFavorito(Boolean favorito) {
        if(favorito){
            btnAddFavoritos.setVisibility(View.GONE);
            btnRemoverFavoritos.setVisibility(View.VISIBLE);
        }else{
            btnAddFavoritos.setVisibility(View.VISIBLE);
            btnRemoverFavoritos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshListaFavoritosPontosTuristicos(ArrayList<PontoTuristico> pontoTuristicos) {

    }

    @Override
    public void onNoFavoritos() {

    }

}