package com.example.ricardogarcia.photocloud.activities.login.core;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ricardo Garcia on 3/30/2018.
 */

public class LoginView {

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

    private View view;

    private Context context;

    public LoginView(LoginActivity context, LoginPresenter presenter) {
        FrameLayout parent = new FrameLayout(context);
        parent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view = LayoutInflater.from(context).inflate(R.layout.activity_login, parent, false);
        this.context=context;
        this.presenter=presenter;
        ButterKnife.bind(this,view);
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
        username.setError(context.getString(R.string.empty_username));
    }

    public void setPasswordError(){
        password.setError(context.getString(R.string.empty_password));
    }

    public void showInvalidCredentials(){
        Toast.makeText(context, context.getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
    }

    public void showUserBlocked(){
        Toast.makeText(context, context.getString(R.string.user_blocked), Toast.LENGTH_SHORT).show();
    }

    public void showError(){
        Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    public View getView() {
        return view;
    }
}
