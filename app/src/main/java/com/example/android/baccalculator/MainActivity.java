package com.example.android.baccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int userWeightInt = 0;
    public String genderString;
    EditText userWeightEditText;
    Switch genderSwitch;
    SeekBar alcoholSeekBar;
    TextView percentAlcoholTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets the title and the icon in the bar
        setTitle("BAC Calculator");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        userWeightEditText = findViewById(R.id.weightEditText);
        genderSwitch = findViewById(R.id.genderSwitch);




        //when user enters weight and gender, and then clicked saved.
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userWeightEditText.getText().toString().equals("")){
                    userWeightEditText.setError("Enter your weight here.");
                }else{
                    userWeightInt = Integer.parseInt(userWeightEditText.getText().toString());
                }

                if(genderSwitch.isChecked()){
                    genderString = genderSwitch.getTextOn().toString();
                }else{
                    genderString = genderSwitch.getTextOff().toString();
                }
            }
        });

        alcoholSeekBar = findViewById(R.id.alcoholSeekBar);
        percentAlcoholTextView = findViewById(R.id.percentAlcohol);

        //setting initial value to 5%
        percentAlcoholTextView.setText(getString(R.string.percentSign,String.valueOf(alcoholSeekBar.getProgress()*5+5)));

        //max minus min, divide by # of steps to get set the max of the seekbar
        alcoholSeekBar.setMax( (25 - 5) / 5 );

        //when user drags the alcohol seekbar
        alcoholSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("demo", progress + "");

               // progress = (progress / 5);
                progress = 5 + (progress * 5);
                percentAlcoholTextView.setText(getString(R.string.percentSign, String.valueOf(progress)) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        findViewById(R.id.addDrinkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((EditText) findViewById(R.id.weightEditText)).getText().toString().equals("")){
                    userWeightEditText.setError("Enter a weight in lbs");
                }else{

                }
            }
        });

    }
}
