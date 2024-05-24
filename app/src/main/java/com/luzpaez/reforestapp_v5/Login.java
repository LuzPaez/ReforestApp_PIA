package com.luzpaez.reforestapp_v5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Referencias a los elementos de la UI
        emailEditText = findViewById(R.id.EdText_Correo);
        passwordEditText = findViewById(R.id.EdText_Contrasena);
        loginButton = findViewById(R.id.btn_Ingresar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

    private void signIn(String email, String password) {
        // Iniciar sesión con email y contraseña en Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso, actualizar UI con la información del usuario
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Navegar a la siguiente pantalla o mostrar mensaje de éxito

                        Intent intent = new Intent(Login.this, Bottom_nav_view.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Ingreso Exitoso!", Toast.LENGTH_SHORT).show();

                    } else {
                        // Si el inicio de sesión falla, mostrar mensaje al usuario
                        Toast.makeText(Login.this, "Error de Autenticación.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // ELEMENTOS: links, mensaje
        // Redirige a la página de registro
        TextView crearCuentaLink = findViewById(R.id.Link_CrearCuenta);
        crearCuentaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigir a la página de registro (RegistroUsuario)
                Intent intent = new Intent(Login.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        // Redirige a la página de recuperar contraseña
        TextView OlvidoContrasenaLink = findViewById(R.id.Link_OlvidoContrasena);
        OlvidoContrasenaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigir a la página de registro (RegistroUsuario)
                Intent intent = new Intent(Login.this, RecuperarContrasena1.class);
                startActivity(intent);
            }
        });
    }
    //mensaje de error:
    private void mostrarMensajeError() {
        Toast.makeText(this, "Credenciales incorrectas. Por favor, inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
    }
}
