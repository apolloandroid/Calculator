package com.example.calculator.secretscreen;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculator.R;
import com.example.calculator.databinding.FragmentSecretBinding;


public class SecretFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSecretBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_secret, container, false);

        binding.toolbar.getNavigationIcon().setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.navigationButtonColor),
                PorterDuff.Mode.SRC_ATOP);

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        return binding.getRoot();
    }
}