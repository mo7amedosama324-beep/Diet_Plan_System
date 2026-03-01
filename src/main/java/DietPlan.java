public class DietPlan {
    double targetCalories;
    String goal;
    double protein;
    double carbs;
    double fats;
    String[] meals;

    public DietPlan(double targetCalories, String goal) {
        this.targetCalories = targetCalories;
        this.goal = goal;
        calculateMacros();
        generateMealPlan();
    }

    private void calculateMacros() {
        if (goal.equalsIgnoreCase("cut")) {
            protein = targetCalories * 0.40 / 4;
            carbs = targetCalories * 0.35 / 4;
            fats = targetCalories * 0.25 / 9;
        } else if (goal.equalsIgnoreCase("bulk")) {
            protein = targetCalories * 0.30 / 4;
            carbs = targetCalories * 0.50 / 4;
            fats = targetCalories * 0.20 / 9;
        } else {
            protein = targetCalories * 0.30 / 4;
            carbs = targetCalories * 0.40 / 4;
            fats = targetCalories * 0.30 / 9;
        }
    }

    private void generateMealPlan() {
        meals = new String[5];
        double caloriesPerMeal = targetCalories / 5;

        if (goal.equalsIgnoreCase("cut")) {
            meals[0] = "Breakfast: Oatmeal with berries + eggs (" + (int)caloriesPerMeal + " cal)";
            meals[1] = "Snack: Greek yogurt + almonds (" + (int)caloriesPerMeal + " cal)";
            meals[2] = "Lunch: Grilled chicken + quinoa + vegetables (" + (int)caloriesPerMeal + " cal)";
            meals[3] = "Snack: Protein shake + banana (" + (int)caloriesPerMeal + " cal)";
            meals[4] = "Dinner: Baked fish + sweet potato + salad (" + (int)caloriesPerMeal + " cal)";
        } else if (goal.equalsIgnoreCase("bulk")) {
            meals[0] = "Breakfast: Pancakes + peanut butter + eggs (" + (int)caloriesPerMeal + " cal)";
            meals[1] = "Snack: Protein bar + whole milk (" + (int)caloriesPerMeal + " cal)";
            meals[2] = "Lunch: Pasta + meatballs + cheese (" + (int)caloriesPerMeal + " cal)";
            meals[3] = "Snack: Sandwich + turkey + avocado (" + (int)caloriesPerMeal + " cal)";
            meals[4] = "Dinner: Steak + rice + vegetables (" + (int)caloriesPerMeal + " cal)";
        } else {
            meals[0] = "Breakfast: Toast + eggs + avocado (" + (int)caloriesPerMeal + " cal)";
            meals[1] = "Snack: Apple + peanut butter (" + (int)caloriesPerMeal + " cal)";
            meals[2] = "Lunch: Chicken wrap + vegetables (" + (int)caloriesPerMeal + " cal)";
            meals[3] = "Snack: Trail mix + dried fruits (" + (int)caloriesPerMeal + " cal)";
            meals[4] = "Dinner: Salmon + brown rice + broccoli (" + (int)caloriesPerMeal + " cal)";
        }
    }

    public void displayDietPlan() {
        System.out.println("\n========== DIET PLAN ==========");
        System.out.println("Goal: " + goal);
        System.out.println("Target Calories: " + (int)targetCalories + " cal/day");
        System.out.println("\nMacros:");
        System.out.println("  Protein: " + (int)protein + "g");
        System.out.println("  Carbs: " + (int)carbs + "g");
        System.out.println("  Fats: " + (int)fats + "g");
        System.out.println("\nMeals:");
        for (int i = 0; i < meals.length; i++) {
            System.out.println("  " + (i+1) + ". " + meals[i]);
        }
        System.out.println("===============================\n");
    }
}
