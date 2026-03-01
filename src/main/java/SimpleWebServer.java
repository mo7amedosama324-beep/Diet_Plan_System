import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class SimpleWebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                String response = getHtmlPage();
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        });
        
        server.createContext("/calculate", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                if ("POST".equals(exchange.getRequestMethod())) {
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    String requestBody = br.lines().collect(Collectors.joining());
                    
                    String response = processRequest(requestBody);
                    
                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    os.close();
                }
            }
        });
        
        server.setExecutor(null);
        server.start();
        System.out.println("=================================");
        System.out.println("Server started on: http://localhost:8080");
        System.out.println("Open your browser and visit the URL above");
        System.out.println("=================================");
    }
    
    private static String processRequest(String json) {
        // Simple JSON parsing
        int age = extractInt(json, "age");
        double height = extractDouble(json, "height");
        double weight = extractDouble(json, "weight");
        String gender = extractString(json, "gender");
        int activityLevel = extractInt(json, "activityLevel");
        String[] healthIssues = extractArray(json, "healthIssues");
        
        Person person = new Person(age, height, weight, gender, activityLevel);
        
        BodyAnalyzer analyzer = new BodyAnalyzer(person);
        analyzer.calculateBMI();
        analyzer.getBodyStatus();
        
        SelectGoal goalSelector = new SelectGoal(analyzer);
        goalSelector.chooseGoal();
        
        CalorieCalculator calculator = new CalorieCalculator(person);
        calculator.setGoal(goalSelector.getGoal());
        
        DietPlan diet = new DietPlan(calculator.getTargetCalories(), goalSelector.getGoal());
        
        ExternalAI exerciseAI = new ExternalAI(goalSelector.getGoal());
        String[] exercises = exerciseAI.getExercises(healthIssues);
        
        // Build JSON response
        StringBuilder response = new StringBuilder("{");
        response.append("\"bmi\":").append(String.format("%.1f", analyzer.bmi)).append(",");
        response.append("\"bodyStatus\":\"").append(analyzer.bodystatus).append("\",");
        response.append("\"goal\":\"").append(goalSelector.getGoal()).append("\",");
        response.append("\"bmr\":").append((int)calculator.getBMR()).append(",");
        response.append("\"tdee\":").append((int)calculator.getTDEE()).append(",");
        response.append("\"targetCalories\":").append((int)calculator.getTargetCalories()).append(",");
        response.append("\"protein\":").append((int)diet.protein).append(",");
        response.append("\"carbs\":").append((int)diet.carbs).append(",");
        response.append("\"fats\":").append((int)diet.fats).append(",");
        response.append("\"meals\":[");
        for (int i = 0; i < diet.meals.length; i++) {
            response.append("\"").append(diet.meals[i].replace("\"", "\\\"")).append("\"");
            if (i < diet.meals.length - 1) response.append(",");
        }
        response.append("],");
        response.append("\"exercises\":[");
        for (int i = 0; i < exercises.length; i++) {
            response.append("\"").append(exercises[i]).append("\"");
            if (i < exercises.length - 1) response.append(",");
        }
        response.append("]}");
        
        return response.toString();
    }
    
    private static int extractInt(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return Integer.parseInt(json.substring(start, end).trim());
    }
    
    private static double extractDouble(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return Double.parseDouble(json.substring(start, end).trim());
    }
    
    private static String extractString(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
    
    private static String[] extractArray(String json, String key) {
        String pattern = "\"" + key + "\":[";
        int start = json.indexOf(pattern);
        if (start == -1) return new String[0];
        start += pattern.length();
        int end = json.indexOf("]", start);
        String arrayContent = json.substring(start, end);
        if (arrayContent.trim().isEmpty()) return new String[0];
        String[] items = arrayContent.split(",");
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].trim().replace("\"", "");
        }
        return items;
    }
    
    private static String getHtmlPage() {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return getEmbeddedHtml();
        }
    }
    
    private static String getEmbeddedHtml() {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Diet Plan System</title>" +
            "<style>*{margin:0;padding:0;box-sizing:border-box}body{font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh;padding:20px}.container{max-width:800px;margin:0 auto;background:white;border-radius:20px;padding:40px;box-shadow:0 20px 60px rgba(0,0,0,0.3)}h1{text-align:center;color:#667eea;margin-bottom:30px;font-size:2.5em}.form-group{margin-bottom:20px}label{display:block;margin-bottom:8px;color:#333;font-weight:600}input,select{width:100%;padding:12px;border:2px solid #e0e0e0;border-radius:8px;font-size:16px}button{width:100%;padding:15px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);color:white;border:none;border-radius:8px;font-size:18px;font-weight:600;cursor:pointer}#result{margin-top:30px;display:none}.result-section{background:#f8f9fa;padding:20px;border-radius:10px;margin-bottom:20px}.stat{display:flex;justify-content:space-between;padding:10px 0;border-bottom:1px solid #e0e0e0}.meals-list,.exercises-list{list-style:none;padding:0}.meals-list li,.exercises-list li{padding:10px;background:white;margin:5px 0;border-radius:5px}</style></head>" +
            "<body><div class='container'><h1>Diet Plan System</h1><form id='dietForm'>" +
            "<div class='form-group'><label>Age:</label><input type='number' id='age' required min='10' max='100'></div>" +
            "<div class='form-group'><label>Height (cm):</label><input type='number' id='height' required min='100' max='250'></div>" +
            "<div class='form-group'><label>Weight (kg):</label><input type='number' id='weight' required min='30' max='200'></div>" +
            "<div class='form-group'><label>Gender:</label><select id='gender' required><option value=''>Select...</option><option value='male'>Male</option><option value='female'>Female</option></select></div>" +
            "<div class='form-group'><label>Activity Level:</label><select id='activityLevel' required><option value=''>Select...</option><option value='1'>1 - Sedentary</option><option value='2'>2 - Light Activity</option><option value='3'>3 - Moderate Activity</option><option value='4'>4 - Very Active</option><option value='5'>5 - Extremely Active</option></select></div>" +
            "<button type='submit'>Calculate Plan</button></form>" +
            "<div id='result'><div class='result-section'><h2>Body Analysis</h2><div class='stat'><span>BMI:</span><span id='bmi'></span></div><div class='stat'><span>Status:</span><span id='status'></span></div><div class='stat'><span>Goal:</span><span id='goal'></span></div></div>" +
            "<div class='result-section'><h2>Diet Plan</h2><div class='stat'><span>Calories:</span><span id='calories'></span></div><div class='stat'><span>Protein:</span><span id='protein'></span></div><h3>Meals:</h3><ul class='meals-list' id='meals'></ul></div>" +
            "<div class='result-section'><h2>Exercises</h2><ul class='exercises-list' id='exercises'></ul></div></div></div>" +
            "<script>document.getElementById('dietForm').addEventListener('submit',async(e)=>{e.preventDefault();const data={age:parseInt(document.getElementById('age').value),height:parseFloat(document.getElementById('height').value),weight:parseFloat(document.getElementById('weight').value),gender:document.getElementById('gender').value,activityLevel:parseInt(document.getElementById('activityLevel').value),healthIssues:[]};const response=await fetch('/calculate',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(data)});const result=await response.json();document.getElementById('bmi').textContent=result.bmi;document.getElementById('status').textContent=result.bodyStatus;document.getElementById('goal').textContent=result.goal;document.getElementById('calories').textContent=result.targetCalories+' cal/day';document.getElementById('protein').textContent=result.protein+'g';const mealsList=document.getElementById('meals');mealsList.innerHTML='';result.meals.forEach(meal=>{const li=document.createElement('li');li.textContent=meal;mealsList.appendChild(li)});const exercisesList=document.getElementById('exercises');exercisesList.innerHTML='';result.exercises.forEach(ex=>{const li=document.createElement('li');li.textContent=ex;exercisesList.appendChild(li)});document.getElementById('result').style.display='block'});</script></body></html>";
    }
}
