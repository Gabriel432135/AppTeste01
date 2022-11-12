package com.example.testelogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testelogin.databinding.TelaCriarBinding;
import com.example.testelogin.databinding.TelaLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaCriar extends AppCompatActivity {
    TelaCriarBinding binding;
    GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = TelaCriarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.button.setOnClickListener(view1 -> {
            try {
                criarUsuarioSenha(binding.editTextTextUsuRio.getText().toString(), binding.editTextTextPassword.getText().toString());
            }catch (Exception e){
                Toast.makeText(this.getApplicationContext(), "Preencha os campos corretamente", Toast.LENGTH_LONG);
            }
        });

        setContentView(view);
    }

    public void criarUsuarioSenha(String usuario, String senha){
        mAuth.createUserWithEmailAndPassword(usuario, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Falha ao criar novo usuário: "+task.getException(), Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
