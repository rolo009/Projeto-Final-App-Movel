package helloworld.amsi.ipleiria.cultravel.adaptadores;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.modelos.PontoTuristico;

public class ListaPontoTuristicoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PontoTuristico> pontosTuristicos;

    public ListaPontoTuristicoAdaptador(Context context, ArrayList<PontoTuristico> pontosTuristicos) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_pontos_turisticos, null);
        }

        /*otimização*/
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(pontosTuristicos.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        TextView tvNomePT, tvTipoMonumento, tvDescricao;
        ImageView imgFotoPT;

        public ViewHolderLista(View view) {
            tvNomePT = view.findViewById(R.id.tvNomePT);
            tvTipoMonumento = view.findViewById(R.id.tvTipoMonumento);
            tvDescricao = view.findViewById(R.id.tvDescricao);
            imgFotoPT = view.findViewById(R.id.imgFotoPT);
        }

        public void update(PontoTuristico pontoTuristico) {
            tvNomePT.setText(pontoTuristico.getNome());
            tvTipoMonumento.setText(pontoTuristico.getTipoMonumento());
            tvDescricao.setText(pontoTuristico.getDescricao());
            Glide.with(context)
                    .load(pontoTuristico.getFoto())
                    .placeholder(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgFotoPT);
        }
    }
}
