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
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{
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
    private LoginPresenter presenter;

    /*
    * ButterKnife
    * Retrofit
    * Retrolambda
    * Reactive
    *
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog= new MaterialDialog.Builder(this)
                .title(R.string.loading)
                .progress(true,0).build();

        presenter= new LoginPresenterImpl(this,new LoginInteractorImpl());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.loginButton)
    public void onViewClicked() {
        presenter.validateCredentials(username.getText().toString(),password.getText().toString());
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.empty_username));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.empty_password));
    }

    @Override
    public void showInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToHome() {
        Intent i= new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
