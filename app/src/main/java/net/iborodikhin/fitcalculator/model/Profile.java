package net.iborodikhin.fitcalculator.model;

import net.iborodikhin.fitcalculator.Constants;

public class Profile {
    protected int age;
    protected int height;
    protected int weight;
    protected boolean isMale;
    protected boolean isFemale;
    protected int activityId;

    public int getAge() {
        return age;
    }

    public Profile setAge(int age) {
        this.age = age;

        return this;
    }

    public int getHeight() {
        return height;
    }

    public Profile setHeight(int height) {
        this.height = height;

        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Profile setWeight(int weight) {
        this.weight = weight;

        return this;
    }

    public boolean isMale() {
        return isMale;
    }

    public Profile setMale(boolean male) {
        isMale = male;

        return this;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public Profile setFemale(boolean female) {
        isFemale = female;

        return this;
    }

    public int getActivityId() {
        return activityId;
    }

    public Profile setActivityId(int activityId) {
        this.activityId = activityId;

        return this;
    }

    public long getBaseCalories() {
        int diff = -161;

        if (isMale()) {
            diff = 5;
        }

        return Math.round(10 * weight + 6.25 * height - 5 * age + diff);
    }

    public long getCalories() {
        double[] multiplier = {1.2, 1.375, 1.4625, 1.550, 1.6375, 1.725, 1.9};

        return Math.round(getBaseCalories() * multiplier[activityId]);
    }

    public long getProteinCalculated() {
        return Math.round(Constants.PROTEINS_PER_KG * weight);
    }

    public long getFatCalculated() {
        if (isMale) {
            return Math.round(Constants.FATS_PER_KG_MALE * weight);
        }

        return Math.round(Constants.FATS_PER_KG_FEMALE * weight);
    }

    public long getCarbsCalculated() {
        return Math.round((getCalories() - (getProteinCalculated() * 4 + getFatCalculated() * 9)) / 4.0);
    }

    public long getCaloriesCalculated() {
        return Math.round(4 * getProteinCalculated() + 9 * getFatCalculated() + 4 * getCarbsCalculated());
    }
}
