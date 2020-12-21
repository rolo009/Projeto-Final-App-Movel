package helloworld.amsi.ipleiria.cultravel.vistas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

    private TextView tv_NomePT, tv_tipoMonumento, tv_estiloConstrucao, tv_anoConstrucao, tv_localidade, tv_rating, tv_descricao;
    private ImageView ivImgPT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_turistico_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra(ID, -1);
        pontoTuristico = SingletonGestorCultravel.getInstance(this).getPontoTuristico(id);

        tv_NomePT = findViewById(R.id.tv_NomePT);
        tv_tipoMonumento = findViewById(R.id.tv_tipoMonumento);
        tv_estiloConstrucao = findViewById(R.id.tv_estiloConstrucao);
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
        tv_estiloConstrucao.setText(pontoTuristico.getEstiloConstrucao());
        tv_anoConstrucao.setText(pontoTuristico.getAnoConstrucao()+"");
        tv_localidade.setText(pontoTuristico.getLocalidade());
        tv_rating.setText(pontoTuristico.getRanking());
        tv_descricao.setText(pontoTuristico.getDescricao());
        Glide.with(this)
                .load(pontoTuristico.getFoto())
                .placeholder(R.drawable.castelo_de_leiria)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImgPT);
    }
}