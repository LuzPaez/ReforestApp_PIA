package com.luzpaez.reforestapp_v5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Opciones extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opciones, container, false);

        // Referencias a los botones
        Button buttonRegistroPlanta = view.findViewById(R.id.buttonRegistroPlanta);
        Button buttonEstadisticas = view.findViewById(R.id.buttonEstadisticas);
        Button buttonConsejos = view.findViewById(R.id.buttonConsejos);
        Button buttonRecursos = view.findViewById(R.id.buttonRecursos);

        // Establece clic listeners para cada botón
        buttonRegistroPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad correspondiente (RegistroPlantacion.class)
                Intent intent = new Intent(getActivity(), RegistroPlantacion.class);
                startActivity(intent);
            }
        });

        buttonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad correspondiente (Estadisticas.class)
                Intent intent = new Intent(getActivity(), Estadisticas.class);
                startActivity(intent);
            }
        });

        buttonConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad correspondiente (Consejos.class)
                Intent intent = new Intent(getActivity(), Consejos.class);
                startActivity(intent);
            }
        });

        buttonRecursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige a la actividad correspondiente (Recursos.class)
                Intent intent = new Intent(getActivity(), Recursos.class);
                startActivity(intent);
            }
        });

        return view;
    }
}