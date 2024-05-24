package com.luzpaez.reforestapp_v5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class RecuperarContrasena1 extends AppCompatActivity {

    private EditText emailEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena1);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.EdText_Correo);

        // El botón enviará el correo electrónico de recuperación y mostrará un mensaje de alerta
        Button btn_EnviarCodigo = findViewById(R.id.btn_EnviarCod);
        btn_EnviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Se envió el correo de recuperación correctamente
                                        mostrarMensajeAlerta("Correo de recuperación enviado", "Se ha enviado un correo electrónico de recuperación a " + email);
                                    } else {
                                        // Error al enviar el correo de recuperación
                                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                            mostrarMensajeAlerta("Error", "No se encontró ninguna cuenta asociada a este correo electrónico.");
                                        } else {
                                            mostrarMensajeAlerta("Error", "Se produjo un error al enviar el correo de recuperación. Por favor, inténtalo de nuevo más tarde.");
                                        }
                                    }
                                }
                            });
                } else {
                    // El campo de correo electrónico está vacío
                    mostrarMensajeAlerta("Campo vacío", "Por favor, ingrese su correo electrónico.");
                }
            }
        });

        // Redirige a la página de Inicio
        TextView InicioLink = findViewById(R.id.Link_IrInicio);
        InicioLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirigir a la página de inicio
                Intent intent = new Intent(RecuperarContrasena1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Redirige a la página de registro
        TextView crearCuentaLink = findViewById(R.id.Link_CrearCuenta);
        crearCuentaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecuperarContrasena1.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarMensajeAlerta(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Aquí puedes realizar alguna acción adicional si es necesario
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
