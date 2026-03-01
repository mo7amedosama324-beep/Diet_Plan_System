public class CalorieCalculator {
    Person p;
    double bmr;
    double tdee;
    double targetCalories;

    public CalorieCalculator(Person p) {
        this.p = p;
        calculateBMR();
        calculateTDEE();
    }

    public void calculateBMR() {
        if (p.gender.equalsIgnoreCase("male")) {
            bmr = 10 * p.weight + 6.25 * p.height - 5 * p.age + 5;
        } else {
            bmr = 10 * p.weight + 6.25 * p.height - 5 * p.age - 161;
        }
    }

    public void calculateTDEE() {
        double[] activityMultipliers = { 1.2, 1.375, 1.55, 1.725, 1.9 };
        if (p.activityLevel >= 1 && p.activityLevel <= 5) {
            tdee = bmr * activityMultipliers[p.activityLevel - 1];
        } else {
            tdee = bmr * 1.2;
        }
    }

    public void setGoal(String goal) {
        if (goal.equalsIgnoreCase("cut")) {
            targetCalories = tdee - 500;
        } else if (goal.equalsIgnoreCase("maintain weight")) {
            targetCalories = tdee;
        } else if (goal.equalsIgnoreCase("bulk")) {
            targetCalories = tdee + 500;
        }
    }

    public double getBMR() {
        return bmr;
    }

    public double getTDEE() {
        return tdee;
    }

    public double getTargetCalories() {
        return targetCalories;
    }
}
