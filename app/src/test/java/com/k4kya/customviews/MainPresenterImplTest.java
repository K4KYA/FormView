package com.k4kya.customviews;

import com.k4kya.validatingformview.FormFieldChangedListener;
import com.k4kya.validatingformview.FormValidator;
import com.k4kya.validatingformview.ValidatingFormField;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MainPresenterImplTest {

    private ValidatingFormField mockFormField;
    private FormValidator alwaysValidForm;
    private FormValidator alwaysInValidForm;

    @Before
    public void setUp() throws Exception {

        alwaysInValidForm = new FormValidator() {
            @Override
            public boolean validate(String value) {
                return false;
            }
        };

        alwaysValidForm = new FormValidator() {
            @Override
            public boolean validate(String value) {
                return true;
            }
        };

        mockFormField = new ValidatingFormField() {
            private FormValidator validator;

            @Override
            public boolean validate() {
                return this.validator.validate("hello");
            }

            @Override
            public void addValidationRule(FormValidator validator) {
                this.validator = validator;
            }

            @Override
            public void setOnFieldChangeListener(FormFieldChangedListener formFieldChangedListener) {
                formFieldChangedListener.onChange("hello");
            }

            @Override
            public void showError() {

            }
        };
    }

    @Test
    public void testPresenterEnablesNextWithValidInput() throws Exception {
        MainView testView = new MainView() {
            @Override
            public void enableNextButton() {
                // Passing path
            }

            @Override
            public void disableNextButton() {
                fail("Button was disabled");
            }
        };
        MainPresenter presenter = new MainPresenterImpl(testView);
        presenter.addFormField(mockFormField, alwaysValidForm);
    }

    @Test
    public void testPresenterDisablesNextWithInvalidInput() {
        MainView testView = new MainView() {
            @Override
            public void enableNextButton() {
                fail("Button was enabled");
            }

            @Override
            public void disableNextButton() {
                // Pass
            }
        };

        MainPresenter presenter = new MainPresenterImpl(testView);
        presenter.addFormField(mockFormField, alwaysInValidForm);
    }

    @Test
    public void testPresenterValidatesOnFieldChange() {
        MainView testView = new MainView() {
            @Override
            public void enableNextButton() {

            }

            @Override
            public void disableNextButton() {
                // Pass
            }
        };

        MainPresenter presenter = new MainPresenterImpl(testView);
        presenter.addFormField(mockFormField, alwaysValidForm);

    }
}