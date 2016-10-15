package com.feicui.reader.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feicui.reader.HomeActivity;
import com.feicui.reader.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/10/15 0015.
 */
public class LoginFragment extends DialogFragment implements LoginView {

    @BindView(R.id.edit_username)
    TextInputEditText etUsername;
    @BindView(R.id.edit_password)
    TextInputEditText etPassword;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.button_confirm)
    Button btnConfirm;
    private Unbinder unbinder;
    private LoginPresenter loginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter();
        loginPresenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        loginPresenter.attachView(this);
    }
    // 按下登录按钮，协调人开始干活
    @OnClick(R.id.button_confirm)
    public void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        loginPresenter.login(username, password);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        loginPresenter.detachView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }

    // start View Interface -------------------------------------
    @Override public void showLoading() {
        setCancelable(false);
        btnConfirm.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideLoading() {
        setCancelable(true);
        btnConfirm.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToHome() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    // end View Interface -------------------------------------
}
