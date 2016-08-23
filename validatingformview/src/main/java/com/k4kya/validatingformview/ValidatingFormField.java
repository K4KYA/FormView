package com.k4kya.validatingformview;

public interface ValidatingFormField {
    boolean validate();
    void addValidationRule(FormValidator validator);
    void setOnFieldChangeListener(FormFieldChangedListener formFieldChangedListener);
    void showError();
}
