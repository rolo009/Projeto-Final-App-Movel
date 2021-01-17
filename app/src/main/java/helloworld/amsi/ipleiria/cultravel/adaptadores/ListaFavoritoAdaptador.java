package helloworld.amsi.ipleiria.cultravel.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;
import helloworld.amsi.ipleiria.cultravel.modelos.SingletonGestorCultravel;
import helloworld.amsi.ipleiria.cultravel.vistas.MenuMainActivity;

public class ListaFavoritoAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PontoTuristico> pontosTuristicos;

    public ListaFavoritoAdaptador(Context context, ArrayList<PontoTuristico> pontosTuristicos) {
        this.context = context;
        this.pontosTuristicos = pontosTuristicos;
    }

    @Override
    public int getCount() {
        return pontosTuristicos.size();
    }

    @Override
    public Object getItem(int position) {
        return pontosTuristicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pontosTuristicos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_favoritos, null);
        }

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(pontosTuristicos.get(position));

        viewHolder.btnRemoverFavoritos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesUser = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                String token = sharedPreferencesUser.getString(MenuMainActivity.TOKEN, null);
                SingletonGestorCultravel.getInstance(context).removerPontoTuristicoFavoritoAPI(context, pontosTuristicos.get(position), token);
            }
        });

        return convertView;
    }

    private class ViewHolderLista {
        TextView tvNomePT, tvTipoMonumento;
        ImageView imgFotoPT;
        Button btnRemoverFavoritos;

        public ViewHolderLista(View view) {
            tvNomePT = view.findViewById(R.id.tvNomePT);
            tvTipoMonumento = view.findViewById(R.id.tvTipoMonumento);
            imgFotoPT = view.findViewById(R.id.imgFotoPT);
            btnRemoverFavoritos = view.findViewById(R.id.btn_removerFavoritos);
        }

        public void update(PontoTuristico pontoTuristico) {
            System.out.println(pontoTuristico.getNome());
            tvNomePT.setText(pontoTuristico.getNome());
            tvTipoMonumento.setText(pontoTuristico.getTipoMonumento());
            Glide.with(context)
                    .load(pontoTuristico.getFoto())
                    .placeholder(R.drawable.castelo_de_leiria)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgFotoPT);
        }
    }
}
