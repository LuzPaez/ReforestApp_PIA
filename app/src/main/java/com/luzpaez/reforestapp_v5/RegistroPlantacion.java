package com.luzpaez.reforestapp_v5;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistroPlantacion extends AppCompatActivity {


    private EditText Edtext_Especie;
    private EditText Edtext_PrecioEsp;
    private EditText Edtext_CantidadSembrada;
    private EditText Edtext_Area;
    private EditText Edtext_C_sitio;
    private Button btn_GuardarRegistro;

    private TextView txtTipoArbol;
    private TextView txtPrecio;
    private TextView txtCantidad;
    private TextView txtArea;
    private TextView txtCondiciones;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_plantacion);

        Button btnEliminarRegistros = findViewById(R.id.btn_EliminarRegistro);
        btnEliminarRegistros.setOnClickListener(v -> eliminarRegistros());

        // Referencias a las MaterialCardView
        final MaterialCardView cardNavegable2 = findViewById(R.id.cardNavegable2);
        final MaterialCardView cardNavegable3 = findViewById(R.id.cardNavegable3);
        final MaterialCardView cardNavegable4 = findViewById(R.id.cardNavegable4);

        // OnClickListener para cada MaterialCardView
        cardNavegable2.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroPlantacion.this, Estadisticas.class);
            startActivity(intent);
        });
        cardNavegable3.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroPlantacion.this, Consejos.class);
            startActivity(intent);
        });
        cardNavegable4.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroPlantacion.this, Recursos.class);
            startActivity(intent);
        });

        // Referencia al ImageButton
        ImageButton botonVolver = findViewById(R.id.BotonVolver);
        botonVolver.setOnClickListener(v -> abrirOtroActivity());

        // Inicializa referencias a los EditText
        Edtext_Especie = findViewById(R.id.Edtext_Especie);
        Edtext_PrecioEsp = findViewById(R.id.Edtext_PrecioEsp);
        Edtext_CantidadSembrada = findViewById(R.id.Edtext_CantidadSembrada);
        Edtext_Area = findViewById(R.id.Edtext_Area);
        Edtext_C_sitio = findViewById(R.id.Edtext_C_sitio);

        // Botón para guardar los datos en Firestore
        Button btnGuardarDatos = findViewById(R.id.btn_GuardarRegistro);
        btnGuardarDatos.setOnClickListener(v -> guardarDatos());


        cargarDatosDesdeFirestore();

    }

    private void guardarDatos() {
        String especie = Edtext_Especie.getText().toString();
        String precio = Edtext_PrecioEsp.getText().toString();
        String cantidad = Edtext_CantidadSembrada.getText().toString();
        String area = Edtext_Area.getText().toString();
        String condiciones = Edtext_C_sitio.getText().toString();

        Map<String, Object> plantacion = new HashMap<>();
        plantacion.put("especie", especie);
        plantacion.put("precio", precio);
        plantacion.put("cantidad", cantidad);
        plantacion.put("area", area);
        plantacion.put("condiciones", condiciones);

        db.collection("plantaciones")
                .add(plantacion)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(RegistroPlantacion.this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show();
                    cargarDatosDesdeFirestore(); // Cargar datos después de guardar exitosamente
                })
                .addOnFailureListener(e -> Toast.makeText(RegistroPlantacion.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show());
    }


    private void abrirOtroActivity() {
        Intent intent = new Intent(this, Bottom_nav_view.class);
        startActivity(intent);
    }

    private void cargarDatosDesdeFirestore() {
        // Acceder a la colección "plantaciones" en Firestore
        db.collection("plantaciones")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Iterar sobre los documentos obtenidos
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener los datos de cada documento
                            String especie = document.getString("especie");
                            String precio = document.getString("precio");
                            String cantidad = document.getString("cantidad");
                            String area = document.getString("area");
                            String condiciones = document.getString("condiciones");

                            // Crear una nueva instancia de CardView
                            CardView cardView = new CardView(this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            layoutParams.setMargins(0, 0, 0, 16);
                            cardView.setLayoutParams(layoutParams);
                            cardView.setCardBackgroundColor(Color.WHITE);
                            cardView.setRadius(getResources().getDimension(R.dimen.card_corner_radius)); // Utiliza el radio definido en tus recursos dimens.xml

                            // Crear un nuevo LinearLayout para organizar los TextViews dentro de la CardView
                            LinearLayout linearLayout = new LinearLayout(this);
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));
                            linearLayout.setOrientation(LinearLayout.VERTICAL);
                            cardView.addView(linearLayout);

                            // Crear y configurar los TextViews dentro de la CardView
                            TextView txtEspecie = new TextView(this);
                            txtEspecie.setText("Especie: " + especie);
                            TextView txtPrecio = new TextView(this);
                            txtPrecio.setText("Precio: " + precio);
                            TextView txtCantidad = new TextView(this);
                            txtCantidad.setText("Cantidad: " + cantidad);
                            TextView txtArea = new TextView(this);
                            txtArea.setText("Área: " + area);
                            TextView txtCondiciones = new TextView(this);
                            txtCondiciones.setText("Condiciones: " + condiciones);

                            // Agregar los TextViews al LinearLayout
                            linearLayout.addView(txtEspecie);
                            linearLayout.addView(txtPrecio);
                            linearLayout.addView(txtCantidad);
                            linearLayout.addView(txtArea);
                            linearLayout.addView(txtCondiciones);

                            // Agregar la CardView al contenedor principal
                            LinearLayout container = findViewById(R.id.container); // Reemplaza "container" con el ID de tu contenedor principal en el XML
                            container.addView(cardView, 0);
                        }
                    } else {
                        Log.d(TAG, "Error obteniendo documentos: ", task.getException());
                    }
                });
    }

    // Método para eliminar todos los registros de Firestore y actualizar la vista
    private void eliminarRegistros() {
        db.collection("plantaciones")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Eliminar cada documento de la colección "plantaciones"
                            db.collection("plantaciones").document(document.getId())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Documento eliminado correctamente"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error al eliminar documento", e));
                        }
                        // Notificar al usuario que los registros se eliminaron correctamente
                        Toast.makeText(RegistroPlantacion.this, "Registros eliminados correctamente", Toast.LENGTH_SHORT).show();

                        // Limpiar la vista después de eliminar los registros
                        LinearLayout container = findViewById(R.id.container); // Reemplaza "container" con el ID de tu contenedor principal en el XML
                        container.removeAllViews();
                    } else {
                        Log.d(TAG, "Error obteniendo documentos para eliminar: ", task.getException());
                    }
                });
    }


}

