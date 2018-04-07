package com.example.ricardogarcia.photocloud.activities.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.photocloud.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.register.core.RegisterModel;
import com.example.ricardogarcia.photocloud.activities.register.core.RegisterPresenter;
import com.example.ricardogarcia.photocloud.activities.register.dagger.DaggerRegisterComponent;
import com.example.ricardogarcia.photocloud.activities.register.dagger.RegisterModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @Inject
    RegisterModel model;

    RegisterPresenter presenter;

    @BindView(R.id.signup)
    TextView signup;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.registerButton)
    Button registerButton;

    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        DaggerRegisterComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).registerModule(new RegisterModule(this)).build().inject(this);

        progressDialog= new MaterialDialog.Builder(this)
                .title(R.string.signing_up)
                .progress(true,0).build();

        presenter=new RegisterPresenter(this,model);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.dismiss();
    }

    public void setFirstNameError(){
        firstName.setError(getString(R.string.invalid_field));
    }

    public void setLastNameError(){
        lastName.setError(getString(R.string.invalid_field));
    }

    public void setEmailError(){
        email.setError(getString(R.string.invalid_field));
    }

    public void setPasswordError(){
        password.setError(getString(R.string.invalid_field));
    }

    public void showAlreadyExistingEmail(){
        Toast.makeText(this, getString(R.string.existing_email), Toast.LENGTH_SHORT).show();
    }

    public void showError(){
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    public void goHome(){
        Intent i=new Intent(this,HomeActivity.class);
        finish();
        startActivity(i);
    }

    @OnClick(R.id.registerButton)
    public void onViewClicked() {
        presenter.registerUser(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());
    }
}
