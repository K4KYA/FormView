package com.k4kya.customviews;

import com.k4kya.validatingformview.FormFieldChangedListener;
import com.k4kya.validatingformview.FormValidator;
import com.k4kya.validatingformview.ValidatingFormField;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter, FormFieldChangedListener {

    List<ValidatingFormField> fields;
    private MainView view;

    public MainPresenterImpl(MainView view) {
        fields = new ArrayList<>();
        this.view = view;
    }

    @Override
    public void validateForm() {
        boolean formIsValid = true;
        for (ValidatingFormField field : fields) {
            if (!field.validate()) {
                formIsValid = false;
                field.showError();
            }
        }
        if (formIsValid) {
            view.enableNextButton();
        } else {
            view.disableNextButton();
        }
    }

    @Override
    public void addFormField(ValidatingFormField formField, FormValidator validator) {
        fields.add(formField);
        formField.addValidationRule(validator);
        formField.setOnFieldChangeListener(this);
    }

    @Override
    public void onChange(String s) {
        validateForm();
    }
}
