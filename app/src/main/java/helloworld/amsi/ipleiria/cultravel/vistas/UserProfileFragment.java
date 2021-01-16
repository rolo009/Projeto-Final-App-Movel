package helloworld.amsi.ipleiria.cultravel.vistas;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import helloworld.amsi.ipleiria.cultravel.R;

public class UserProfileFragment extends Fragment {

    private FragmentManager fragmentManager;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        fragmentManager = getFragmentManager();

        Button btnLogout = view.findViewById(R.id.btn_terminarSessao);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencesUser.edit();

                editor.clear().apply();

                Toast.makeText(getContext(), "Terminou a sessão com sucesso!", Toast.LENGTH_LONG).show();

                Fragment fragment = new SearchFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
            }
        });

        Button btn_editarRegisto = view.findViewById(R.id.btn_editarRegisto);
        btn_editarRegisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditarRegistoFragment();
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).addToBackStack(null).commit();
            }
        });


        return view;
    }



}