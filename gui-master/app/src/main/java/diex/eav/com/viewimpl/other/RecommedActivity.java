package diex.eav.com.viewimpl.other;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import diex.eav.com.R;
import diex.eav.com.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecommedActivity extends BaseActivity {

    Button buttonCalculate;
    EditText height;
    EditText weight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bmi_chart)
    ImageView ivTopic;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calculate);

// Get the references to the widgets
        final EditText e1 = (EditText) findViewById(R.id.et1);
        final EditText e2 = (EditText) findViewById(R.id.et2);
        final TextView tv4 = (TextView) findViewById(R.id.tv4);

        findViewById(R.id.ib1).setOnClickListener(new View.OnClickListener() {

            // Logic for validation, input can't be empty
            @Override
            public void onClick(View v) {

                String str1 = e1.getText().toString();
                String str2 = e2.getText().toString();

                if(TextUtils.isEmpty(str1)){
                    e1.setError("Please enter your weight");
                    e1.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(str2)){
                    e2.setError("Please enter your height");
                    e2.requestFocus();
                    return;
                }

//Get the user values from the widget reference
                float weight = Float.parseFloat(str1);
                float height = Float.parseFloat(str2)/100;

//Calculate BMI value
                float bmiValue = calculateBMI(weight, height);

//Define the meaning of the bmi value
                String bmiInterpretation = interpretBMI(bmiValue);

                tv4.setText(String.valueOf(bmiValue + "-" + bmiInterpretation));

            }
        });

    }


    private void initView() {

        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backThActivity();
            }
        });
        toolbar.setTitle("Body Mass Index");


    }
    public void calculate(){

        buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        weight = (EditText) findViewById(R.id.BMIweight);
        weight = (EditText) findViewById(R.id.BMIheight);
    }
    @Override
    public String setActName() {
        return null;
    }



        //Calculate BMI
        private float calculateBMI (float weight, float height) {
            return (float) (weight / (height * height));
        }

        // Interpret what BMI means
        private String interpretBMI(float bmiValue) {

            if (bmiValue < 16) {
                return "Severely underweight";
            } else if (bmiValue < 18.5) {

                return "Underweight";
            } else if (bmiValue < 25) {

                return "Normal";
            } else if (bmiValue < 30) {

                return "Overweight";
            } else {
                return "Obese";
            }
        }
}

