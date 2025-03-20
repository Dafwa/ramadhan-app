package com.daffafakhir.splashscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            replaceFragment(new ProfileFragment());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etUsername = view.findViewById(R.id.username);
        EditText etPassword = view.findViewById(R.id.password);
        Button btnLogin = view.findViewById(R.id.keLoginButton);
        Button btnRegister = view.findViewById(R.id.registerButton);
        ProgressBar progressBar = view.findViewById(R.id.circleLoad);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            replaceFragment(new LoginFragment());
        });

        btnRegister.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Masukkan Email!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Masukkan Password!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Register berhasil!",
                                        Toast.LENGTH_SHORT).show();
                                replaceFragment(new LoginFragment());
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Register gagal.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);  // Pastikan ID ini ada di activity_main.xml
        fragmentTransaction.commit();
    }
}