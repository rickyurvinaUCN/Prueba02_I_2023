package com.example.prueba02;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prueba02.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //HACER LA CODIFICACION NECESARIA PARA QUE
        // EXISTA UN INPUT DE TEXTO Y CUANDO EL USUARIO DA
        // CLIC SOBRE NAVEGAR LA APLICACION VAYA AL ACTIVITYCRUD Y
        // CUANDO SE MUESTRE CORRECTAMENTE EL ACTIVITYCRUD MUESTRE UN T
        // OAST CON EL TEXTO QUE ENVIO DESDE EL INPUT DE ESTE FRAGMENT
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void navigate(){
        Intent i= new Intent(this.getContext(), ActivityCrud.class);
        startActivity(i);
    }

}