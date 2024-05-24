package com.luzpaez.reforestapp_v5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Configuracion extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        // Edit & buton
        EditText nombreEditText = view.findViewById(R.id.EdText_NomUsuario);
        EditText telefonoEditText =view.findViewById(R.id.EdText_Tel);
        Button actualizarDatosButton = view.findViewById(R.id.btn_ActualizarDatos);

        actualizarDatosButton.setOnClickListener(v -> {
            String nombre = nombreEditText.getText().toString();
            String telefono = telefonoEditText.getText().toString();
            guardarDatosUsuario(nombre, telefono);
        });

        return view;
    }

    private void guardarDatosUsuario(String nombre, String telefono) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = ((FirebaseUser) currentUser).getUid();
            Map<String, Object> user = new HashMap<>();
            user.put("nombre", nombre);
            user.put("telefono", telefono);

            db.collection("usuarios").document(userId).set(user)
                    .addOnSuccessListener(aVoid -> {
                        // Datos guardados correctamente
                        Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Error al guardar datos
                        Toast.makeText(getActivity(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
