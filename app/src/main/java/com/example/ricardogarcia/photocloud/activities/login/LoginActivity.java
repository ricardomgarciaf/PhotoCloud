package com.example.ricardogarcia.photocloud.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.application.PhotoCloudApplication;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;
import com.example.ricardogarcia.photocloud.activities.login.core.LoginModel;
import com.example.ricardogarcia.photocloud.activities.login.core.LoginPresenter;
import com.example.ricardogarcia.photocloud.activities.login.dagger.DaggerLoginComponent;
import com.example.ricardogarcia.photocloud.activities.login.dagger.LoginModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginModel model;

    LoginPresenter presenter;

    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.loginButton)
    Button loginButton;

    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerLoginComponent.builder().appComponent(PhotoCloudApplication.getAppComponent()).loginModule(new LoginModule(this)).build().inject(this);
        setContentView(R.layout.activity_login);

        progressDialog= new MaterialDialog.Builder(this)
                .title(R.string.loading)
                .progress(true,0).build();
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, model);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void goHome() {
        Intent i = new Intent(this, HomeActivity.class);
        finish();
        startActivity(i);
    }

    @OnClick(R.id.loginButton)
    public void onViewClicked() {
        presenter.validateCredentials(username.getText().toString(),password.getText().toString());
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.dismiss();
    }

    public void setUsernameError(){
        username.setError(getString(R.string.empty_username));
    }

    public void setPasswordError(){
        password.setError(getString(R.string.empty_password));
    }

    public void showInvalidCredentials(){
        Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
    }

    public void showUserBlocked(){
        Toast.makeText(this, getString(R.string.user_blocked), Toast.LENGTH_SHORT).show();
    }

    public void showError(){
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }
}
