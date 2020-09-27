package com.example.calculator.calculator;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.Objects;

public class CalculatorViewModel extends ViewModel {
    private final String PASSWORD = "123";
    private String secretModeExpression = "";

    private MutableLiveData<String> expression = new MutableLiveData<>("");

    public MutableLiveData<String> getExpression() {
        return expression;
    }

    private MutableLiveData<String> expressionResult = new MutableLiveData<>("");

    public MutableLiveData<String> getExpressionResult() {
        return expressionResult;
    }

    private MutableLiveData<Boolean> incorrectExpression = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getIncorrectExpression() {
        return incorrectExpression;
    }

    private MutableLiveData<Boolean> navigateToSecretScreen = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getNavigateToSecretScreen() {
        return navigateToSecretScreen;
    }

    private MutableLiveData<Boolean> isSecretMode = new MutableLiveData(false);

    public MutableLiveData<Boolean> getIsSecretMode() {
        return isSecretMode;
    }

    public void addSymbol(String text) {
        if (isSecretMode.getValue()) {
            secretModeExpression = secretModeExpression + text;
            expression.setValue(expression.getValue() + text);
        }
        if (!expressionResult.getValue().equals("")){
            expression.setValue(expressionResult.getValue() + text);
            expressionResult.setValue("");
        }

        else expression.setValue(expression.getValue() + text);
    }

    public void deleteSymbol() {
        if (Objects.requireNonNull(expression.getValue()).length() > 0)
            expression.setValue(expression.getValue().substring(0, expression.getValue().length() - 1));
    }

    public void clear() {
        expression.setValue("");
        expressionResult.setValue("");
    }

    public void calculate() {
        try {
            Expression expression = new ExpressionBuilder(Objects.requireNonNull(this.expression.getValue())).build();
            double result = expression.evaluate();
            long resultAsLongNumber = (long) result;
            if (result == (double) resultAsLongNumber)
                this.expressionResult.setValue(String.valueOf(resultAsLongNumber));
            else this.expressionResult.setValue(String.valueOf(result));
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
        secretModeExpression = "";
    }

    public boolean checkSecretPassword() {
        return secretModeExpression.equals(PASSWORD);
    }

    public void counter() {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkSecretPassword()) navigateToSecretScreen.setValue(true);
                turnSecretModeOff();
            }
        }, 5000);
    }
}
