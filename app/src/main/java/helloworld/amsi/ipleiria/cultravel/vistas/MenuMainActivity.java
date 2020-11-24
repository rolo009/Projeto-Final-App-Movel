package helloworld.amsi.ipleiria.cultravel.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import helloworld.amsi.ipleiria.cultravel.R;
import helloworld.amsi.ipleiria.cultravel.vistas.SearchFragment;

public class MenuMainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new SearchFragment();
                        setTitle(item.getTitle());
                        break;
                    case R.id.nav_favoritos:
                        fragment = new FavouriteFragment();
                        setTitle(item.getTitle());
                        break;
                    case R.id.nav_contactos:
                        System.out.println("-->Nav Email");
                        break;
                    case R.id.nav_areaPessoal:
                        fragment = new EditarRegistoFragment();
                        setTitle(item.getTitle());
                        break;
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
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        Fragment fragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle("HOME");
    }
}