package helloworld.amsi.ipleiria.cultravel.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import helloworld.amsi.ipleiria.cultravel.R;

public class MenuMainActivity extends AppCompatActivity{

    public static final String EMAIL = "EMAIL";
    public static final String TOKEN = "TOKEN";
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    public static final String PREF_INFO_USER = "PREF_INFO_USER";
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                token = sharedPrefInfoUser.getString(TOKEN, null);
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new SearchFragment();
                        setTitle(item.getTitle());
                        break;
                    case R.id.nav_favoritos:
                        if(token != null){
                            fragment = new FavouriteFragment();
                            setTitle(item.getTitle());
                            break;
                        }else{
                            fragment = new LoginFragment();
                            setTitle(item.getTitle());
                            break;
                        }
                    case R.id.nav_contactos:
                        fragment = new ContactosFragment();
                        System.out.println("-->Nav Email");
                        break;
                    case R.id.nav_areaPessoal:
                        if(token != null){
                            fragment = new UserProfileFragment();
                            setTitle(item.getTitle());
                            break;
                        }else{
                            fragment = new LoginFragment();
                            setTitle(item.getTitle());
                            break;
                        }
                }

                if (fragment != null) {
                    fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
                }

                return true;
            }
        });
        fragmentManager = getSupportFragmentManager();

        /*carregarCabecalho();*/
        carregarFragmentoInicial();

    }

   /* private void carregarCabecalho() {
        View hView = navigationView.getHeaderView(0);
    }*/

    private void carregarFragmentoInicial() {
            Fragment fragment = new SearchFragment();
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();


    }
}