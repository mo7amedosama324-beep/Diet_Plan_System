public class BodyAnalyzer {
    Person p;
    double bmi;
    String bodystatus;

    public BodyAnalyzer(Person p) {
        this.p = p;
    }

    public void calculateBMI() {
        double heightInMeters = p.getHeight();
        bmi = p.weight / (heightInMeters * heightInMeters);
    }

    public void getBodyStatus() {
        if (bmi < 18.5) {
            bodystatus = "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            bodystatus = "Normal weight";
        } else if (bmi >= 25 && bmi < 30) {
            bodystatus = "Overweight";
        } else {
            bodystatus = "Obese";
        }
    }
}
