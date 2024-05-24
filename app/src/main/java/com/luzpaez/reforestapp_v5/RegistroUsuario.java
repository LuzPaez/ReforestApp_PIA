package com.luzpaez.reforestapp_v5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistroUsuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //inicializar Firebase Auth

        mAuth = FirebaseAuth.getInstance();

        //Referencia a los elementos de la UI

        emailEditText =  findViewById(R.id.EdText_Correo);
        passwordEditText = findViewById(R.id.EdText_Contraseña);
        repeatPasswordEditText = findViewById(R.id.EdText_RepetirContraseña);

        registerButton =  findViewById(R.id.btn_Registrarse);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta(emailEditText.getText().toString(),passwordEditText.getText().toString());

            }
        });
    }

    private void crearCuenta(String email, String password){
        //Verificar que las contraseñas coincidan y no esnten vacias

        if (!password.equals(repeatPasswordEditText.getText().toString())){
            //Mostrar mensaje de error
            return;
        }

        // Crear usuario con email y contraseña en Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso, actualizar UI con la información del usuario
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Navegar a la siguiente pantalla o mostrar mensaje de éxito
                        Intent intent = new Intent(RegistroUsuario.this, Login.class);
                        startActivity(intent);
                        Toast.makeText(RegistroUsuario.this, "Registro Exitoso!", Toast.LENGTH_SHORT).show();

                    } else {
                        // Si el registro falla, mostrar mensaje al usuario
                        Toast.makeText(RegistroUsuario.this, "Autenticación Fallida.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // ELEMENTOS: links, mensaje
        // Redirige a la página de Inicio
        TextView InicioLink = findViewById(R.id.Link_IrInicio);
        InicioLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigir a la página de inicio
                Intent intent = new Intent(RegistroUsuario.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void mostrarMensajeErrorCheck() {
        Toast.makeText(this, "Debes aceptar las condiciones para continuar ", Toast.LENGTH_SHORT).show();
    }
    private void mostrarMensajeExito() {
        Toast.makeText(this, "¡Registro exitoso! Tus datos se han guardado correctamente.", Toast.LENGTH_SHORT).show();
    }
}


