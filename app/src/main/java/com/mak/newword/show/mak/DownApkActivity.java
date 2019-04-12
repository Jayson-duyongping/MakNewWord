package com.mak.newword.show.mak;

import android.app.Activity;
import android.os.Bundle;

import com.mak.newword.R;
import com.mak.newword.utils.downapk.InstallUtils;


public class DownApkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._mak_activity_down_apk);

        String apkurl=getIntent().getStringExtra("url");
        InstallUtils.updateApk(this,apkurl);
    }
}
