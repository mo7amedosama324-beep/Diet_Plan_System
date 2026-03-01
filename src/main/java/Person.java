public class Person {
    int age;
    double height;
    double weight;
    String gender;
    int activityLevel;

    public Person(int age, double height, double weight, String gender, int activityLevel) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activityLevel = activityLevel;
    }

    public double getHeight() {
        return height / 100.0;
    }
}

