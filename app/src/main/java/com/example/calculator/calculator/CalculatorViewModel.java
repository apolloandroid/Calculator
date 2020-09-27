package com.example.calculator.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Objects;

public class CalculatorViewModel extends ViewModel {
    private final String PASSWORD = "123";
    private boolean isSecretMode = false;
    private String secretModeExpression = "";

    private MutableLiveData<String> expression = new MutableLiveData<>("");

    public MutableLiveData<String> getExpression() {
        return expression;
    }

    private MutableLiveData<String> result = new MutableLiveData<>("");

    public MutableLiveData<String> getResult() {
        return result;
    }

    private MutableLiveData<Boolean> incorrectExpression = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getIncorrectExpression() {
        return incorrectExpression;
    }

    private MutableLiveData<Boolean> navigateToSecretScreen = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getNavigateToSecretScreen() {
        return navigateToSecretScreen;
    }

    public void addSymbol(String text) {
        if (isSecretMode) {
            secretModeExpression += text;
            expression.setValue(expression.getValue() + text);
        }
        expression.setValue(expression.getValue() + text);
    }

    public void deleteSymbol() {
        if (Objects.requireNonNull(expression.getValue()).length() > 0)
            expression.setValue(expression.getValue().substring(0, expression.getValue().length() - 1));
    }

    public void clear() {
        expression.setValue("");
        result.setValue("");
    }

    public void calculate() {
        if (!isSecretMode) {
            try {
                Expression expressionBuilder = new ExpressionBuilder(Objects.requireNonNull(expression.getValue())).build();
                double expressionResult = expressionBuilder.evaluate();
                result.setValue(String.valueOf(expressionResult));
            } catch (Exception e) {
                incorrectExpression.setValue(true);
            }
        } else {

        }


    }

    public void turnSecretModeOn() {
        isSecretMode = true;
    }

    public void turnSecretModeOff() {
        isSecretMode = false;
        navigateToSecretScreen.setValue(false);
        secretModeExpression = "";

    }

    public void checkSecretPassword() {
        if (secretModeExpression.equals(PASSWORD)) navigateToSecretScreen.setValue(true);
    }
}
