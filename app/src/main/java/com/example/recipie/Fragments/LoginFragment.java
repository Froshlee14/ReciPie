package com.example.recipie.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipie.Activities.AccountActivity;
import com.example.recipie.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FirebaseAuth auth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance ();
        EditText edtEmail = view.findViewById (R.id.user_edtxt);
        EditText edtPassword = view.findViewById (R.id.pass_edtxt);

        Button btnRegister = view.findViewById (R.id.login_btn);
        btnRegister.setOnClickListener (v -> {
            if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                Toast.makeText (getActivity (), R.string.type_mail, Toast.LENGTH_LONG).show ();
            }
            else if(TextUtils.isEmpty(edtPassword.getText().toString())) {
                Toast.makeText (getActivity (), R.string.type_password, Toast.LENGTH_LONG).show ();
            }
            else {
                login(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });

    }

    private void login (String email, String password) {

        auth.signInWithEmailAndPassword (email, password)
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        FirebaseUser user = auth.getCurrentUser ();

                        if (user != null) {
                            ((AccountActivity) getActivity()).openRecipesMenu();
                        }

                        Toast.makeText (getActivity (), R.string.welcome, Toast.LENGTH_LONG).show ();
                    } else {
                        Toast.makeText (getActivity (), R.string.incorrect_user, Toast.LENGTH_LONG).show ();
                    }
                });
    }

}