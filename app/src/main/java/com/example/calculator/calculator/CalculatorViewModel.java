package com.example.calculator.calculator;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Objects;

public class CalculatorViewModel extends ViewModel {
    private long secretModeStartTime;
    private String expressionInSecretMode = "";
    private MutableLiveData<String> expression = new MutableLiveData<>("");
    private MutableLiveData<String> expressionResult = new MutableLiveData<>("0");
    private MutableLiveData<Boolean> incorrectExpression = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> navigateToSecretScreenOn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> secretModeOn = new MutableLiveData<>(false);

    public long getSecretModeStartTime() {
        return secretModeStartTime;
    }

    public void setSecretModeStartTime(long secretModeStartTime) {
        this.secretModeStartTime = secretModeStartTime;
    }

    public long getSecretModeDuration() {
        return 4000L;
    }

    public LiveData<String> getExpression() {
        return expression;
    }

    public LiveData<String> getExpressionResult() {
        return expressionResult;
    }

    public LiveData<Boolean> isIncorrectExpression() {
        return incorrectExpression;
    }

    public LiveData<Boolean> isNavigateToSecretScreenOn() {
        return navigateToSecretScreenOn;
    }

    public LiveData<Boolean> isSecretModeOn() {
        return secretModeOn;
    }

    public void enterSymbol(String text) {
        if (secretModeOn.getValue()) {
            expressionInSecretMode  += text;
            expression.setValue(expression.getValue() + text);
            checkExpressionInSecretMode();
            if (expressionInSecretMode.length() >= 3) expressionInSecretMode = "";
        } else {
            if (!expressionResult.getValue().equals("")) {
                if (expressionResult.getValue().equals("0")) {
                    expression.setValue(expression.getValue() + text);
                } else {
                    expression.setValue(expressionResult.getValue() + text);
                    expressionResult.setValue("");
                }
            } else expression.setValue(expression.getValue() + text);
        }
    }

    public void deleteLastSymbol() {
        if (Objects.requireNonNull(expression.getValue()).length() > 0)
            expression.setValue(expression.getValue().substring(0, expression.getValue().length() - 1));
    }

    public void clearInput() {
        expression.setValue("");
        expressionResult.setValue("0");
    }

    @SuppressLint("DefaultLocale")
    public void calculateExpression() {
        try {
            Expression expressionToEvaluate = new ExpressionBuilder(
                    Objects.requireNonNull(expression.getValue())).build();
            double result = expressionToEvaluate.evaluate();
            if (result == (long) result) expressionResult.setValue(String.valueOf((long) result));
            else expressionResult.setValue(String.format("%.2f", result));
        } catch (Exception e) {
            incorrectExpression.setValue(true);
        }
    }

    public void turnSecretModeOn() {
        secretModeOn.setValue(true);
        setUpCounter();
    }

    public void turnSecretModeOff() {
        secretModeOn.setValue(false);
        navigateToSecretScreenOn.setValue(false);
        expressionInSecretMode = "";
    }

    private void setUpCounter() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                turnSecretModeOff();
            }
        }, 5000);
    }

    private void checkExpressionInSecretMode() {
        String passwordToSecretScreen = "123";
        if (expressionInSecretMode.equals(passwordToSecretScreen))
            navigateToSecretScreenOn.setValue(true);
    }
}
