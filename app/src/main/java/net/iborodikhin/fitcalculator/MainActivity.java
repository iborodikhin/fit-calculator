package net.iborodikhin.fitcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.iborodikhin.fitcalculator.database.ProfileDbHelper;
import net.iborodikhin.fitcalculator.model.Profile;
import net.iborodikhin.fitcalculator.viewmodel.ProfileAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        try {
            Profile profile = readProfile();
            ProfileAdapter adapter = new ProfileAdapter(this, profile);

            adapter
                    .map(R.id.baseConsumption, adapter.getProfile().getBaseCalories())
                    .map(R.id.avgConsumption, adapter.getProfile().getCalories())
                    .map(R.id.calories, adapter.getProfile().getCaloriesCalculated())
                    .map(R.id.proteins, adapter.getProfile().getProteinCalculated())
                    .map(R.id.fats, adapter.getProfile().getFatCalculated())
                    .map(R.id.carbs, adapter.getProfile().getCarbsCalculated());
        } catch (Exception e) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
    }

    protected Profile readProfile() throws Exception {
        ProfileDbHelper helper = new ProfileDbHelper(getBaseContext());

        return helper.read();
    }
}
