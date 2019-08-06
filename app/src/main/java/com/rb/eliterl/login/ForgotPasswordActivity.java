package com.rb.eliterl.login;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rb.eliterl.BaseActivity;
import com.rb.eliterl.R;
import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.controller.register.RegisterController;
import com.rb.eliterl.core.response.CommonResponse;
import com.rb.eliterl.utility.Constants;

public class ForgotPasswordActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

    EditText etMobile;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,ForgotPasswordActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    etMobile.setError("Enter Mobile");
                    return;
                }

                showDialog("Please Wait...");
                new RegisterController(ForgotPasswordActivity.this).forgotPassword(etMobile.getText().toString(), this);
                break;

        }

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof CommonResponse) {
            if (response.getStatus_code() == 0) {
                //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                getCustomToast( response.getMessage());
                etMobile.setText("");

            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
