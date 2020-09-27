package com.example.calculator.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.calculator.R;
import com.example.calculator.databinding.FragmentCalculatorBinding;
import com.google.android.material.snackbar.Snackbar;


public class CalculatorFragment extends Fragment implements View.OnClickListener {
    private FragmentCalculatorBinding binding;
    private CalculatorViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new CalculatorViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setOnClickListeners();
        initObservers();

        return binding.getRoot();
    }

    private void initObservers() {
        viewModel.getIncorrectExpression().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Snackbar.make(binding.getRoot(), "Incorrect expression", Snackbar.LENGTH_LONG);
            }
        });

        viewModel.getNavigateToSecretScreen().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) navigateToSecretScreen();
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
//        binding.buttonCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String buttonText = "";
        switch (v.getId()) {
            case R.id.button_add:
                buttonText = binding.buttonAdd.getText().toString();
                break;
            case R.id.button_subtract:
                buttonText = binding.buttonSubtract.getText().toString();
                break;
            case R.id.button_multiply:
                buttonText = binding.buttonMultiply.getText().toString();
                break;
            case R.id.button_divide:
                buttonText = binding.buttonDivide.getText().toString();
                break;
            case R.id.button_open_bracket:
                buttonText = binding.buttonOpenBracket.getText().toString();
                break;
            case R.id.button_close_bracket:
                buttonText = binding.buttonCloseBracket.getText().toString();
                break;
            case R.id.button_zero:
                buttonText = binding.buttonZero.getText().toString();
                break;
            case R.id.button_one:
                buttonText = binding.buttonOne.getText().toString();
                break;
            case R.id.button_two:
                buttonText = binding.buttonTwo.getText().toString();
                break;
            case R.id.button_three:
                buttonText = binding.buttonThree.getText().toString();
                break;
            case R.id.button_four:
                buttonText = binding.buttonFour.getText().toString();
                break;
            case R.id.button_five:
                buttonText = binding.buttonFive.getText().toString();
                break;
            case R.id.button_six:
                buttonText = binding.buttonSix.getText().toString();
                break;
            case R.id.button_seven:
                buttonText = binding.buttonSeven.getText().toString();
                break;
            case R.id.button_eight:
                buttonText = binding.buttonEight.getText().toString();
                break;
            case R.id.button_nine:
                buttonText = binding.buttonNine.getText().toString();
                break;
            case R.id.button_dot:
                buttonText = binding.buttonDot.getText().toString();
                break;
            case R.id.button_delete:
                viewModel.deleteSymbol();
                break;
            case R.id.button_clear:
                viewModel.clear();
                break;
            case R.id.button_calculate:
                viewModel.calculate();
                break;
        }

        viewModel.addSymbol(buttonText);
    }

    private void navigateToSecretScreen() {
        if (Navigation.findNavController(binding.getRoot()).getCurrentDestination().getId() == R.id.calculatorFragment) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_calculatorFragment_to_secretFragment);
        }
    }
}