package com.daffafakhir.splashscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView nama = view.findViewById(R.id.textView3);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null){
            replaceFragment(new LoginFragment());
        } else {
            nama.setText(user.getEmail());
        }

        // Inisialisasi tombol logout
        Button btnLogout = view.findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new LoginFragment());
            fragmentTransaction.commit();
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);  // Pastikan ID ini ada di activity_main.xml
        fragmentTransaction.commit();
    }
}