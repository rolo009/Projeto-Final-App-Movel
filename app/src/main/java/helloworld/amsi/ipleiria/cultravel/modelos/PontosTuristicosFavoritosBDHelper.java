package helloworld.amsi.ipleiria.cultravel.modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PontosTuristicosFavoritosBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbcultravel";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "favoritos";
    private static final String ID_FAVORITOS = "id";
    private static final String ID_PONTO_TURISTICO = "idPontoTuristico";
    private static final String ANO_CONSTRUCAO_PONTO_TURISTICO = "anoConstrucaoPontoTuristico";
    private static final String NOME_PONTO_TURISTICO = "nomePontoTuristico";
    private static final String DESCRICAO_PONTO_TURISTICO = "descricaoPontoTuristico";
    private static final String LOCALIDADE_PONTO_TURISTICO = "localidadePontoTuristico";
    private static final String ESTILO_CONSTRUCAO_PONTO_TURISTICO = "estiloConstrucaoPontoTuristico";
    private static final String TIPO_MONUMENTO_PONTO_TURISTICO = "tipoMonunmentoPontoTuristico";
    private static final String FOTO_PONTO_TURISTICO = "fotoPontoTuristico";
    private static final String LATITUDE_PONTO_TURISTICO = "latitudePontoTuristico";
    private static final String LOGITUDE_PONTO_TURISTICO = "longitudePontoTuristico";

    private final SQLiteDatabase db;

    public PontosTuristicosFavoritosBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//Código SQL da criação da tabela
        String createTableFavoritos = "CREATE TABLE " + TABLE_NAME + "( " +
                ID_PONTO_TURISTICO + " INTEGER NOT NULL, " +
                NOME_PONTO_TURISTICO + " TEXT NOT NULL, " +
                ANO_CONSTRUCAO_PONTO_TURISTICO + " TEXT NOT NULL, " +
                LOCALIDADE_PONTO_TURISTICO + " TEXT NOT NULL, " +
                DESCRICAO_PONTO_TURISTICO + " TEXT NOT NULL, " +
                ESTILO_CONSTRUCAO_PONTO_TURISTICO + " TEXT NOT NULL, " +
                TIPO_MONUMENTO_PONTO_TURISTICO + " TEXT NOT NULL, " +
                LATITUDE_PONTO_TURISTICO + " TEXT NOT NULL, " +
                LOGITUDE_PONTO_TURISTICO + " TEXT NOT NULL, " +
                FOTO_PONTO_TURISTICO + " TEXT NOT NULL );";
        db.execSQL(createTableFavoritos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTableFavoritos = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(deleteTableFavoritos);
        this.onCreate(db);
    }

    /*********************************CRUD**********************/

    /**
     * INSERT : o método insert devolve o id da linha inserida ou -1
     *
     * @param pontoTuristico
     * @return
     */

    public void adicionarPontoTuristicoFavoritoBD(PontoTuristico pontoTuristico) {
        //adicionar ponto turistico
        ContentValues values = new ContentValues();


        values.put(ID_PONTO_TURISTICO, pontoTuristico.getId());
        values.put(NOME_PONTO_TURISTICO, pontoTuristico.getNome());
        values.put(ANO_CONSTRUCAO_PONTO_TURISTICO, pontoTuristico.getAnoConstrucao());
        values.put(LOCALIDADE_PONTO_TURISTICO, pontoTuristico.getLocalidade());
        values.put(DESCRICAO_PONTO_TURISTICO, pontoTuristico.getDescricao());
        values.put(ESTILO_CONSTRUCAO_PONTO_TURISTICO, pontoTuristico.getEstiloConstrucao());
        values.put(TIPO_MONUMENTO_PONTO_TURISTICO, pontoTuristico.getTipoMonumento());
        values.put(LATITUDE_PONTO_TURISTICO, pontoTuristico.getLatitude());
        values.put(LOGITUDE_PONTO_TURISTICO, pontoTuristico.getLongitude());
        values.put(FOTO_PONTO_TURISTICO, pontoTuristico.getFoto());


        this.db.insert(TABLE_NAME, null, values);
    }

    /**
     * DELETE
     *
     * @param id
     * @return
     */

    public boolean removerPontoTuristicoFavoritoBD(int id) {
        //remover Ponto Turistico Favorito
        int nRows = this.db.delete(TABLE_NAME, "id = ?", new String[]{id + ""});
        return (nRows > 0);
    }

    public void removerAllPontosTuristicosFavoritosBD() {
        //remover todos os Pontos Turisticos Favoritos
        this.db.delete(TABLE_NAME, null, null);
    }

    /**
     * SELECT
     *
     * @return
     */

    public ArrayList<PontoTuristico> getAllPontosTuristicosFavoritosBD() {
        //captar todos os pontos turisticos

        ArrayList<PontoTuristico> pontoTuristico = new ArrayList<>();
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                PontoTuristico auxPontoTuristico = new PontoTuristico(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),cursor.getString(8),
                        cursor.getString(9), 0);

               // auxPontoTuristico.setId(cursor.getInt(0));
                pontoTuristico.add(auxPontoTuristico);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return pontoTuristico;
    }

}
