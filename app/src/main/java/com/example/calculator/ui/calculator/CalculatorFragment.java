package com.example.calculator.ui.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.calculator.R;
import com.example.calculator.databinding.FragmentCalculatorBinding;
import com.example.calculator.di.DaggerAppComponent;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;


public class CalculatorFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private FragmentCalculatorBinding binding;
    @Inject
    public CalculatorViewModel viewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DaggerAppComponent.create().injectCalculatorFragment(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.buttonCalculate.setOnTouchListener(this);
        binding.textOutputResult.setText(viewModel.getExpressionResult().getValue());
        setOnClickListeners();
        initObservers();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        Button pressedButton = (Button) v;
        String text = pressedButton.getId() == R.id.button_multiply ?
                "*" : pressedButton.getText().toString();
        switch (pressedButton.getId()) {
            case R.id.button_delete:
                viewModel.deleteLastSymbol();
                break;
            case R.id.button_clear:
                viewModel.clearInput();
                break;
            default:
                viewModel.enterSymbol(text);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            viewModel.setSecretModeStartTime(System.currentTimeMillis());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            long totalTime = System.currentTimeMillis() - viewModel.getSecretModeStartTime();
            if (totalTime >= viewModel.getSecretModeDuration()) viewModel.turnSecretModeOn();
            else viewModel.calculateExpression();
        }
        return true;
    }

    private void initObservers() {
        viewModel.isIncorrectExpression().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isIncorrectExpression) {
                if (isIncorrectExpression)
                    Snackbar.make(binding.getRoot(), R.string.text_incorrect_expression, Snackbar.LENGTH_LONG).show();
            }
        });

        viewModel.isSecretModeOn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSecretModeOn) {
                if (isSecretModeOn) vibrate();
            }
        });

        viewModel.isNavigateToSecretScreenOn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navigateToSecretScreenOn) {
                if (navigateToSecretScreenOn) {
                    navigateToSecretScreen();
                    viewModel.turnSecretModeOff();
                }
            }
        });
    }

    private void setOnClickListeners() {
        binding.buttonAdd.setOnClickListener(this);
        binding.buttonSubtract.setOnClickListener(this);
        binding.buttonMultiply.setOnClickListener(this);
        binding.buttonDivide.setOnClickListener(this);
        binding.buttonOpenBracket.setOnClickListener(this);
        binding.buttonCloseBracket.setOnClickListener(this);
        binding.buttonZero.setOnClickListener(this);
        binding.buttonOne.setOnClickListener(this);
        binding.buttonTwo.setOnClickListener(this);
        binding.buttonThree.setOnClickListener(this);
        binding.buttonFour.setOnClickListener(this);
        binding.buttonFive.setOnClickListener(this);
        binding.buttonSix.setOnClickListener(this);
        binding.buttonSeven.setOnClickListener(this);
        binding.buttonEight.setOnClickListener(this);
        binding.buttonNine.setOnClickListener(this);
        binding.buttonDot.setOnClickListener(this);
        binding.buttonDelete.setOnClickListener(this);
        binding.buttonClear.setOnClickListener(this);
    }

    private void navigateToSecretScreen() {
        if (Objects.requireNonNull(Navigation.findNavController(binding.getRoot())
                .getCurrentDestination()).getId() == R.id.calculatorFragment) {
            Navigation.findNavController(binding
                    .getRoot())
                    .navigate(R.id.action_calculatorFragment_to_secretFragment);
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100);
        }
    }
}
