package com.example.liquiya;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils;

public class Registro extends AppCompatActivity {
    Button buttonRegister;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUsername;
    TextView textViewMessage;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        this.editTextUsername = (EditText) findViewById(R.id.Registro_Nombre);
        this.editTextEmail = (EditText) findViewById(R.id.Registro_Correo);
        this.editTextPassword = (EditText) findViewById(R.id.Registro_Contraseña);
        this.buttonRegister = (Button) findViewById(R.id.BRegistrarse);
        this.textViewMessage = (TextView) findViewById(R.id.Mensaje);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            textViewMessage.setText("Por favor, complete todos los campos.");
            return;
        }

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(Registro.this.editTextEmail.getText().toString().trim(), Registro.this.editTextPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @SuppressLint("SetTextI18n")
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Registro.this.textViewMessage.setText("Registro exitoso para " + mAuth.getCurrentUser().getEmail());
                            Registro.this.editTextUsername.setText("");
                            Registro.this.editTextEmail.setText("");
                            Registro.this.editTextPassword.setText("");
                            return;
                        }
                        Registro.this.textViewMessage.setText("Error al registrar el usuario: " + task.getException().getMessage());
                    }
                });
            }
        });
    }
}
