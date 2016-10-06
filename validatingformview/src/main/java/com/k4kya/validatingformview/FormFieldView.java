package com.k4kya.validatingformview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.*;

public class FormFieldView extends LinearLayout implements ValidatingFormField {

    private final int minChars;
    private FormValidator validator;
    private TextView formLabel;
    private TextView formError;
    private EditText formField;
    private CheckBox showPassword;
    private TextWatcher textWatcher;
    private FormFieldChangedListener onFieldChangedListener;

    public FormFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FormFieldView);
        String label = attributes.getString(R.styleable.FormFieldView_label);
        String placeholder = attributes.getString(R.styleable.FormFieldView_placeholder);
        String errorMessage = attributes.getString(R.styleable.FormFieldView_errorMessage);
        int type = attributes.getInt(R.styleable.FormFieldView_type, 0);
        minChars = attributes.getInt(R.styleable.FormFieldView_minChars, 0);
        int inputTypeCode = InputType.TYPE_CLASS_TEXT;
        switch (type) {
            case 0:
                inputTypeCode = InputType.TYPE_CLASS_TEXT;
                break;
            case 1:
                inputTypeCode = InputType.TYPE_CLASS_NUMBER;
                break;
            case 2:
                inputTypeCode = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
            case 3:
                inputTypeCode = InputType.TYPE_CLASS_PHONE;
        }
        attributes.recycle();
        setupField(context);
        setupLabel(label);
        setupPlaceholder(placeholder);
        setupInputType(inputTypeCode, type);
        setupErrorMessage(errorMessage);
        setupHooks(minChars);
    }

    private void setupField(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.START);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.form_field_view, this, true);
        formField = (EditText) getChildAt(1);
    }

    private void setupErrorMessage(String errorMessage) {
        formError = (TextView) getChildAt(2);
        if (errorMessage != null) {
            formError.setText(errorMessage);
        }
    }

    private void setupInputType(int inputTypeCode, int type) {
        formField.setInputType(inputTypeCode);
        if (type == 2) {
            configureFieldAsPassword();
        }
    }

    private void configureFieldAsPassword() {
        showPassword = (CheckBox) getChildAt(3);
        showPassword.setVisibility(VISIBLE);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                int cursor = formField.getSelectionStart();
                if (checked) {
                    formField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    formField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                formField.setSelection(cursor);
            }
        });
    }

    private void setupPlaceholder(String placeholder) {
        formField.setHint(placeholder);
    }

    private void setupLabel(String label) {
        formLabel = (TextView) getChildAt(0);
        formLabel.setText(label);
    }

    @Override
    public void addValidationRule(FormValidator validator) {
        this.validator = validator;
    }

    @Override
    public void setOnFieldChangeListener(FormFieldChangedListener formFieldChangedListener) {
        this.onFieldChangedListener = formFieldChangedListener;
    }

    @Override
    public void showError() {
        formError.setVisibility(VISIBLE);
    }

    @Override
    public boolean validate() {
        if (preValidationHooks()) {
            return this.validator == null || validator.validate(formField.getText().toString());
        }
        return false;
    }

    private boolean preValidationHooks() {
        return minChars == 0 || (formField.getText().length() >= minChars);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        formField.removeTextChangedListener(textWatcher);

    }

    private void setupHooks(final int minChars) {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                formError.setVisibility(GONE);
                onValueChanged(s);
            }
        };
        formField.addTextChangedListener(textWatcher);
    }

    private void onValueChanged(Editable s) {
        if (onFieldChangedListener != null) {
            onFieldChangedListener.onChange(s.toString());
        }
    }
}
