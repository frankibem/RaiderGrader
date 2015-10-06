package android.app.rgs.com.raidergrader.helpers;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class RgsTextWatcher implements TextWatcher {
    private Window window;
    private TextView view;
    private TextInputLayout textInputLayout;
    private ValidateConstant textType;

    public RgsTextWatcher(Window window, TextView view, TextInputLayout textInputLayout, ValidateConstant textType) {
        this.window = window;
        this.view = view;
        this.textInputLayout = textInputLayout;
        this.textType = textType;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (textType) {
            case EMAIL:
                if (!Validators.validateEmail(view.getText().toString())) {
                    textInputLayout.setError("Enter a valid email address");
                    requestFocus(view);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
                break;
            case PASSWORD:
                if (!Validators.validatePassword(view.getText().toString())) {
                    textInputLayout.setError("Enter the password");
                    requestFocus(view);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
                break;
        }
    }
}