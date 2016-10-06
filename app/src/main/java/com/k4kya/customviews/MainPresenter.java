package com.k4kya.customviews;

import com.k4kya.validatingformview.FormValidator;
import com.k4kya.validatingformview.ValidatingFormField;

public interface MainPresenter {
    void validateForm();
    void addFormField(ValidatingFormField formField, FormValidator validator);
    void addFormField(ValidatingFormField formField);
}
