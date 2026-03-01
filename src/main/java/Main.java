import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("===== DIET PLAN SYSTEM =====\n");
        
        System.out.print("Enter age: ");
        int age = input.nextInt();
        
        System.out.print("Enter height (cm): ");
        double height = input.nextDouble();
        
        System.out.print("Enter weight (kg): ");
        double weight = input.nextDouble();
        
        System.out.print("Enter gender (male/female): ");
        String gender = input.next();
        
        System.out.print("Enter activity level (1-5): ");
        int activityLevel = input.nextInt();
        
        Person person = new Person(age, height, weight, gender, activityLevel);
        
        BodyAnalyzer analyzer = new BodyAnalyzer(person);
        analyzer.calculateBMI();
        analyzer.getBodyStatus();
        
        SelectGoal goalSelector = new SelectGoal(analyzer);
        goalSelector.chooseGoal();
        
        CalorieCalculator calculator = new CalorieCalculator(person);
        calculator.setGoal(goalSelector.getGoal());
        
        System.out.println("\n===== RESULTS =====");
        System.out.println("BMI: " + String.format("%.1f", analyzer.bmi));
        System.out.println("Status: " + analyzer.bodystatus);
        System.out.println("Goal: " + goalSelector.getGoal());
        System.out.println("BMR: " + (int)calculator.getBMR() + " cal/day");
        System.out.println("TDEE: " + (int)calculator.getTDEE() + " cal/day");
        
        DietPlan diet = new DietPlan(calculator.getTargetCalories(), goalSelector.getGoal());
        diet.displayDietPlan();
        
        // Exercise Plan
        System.out.println("Do you have any health issues? (yes/no): ");
        String hasIssues = input.next();
        
        String[] userIssues = new String[0];
        if (hasIssues.equalsIgnoreCase("yes")) {
            System.out.println("Select your issues (type number and press Enter, 0 to finish):");
            System.out.println("1. Knee problems");
            System.out.println("2. Back problems");
            System.out.println("3. Shoulder problems");
            System.out.println("4. Arm problems");
            System.out.println("5. Core problems");
            System.out.println("0. Finish");
            
            String[] issueNames = {"knee", "back", "shoulder", "arm", "core"};
            boolean[] selectedIssues = new boolean[5];
            
            int choice;
            do {
                System.out.print("Enter choice: ");
                choice = input.nextInt();
                if (choice >= 1 && choice <= 5) {
                    selectedIssues[choice - 1] = true;
                }
            } while (choice != 0);
            
            int count = 0;
            for (boolean selected : selectedIssues) {
                if (selected) count++;
            }
            
            userIssues = new String[count];
            int index = 0;
            for (int i = 0; i < selectedIssues.length; i++) {
                if (selectedIssues[i]) {
                    userIssues[index++] = issueNames[i];
                }
            }
        }
        
        ExternalAI exerciseAI = new ExternalAI(goalSelector.getGoal());
        String[] recommendedExercises = exerciseAI.getExercises(userIssues);
        
        System.out.println("\n========== EXERCISE PLAN ==========");
        System.out.println("Goal: " + goalSelector.getGoal());
        System.out.println("\nRecommended Exercises:");
        for (int i = 0; i < recommendedExercises.length; i++) {
            System.out.println("  " + (i+1) + ". " + recommendedExercises[i]);
        }
        System.out.println("===================================\n");
        
        input.close();
    }
}
