package com.stevensiahaan.learnrxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnIntro, btnDebounce, btnBuffer, btnJust;
    private Button btnFrom, btnRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnIntro = (Button) findViewById(R.id.btnIntro);
        btnBuffer = (Button) findViewById(R.id.btnBuffer);
        btnDebounce = (Button) findViewById(R.id.btnDebounce);
        btnJust = (Button) findViewById(R.id.btnJust);
        btnFrom = (Button) findViewById(R.id.btnFrom);
        btnRange = (Button) findViewById(R.id.btnRange);

        btnIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new MainActivity());
            }
        });

        btnJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new JustOperatorActivity());
            }
        });

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new FromOperatorActivity());
            }
        });


        btnRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new RangeOperatorActivity());
            }
        });

        btnBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new BufferOperatorActivity());
            }
        });

        btnDebounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new DebounceOperatorActivity());
            }
        });


    }

    private void navigateTo(Activity activity) {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }
}
