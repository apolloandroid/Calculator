package com.example.calculator.calculator;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CalculatorViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalculatorViewModel.class))
            return (T) new CalculatorViewModel();
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
