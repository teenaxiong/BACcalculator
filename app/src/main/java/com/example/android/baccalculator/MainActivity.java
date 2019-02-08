package com.example.android.baccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int userWeightInt = 0;
    public double genderValue;
    public int ozOfAlcoholConsumed = 0;
    public int drinkSizeInt = 0;
    public double alcoholPercentage = 0;
    double bacResultDouble = 0.00;

    EditText userWeightEditText;
    Switch genderSwitch;
    SeekBar alcoholSeekBar;
    TextView percentAlcoholTextView;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView bacResultTextView;
    ProgressBar progressBar;
    TextView statusResult;
    String genderString = "";

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
        radioGroup = findViewById(R.id.drinkSizeGroup);
        alcoholSeekBar = findViewById(R.id.alcoholSeekBar);
        percentAlcoholTextView = findViewById(R.id.percentAlcohol);
        progressBar = findViewById(R.id.progressBar);
        bacResultTextView = findViewById(R.id.BACresult);

        //setting initial value to 5%
        percentAlcoholTextView.setText(getString(R.string.percentSign,String.valueOf(alcoholSeekBar.getProgress()*5+5)));

        //max minus min, divide by # of steps to get set the max of the seekbar
        alcoholSeekBar.setMax( (25 - 5) / 5 );


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
                    genderValue = 0.68;
                }else {
                    genderString = genderSwitch.getTextOff().toString();
                    genderValue = 0.55;
                }


            }
        });


        //when user drags the alcohol seekbar
        alcoholSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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

        //when user clicks on the Add Drink button
        findViewById(R.id.addDrinkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userWeightInt == 0){
                    userWeightEditText.setError("Enter a weight in lbs");
                }else{
                    int checkRadioGroupInt = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(checkRadioGroupInt);
                    alcoholPercentage = (alcoholSeekBar.getProgress()*5 + 5) / 100.0;


                    switch (radioButton.getText().toString()){
                        case "1 oz":
                            bacResultDouble = ((1 *alcoholPercentage)*6.24 / (userWeightInt * genderValue)) + bacResultDouble;
                            bacResultDouble = Math.round(bacResultDouble *100.0)/100.0;
                            bacResultTextView.setText(""+bacResultDouble);
                            break;
                        case "5 oz":
                            bacResultDouble = ((5 *alcoholPercentage)*6.24 / (userWeightInt * genderValue)+ bacResultDouble);
                            bacResultDouble = Math.round(bacResultDouble *100.0)/100.0;
                            bacResultTextView.setText(""+bacResultDouble);
                            break;
                        case "12 oz":
                            bacResultDouble = ((12 *alcoholPercentage)*6.24 / (userWeightInt * genderValue)+ bacResultDouble);
                            bacResultDouble = Math.round(bacResultDouble *100.0)/100.0;
                            bacResultTextView.setText(""+bacResultDouble);
                            break;
                    }


                    int bacResultInt = (int)(bacResultDouble * 100);
                    progressBar.setProgress(bacResultInt);

                    statusResult = findViewById(R.id.statusResult);
                    if(bacResultDouble <=0.08){
                        statusResult.setText("You're safe.");
                    }else if(bacResultDouble >0.08 && bacResultDouble<0.20){
                        statusResult.setText("Be careful.");
                    }else if(bacResultDouble >=0.20 && bacResultDouble<0.25){
                        statusResult.setText("Over the limit!!");
                    }else  if(bacResultDouble>=0.25){
                        findViewById(R.id.addDrinkButton).setEnabled(false);
                        findViewById(R.id.saveButton).setEnabled(false);
                        Toast toast = Toast.makeText(getApplicationContext(), "No more drinks for you!!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeightEditText.setText(null);
                userWeightInt = 0;
                genderSwitch.setChecked(false);
                genderValue = 0;
                radioGroup.check(R.id.oneOunceRadioButton);
                alcoholSeekBar.setProgress(0);
                percentAlcoholTextView.setText(getString(R.string.percentSign,String.valueOf(alcoholSeekBar.getProgress()*5+5)));
                progressBar.setProgress(0);
                bacResultTextView.setText(null);
                bacResultDouble = 0.00;
                findViewById(R.id.saveButton).setEnabled(true);
                findViewById(R.id.addDrinkButton).setEnabled(true);
            }
        });

    }
}
