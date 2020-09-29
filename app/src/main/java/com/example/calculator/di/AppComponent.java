package com.example.calculator.di;

import com.example.calculator.ui.calculator.CalculatorFragment;


import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = CalculatorFragmentModule.class)
public interface AppComponent {
    void injectCalculatorFragment(CalculatorFragment calculatorFragment);
}
