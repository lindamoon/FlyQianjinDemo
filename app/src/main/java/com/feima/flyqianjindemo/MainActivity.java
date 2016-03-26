package com.feima.flyqianjindemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private FlyControlView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = (FlyControlView) findViewById(R.id.fcv);
    }

    public void logLocation(View view) {
        mView.logLocationInfo();

    }
}
