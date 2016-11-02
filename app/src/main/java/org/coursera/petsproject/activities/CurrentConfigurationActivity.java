package org.coursera.petsproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.coursera.petsproject.R;
import org.coursera.petsproject.activities.Interfaces.ICurrentConfigurationActivity;

public class CurrentConfigurationActivity extends AppCompatActivity implements ICurrentConfigurationActivity{

    private TextView tvLabConfigKeyACC, tvLabConfigDispACC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_configuration);

        initializeComponents();
    }

    private void initializeComponents(){
        tvLabConfigKeyACC = (TextView) findViewById(R.id.tvLabConfigKeyACC);
        tvLabConfigDispACC = (TextView) findViewById(R.id.tvLabConfigDispACC);

        getData();
    }

    @Override
    public void goNewConfiguration(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getData() {
        if(getIntent().getExtras() != null){
            tvLabConfigKeyACC.setText(getIntent().getExtras().getString("key_u"));
            tvLabConfigDispACC.setText(getIntent().getExtras().getString("dis_u"));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goMainActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
