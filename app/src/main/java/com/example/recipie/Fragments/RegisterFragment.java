package com.example.recipie.Fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance ();

        EditText edtUser = view.findViewById (R.id.user_name_edtxt);
        EditText edtEmail = view.findViewById (R.id.user_edtxt);
        EditText edtPassword = view.findViewById (R.id.pass_edtxt);


        Button btnRegister = view.findViewById (R.id.register_btn);
        btnRegister.setOnClickListener (v -> {
            registerUser (edtEmail.getText().toString (), edtPassword.getText().toString (),edtUser.getText().toString ());
        });

    }

    private void registerUser (String email, String password, String userName) {

        auth.createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {

                        saveNewUser(email, userName);

                        Toast.makeText (getContext (), "Register completed!", Toast.LENGTH_LONG).show ();
                        ((AccountActivity) getActivity()).openRecipesMenu();

                    } else {
                        if (task.getException () != null) {
                            Log.e("TYAM", task.getException().getMessage());
                        }
                        Toast.makeText (getContext (), "Register failed!", Toast.LENGTH_LONG).show ();
                    }
                });


    }

    private void saveNewUser (String userID, String userName) {

        String default_foto = "https://firebasestorage.googleapis.com/v0/b/recipie-31aab.appspot.com/o/Sin%20t%C3%ADtulo-1.png?alt=media&token=416bd06f-080e-4223-aaa7-faf13795b1cd";
        ArrayList<String> fav = new ArrayList<>();

        Map<String, Object> user = new HashMap<>();
        user.put("foto", default_foto);
        user.put("name", userName);
        user.put("favorites", fav);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document( userID)
                .set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText (getContext (), "datos registrados!", Toast.LENGTH_LONG).show ())
                .addOnFailureListener(e -> Toast.makeText (getContext (), "datos no registrados!", Toast.LENGTH_LONG).show ());
    }

}


