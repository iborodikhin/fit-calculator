package net.iborodikhin.fitcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        Intent intent = getIntent();

        int weight = Integer.parseInt(intent.getStringExtra(Constants.MESSAGE_WEIGHT));
        int height = Integer.parseInt(intent.getStringExtra(Constants.MESSAGE_HEIGHT));
        int age = Integer.parseInt(intent.getStringExtra(Constants.MESSAGE_AGE));
        boolean is_male = intent.getBooleanExtra(Constants.MESSAGE_IS_MALE, false);
        boolean is_female = intent.getBooleanExtra(Constants.MESSAGE_IS_FEMALE, false);
        int activityId = (int) intent.getLongExtra(Constants.MESSAGE_ACTIVITY, 0);
        double baseConsumption = 0.0;
        double avgConsumption = 0.0;
        long proteins = Math.round(1.5 * weight); // 4
        long fats = 0; // 9
        long carbs = 0; // 4

        if (is_male) {
            baseConsumption = 10 * weight + 6.25 * height - 5 * age  + 5;
            fats = Math.round(80.0 * weight / 100);
        }

        if (is_female) {
            baseConsumption = 10 * weight + 6.25 * height - 5 * age - 161;
            fats = Math.round(60.8 * weight / 100);
        }

        switch (activityId) {
            case 0:
                avgConsumption = baseConsumption * 1.2;
                break;
            case 1:
                avgConsumption = baseConsumption * 1.375;
                break;
            case 2:
                avgConsumption = baseConsumption * 1.4625;
                break;

            case 3:
                avgConsumption = baseConsumption * 1.550;
                break;

            case 4:
                avgConsumption = baseConsumption * 1.6375;
                break;

            case 5:
                avgConsumption = baseConsumption * 1.725;
                break;

            case 6:
                avgConsumption = baseConsumption * 1.9;
                break;
        }

        double consumptionPerCarbs = avgConsumption - (proteins * 4 + fats * 9);
        carbs = Math.round(consumptionPerCarbs / 4.0);

        TextView baseConsumptionView = (TextView) findViewById(R.id.baseConsumption);
        TextView avgConsumptionView = (TextView) findViewById(R.id.avgConsumption);
        TextView caloriesView = (TextView) findViewById(R.id.calories);
        TextView proteinsView = (TextView) findViewById(R.id.proteins);
        TextView fatsView = (TextView) findViewById(R.id.fats);
        TextView carbsView = (TextView) findViewById(R.id.carbs);

        baseConsumptionView.setText(String.format(baseConsumptionView.getText().toString(), Math.round(baseConsumption)));
        avgConsumptionView.setText(String.format(avgConsumptionView.getText().toString(), Math.round(avgConsumption)));
        proteinsView.setText(String.format(proteinsView.getText().toString(), proteins));
        fatsView.setText(String.format(fatsView.getText().toString(), fats));
        carbsView.setText(String.format(carbsView.getText().toString(), carbs));
        caloriesView.setText(String.format(caloriesView.getText().toString(), (proteins * 4 + fats * 9 + carbs * 4)));
    }

}
