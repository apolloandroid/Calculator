package com.example.calculator.calculator;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Objects;

public class CalculatorViewModel extends ViewModel {
    private final String PASSWORD = "123";

    private MutableLiveData<String> expression = new MutableLiveData<>("");

    public LiveData<String> getExpression() {
        return expression;
    }

    private MutableLiveData<String> expressionSecret = new MutableLiveData<>("");

    private MutableLiveData<String> expressionResult = new MutableLiveData<>("");

    public LiveData<String> getExpressionResult() {
        return expressionResult;
    }

    private MutableLiveData<Boolean> incorrectExpression = new MutableLiveData<>(false);

    public LiveData<Boolean> getIncorrectExpression() {
        return incorrectExpression;
    }

    private MutableLiveData<Boolean> navigateToSecretScreen = new MutableLiveData<>(false);

    public LiveData<Boolean> getNavigateToSecretScreen() {
        return navigateToSecretScreen;
    }

    private MutableLiveData<Boolean> isSecretMode = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsSecretMode() {
        return isSecretMode;
    }

    public void addSymbol(String text) {
        if (isSecretMode.getValue()) {
            expressionSecret.setValue(expressionSecret.getValue() + text);
            expression.setValue(expression.getValue() + text);
            checkSecretPassword();
            if (expressionSecret.getValue().length() >= 3) expressionSecret.setValue("");
        } else {
            if (!expressionResult.getValue().equals("")) {
                expression.setValue(expressionResult.getValue() + text);
                expressionResult.setValue("");
            } else expression.setValue(expression.getValue() + text);
        }
    }

    public void deleteLastSymbol() {
        if (Objects.requireNonNull(expression.getValue()).length() > 0)
            expression.setValue(expression.getValue().substring(0, expression.getValue().length() - 1));
    }

    public void clearInput() {
        expression.setValue("");
        expressionResult.setValue("");
    }

    public void calculate() {
        try {
            Expression expression = new ExpressionBuilder(Objects.requireNonNull(this.expression.getValue())).build();
            double result = expression.evaluate();
            long resultAsLongNumber = (long) result;
            if (result == (double) resultAsLongNumber)
                expressionResult.setValue(String.valueOf(resultAsLongNumber));
            else expressionResult.setValue(String.format("%.2f", result));
        } catch (Exception e) {
            incorrectExpression.setValue(true);
        }
    }

    public void turnSecretModeOn() {
        isSecretMode.setValue(true);
        counter();
    }

    public void turnSecretModeOff() {
        isSecretMode.setValue(false);
        navigateToSecretScreen.setValue(false);
        expressionSecret.setValue("");
    }

    public void checkSecretPassword() {
        if (expressionSecret.getValue().equals(PASSWORD)) navigateToSecretScreen.setValue(true);
    }

    public void counter() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                turnSecretModeOff();
            }
        }, 5000);
    }
}
