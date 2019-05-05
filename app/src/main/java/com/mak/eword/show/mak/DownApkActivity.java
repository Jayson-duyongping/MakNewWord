package com.mak.eword.show.mak;

import android.app.Activity;
import android.os.Bundle;

import com.mak.eword.R;
import com.mak.eword.utils.downapk.InstallUtils;


public class DownApkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._mak_activity_down_apk);

        String apkurl=getIntent().getStringExtra("url");
        InstallUtils.updateApk(this,apkurl);
    }
}
