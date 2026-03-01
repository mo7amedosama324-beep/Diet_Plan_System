# Diet Plan System 🥗

A comprehensive Java application for calculating calories, BMI, and generating personalized diet and exercise plans.

## Features ✨

- **BMI Calculator**: Calculate Body Mass Index and determine body status
- **Calorie Calculator**: Calculate BMR (Basal Metabolic Rate) and TDEE (Total Daily Energy Expenditure)
- **Goal Selection**: Choose between bulking, cutting, or maintaining weight
- **Diet Plan Generator**: Get personalized meal plans with macros breakdown
- **Exercise Recommendations**: Receive exercise plans based on your goals
- **Web Interface**: Access the system through a simple web server

## Technologies Used 🛠️

- Java
- Maven
- HTML/CSS (for web interface)

## Project Structure 📁

```
project/
├── src/main/java/
│   ├── Main.java              # Console application entry point
│   ├── Person.java            # Person data model
│   ├── BodyAnalyzer.java      # BMI and body status calculations
│   ├── CalorieCalculator.java # BMR and TDEE calculations
│   ├── SelectGoal.java        # Goal selection logic
│   ├── DietPlan.java          # Diet plan generation
│   ├── ExternalAI.java        # Exercise plan generation
│   └── SimpleWebServer.java   # Web server implementation
├── src/main/resources/
│   ├── templates/
│   │   └── index.html         # Web interface
│   └── application.properties
└── pom.xml
```

## How to Run 🚀

### Console Version
```bash
cd target/classes
java Main
```

### Web Version
```bash
cd target/classes
java SimpleWebServer
```
Then open your browser and navigate to `http://localhost:8080`

## How to Build 🔨

If you have Maven installed:
```bash
mvn clean compile
```

## Usage Example 📝

1. Run the program
2. Enter your age, height, weight, gender
3. Enter your activity level (1-5)
4. The system will calculate your BMI and TDEE
5. Choose your goal (bulk/cut/maintain)
6. Get your personalized diet plan with meals and macros
7. Get exercise recommendations

## Contributors 👥

FCAI Project Team

## License 📄

This project is open source and available for educational purposes.
