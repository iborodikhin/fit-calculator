package net.iborodikhin.fitcalculator.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import net.iborodikhin.fitcalculator.model.Profile;

public class ProfileAdapter {
    protected Activity activity;
    protected Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public ProfileAdapter(Activity activity, Profile profile) {
        this.activity = activity;
        this.profile = profile;
    }

    public ProfileAdapter map(int viewId, Object value) {
        TextView view = activity.findViewById(viewId);
        view.setText(String.format(view.getText().toString(), value));

        return this;
    }
}
