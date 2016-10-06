package com.k4kya.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import com.k4kya.validatingformview.FormFieldView;
import com.k4kya.validatingformview.FormValidator;

public class MainActivity extends AppCompatActivity implements MainView {

    LinearLayout root;
    MainPresenter presenter;
    Button btnNext;
    Button btnAddField;

    @Override
    public void enableNextButton() {
        btnNext.setEnabled(true);
    }

    @Override
    public void disableNextButton() {
        btnNext.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnAddField = (Button) findViewById(R.id.btn_add_field);
        root = (LinearLayout) findViewById(R.id.main_root);

        FormFieldView name = (FormFieldView) findViewById(R.id.field_name);
        presenter.addFormField(name, new FormValidator() {
            @Override
            public boolean validate(String value) {
                return (value.equals("Amal"));
            }
        });
        FormFieldView phone = (FormFieldView) findViewById(R.id.phone_no);
        presenter.addFormField(phone);
        FormFieldView password = (FormFieldView) findViewById(R.id.field_password);
        presenter.addFormField(password);
    }
}
