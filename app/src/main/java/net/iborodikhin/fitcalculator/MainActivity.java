package net.iborodikhin.fitcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) this.findViewById(R.id.calculate);
        final TextView weight = (TextView) this.findViewById(R.id.weight);
        final TextView height = (TextView) this.findViewById(R.id.height);
        final TextView age = (TextView) this.findViewById(R.id.age);
        final RadioButton gender_is_male = (RadioButton) this.findViewById(R.id.gender_is_male);
        final RadioButton gender_is_female = (RadioButton) this.findViewById(R.id.gender_is_female);
        final Spinner activity = (Spinner) this.findViewById(R.id.activity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weightText = weight.getText().toString();
                String heightText = height.getText().toString();
                String ageText = age.getText().toString();
                boolean is_male = gender_is_male.isChecked();
                boolean is_female = gender_is_female.isChecked();
                long activityId = activity.getSelectedItemId();

                if (heightText.length() != 3) {
                    showAlert("Укажите рост от 100 до 999 см");

                    return;
                }

                if (weightText.length() < 2 || weightText.length() > 3) {
                    showAlert("Укажите вес от 10 до 999 кг");

                    return;
                }

                if (ageText.length() < 2 || ageText.length() > 2) {
                    showAlert("Укажите возраст от 10 до 99 лет");

                    return;
                }

                Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
                intent.putExtra(Constants.MESSAGE_WEIGHT, weightText);
                intent.putExtra(Constants.MESSAGE_HEIGHT, heightText);
                intent.putExtra(Constants.MESSAGE_AGE, ageText);
                intent.putExtra(Constants.MESSAGE_IS_MALE, is_male);
                intent.putExtra(Constants.MESSAGE_IS_FEMALE, is_female);
                intent.putExtra(Constants.MESSAGE_ACTIVITY, activityId);

                startActivity(intent);
            }
        });
    }

    protected void showAlert(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
