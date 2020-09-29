package com.example.calculator.di;

import com.example.calculator.ui.calculator.CalculatorViewModel;
import com.example.calculator.ui.calculator.CalculatorViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
class CalculatorFragmentModule {
    @Singleton
    @Provides
     CalculatorViewModel providesCalculatorViewModel() {
        CalculatorViewModelFactory factory = new CalculatorViewModelFactory();
        return factory.create(CalculatorViewModel.class);
    }
}
