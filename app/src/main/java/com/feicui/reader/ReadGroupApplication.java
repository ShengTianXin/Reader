package com.feicui.reader;

import android.content.Intent;

import com.feicui.apphx.HxBaseApplication;
import com.feicui.reader.presentation.SplashActivity;


public class ReadGroupApplication extends HxBaseApplication {

    @Override protected void exit() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
