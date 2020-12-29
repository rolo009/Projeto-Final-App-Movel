package helloworld.amsi.ipleiria.cultravel.vistas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;

public class PontoTuristicoDetailsActivity extends AppCompatActivity {
    public static final String ID = "ID";
    private PontoTuristico pontoTuristico;

    private TextView tv_NomePT, tv_tipoMonumento, tv_estiloConstrucao, tv_anoConstrucao, tv_localidade, tv_rating, tv_descricao, tvEC, tvAC;
    private ImageView ivImgPT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_turistico_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra(ID, -1);
        pontoTuristico = SingletonGestorCultravel.getInstance(this).getPontoTuristico(id);

        tv_NomePT = findViewById(R.id.tv_NomePT);
        tv_tipoMonumento = findViewById(R.id.tv_tipoMonumento);

        tvEC = findViewById(R.id.tvEC);
        tv_estiloConstrucao = findViewById(R.id.tv_estiloConstrucao);

        tvAC = findViewById(R.id.tvAC);
        tv_anoConstrucao = findViewById(R.id.tv_anoConstrucao);

        tv_localidade = findViewById(R.id.tv_localidade);
        tv_rating = findViewById(R.id.tv_rating);
        tv_descricao = findViewById(R.id.tv_descricao);
        ivImgPT = findViewById(R.id.ivImgPT);

        carregarPontoTuristico(pontoTuristico);
    }

    private void carregarPontoTuristico(PontoTuristico pontoTuristico) {

        tv_NomePT.setText(pontoTuristico.getNome());
        tv_tipoMonumento.setText(pontoTuristico.getTipoMonumento());

        if(pontoTuristico.getEstiloConstrucao() != null){
            tv_estiloConstrucao.setText(pontoTuristico.getEstiloConstrucao());
        }else{
            tvEC.setVisibility(View.GONE);
            tv_estiloConstrucao.setVisibility(View.GONE);
        }

        if(pontoTuristico.getAnoConstrucao() != null){
            tv_anoConstrucao.setText(pontoTuristico.getAnoConstrucao()+"");
        }else{
            tvAC.setVisibility(View.GONE);
            tv_anoConstrucao.setVisibility(View.GONE);
        }
        tv_localidade.setText(pontoTuristico.getLocalidade());
        /*tv_rating.setText(pontoTuristico.getRanking());*/
        tv_descricao.setText(pontoTuristico.getDescricao());
        Glide.with(this)
                .load(pontoTuristico.getFoto())
                .placeholder(R.drawable.castelo_de_leiria)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImgPT);
    }


    private void onClickAddFavoritos(PontoTuristico pontoTuristico) { /*
        SingletonGestorCultravel.getInstance(getApplicationContext()).adicionarPontoTuristicoFavoritoBD(pontoTuristico);*/
    }

    private void onClickGoGoogleMaps(PontoTuristico pontoTuristico) {
        Uri gmmIntentUri = Uri.parse("geo:"+pontoTuristico.getLatitude()+","+pontoTuristico.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}