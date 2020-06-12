import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.json.*;

@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Float counter = (Float) getApplication().getAttribute("counter");
        if (counter == null) counter = 0.0f;
        Float sum = getPersistentSum();
        String body = getBody(request);

        System.out.println(body);

        String[] params = body.split(",");
        String event = params[0];

        if (event.equals("leave")) {
            String priceString = params[4];
            String timeString = params[3];
            int id = Integer.parseInt(params[1]);
            long ankunft = Long.parseLong(params[2]);
            long dauer = Long.parseLong(params[3]);
            int platz = Integer.parseInt(params[7]);

            ArrayList<Car> autos = (ArrayList<Car>) getApplication().getAttribute("autos");
            if (autos == null) {
                getApplication().setAttribute("autos", new ArrayList<Car>());
                autos = (ArrayList<Car>) getApplication().getAttribute("autos");
            }

            Car auto = new Car(id, ankunft, dauer, platz);

            autos.add(auto);

            counter++;
            getApplication().setAttribute("counter", counter);

            Float timeAvg = getPersistentAvgTime(Float.parseFloat(timeString));

            float price = Integer.parseInt(priceString) / 100.0f;
            sum += price;
            // store sum persistently in ServletContext
            getApplication().setAttribute("sum", sum);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(sum);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requestParamString = request.getQueryString().split("=");
        String command = requestParamString[0];
        String param = requestParamString[1];
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        switch (request.getParameter("cmd")) {
            case "Summe":
                Float sum = getPersistentSum();
                out.println(sum);
                System.out.println("sum = " + sum);
                break;
            case "avg":
                Float avg = getPersistentAvg();
                out.println(avg);
                System.out.println("avg = " + avg);
                break;
            case "avg_time":
                Float avg_time = getPersistentAvgTime(null);
                out.println(avg_time);
                System.out.println("avg_time = " + avg_time);
                break;

            case "config":
                int Max = 10;
                out.println(Max + ",5,23,100,10");
                break;

            case "cars":
                StringBuilder carBuilder = new StringBuilder();
                for (Car car : cars()) {
                    if (carBuilder.length() > 1) carBuilder.append(",");
                    carBuilder.append(car.id);
                }
                out.println(carBuilder.toString());
                break;

            case "chart":
                /*
                String output= "{\n" + " \"data\": [\n";
                ArrayList<Car> autos = cars();
                String autoid = "{ \n \"x\": [ \n" ;
                String parkdauer = " \n \"y\": [\n";
                int counter = 0;
                for(Car e: autos){
                    if(counter+1 == autos.size()){
                        autoid+= e.id +"\n";
                        parkdauer += e.dauer +"\n";
                        break;
                    }
                    autoid += e.id +",\n";
                    parkdauer += e.dauer +",\n";
                    counter ++;
                }

                out.println(output + autoid +"],\n" + parkdauer + "],\n" + "\"type\": \"bar\"\n}\n]\n}" );
                */
                //Unser Versuch, die Vorgehensweise vom Video zu implementieren
                System.out.println("in chart");
                JsonObject root = Json.createObjectBuilder()
                        .add("data", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("x", Car.asNrArray(cars()))
                                        .add("y", Car.asDurationArray(cars()))
                                        .add("type", "bar")
                                        .add("name", "Duration")
                                )
                                .add(Json.createObjectBuilder()
                                        .add("x", Car.asNrArray(cars()))
                                        .add("y", Car.asBeginArray(cars()))
                                        .add("type", "bar")
                                        .add("name", "Begin")
                                )
                                .add(Json.createObjectBuilder()
                                        .add("x", Car.asNrArray(cars()))
                                        .add("y", Car.asEndArray(cars()))
                                        .add("type", "bar")
                                        .add("name", "End")
                                )
                        ).build();
                out.println(root.toString());
                System.out.println(root.toString());
                break;

            default:
                System.out.println("Invalid Command: " + request.getQueryString());
        }
    }

    private ServletContext getApplication() {
        return getServletConfig().getServletContext();
    }

    private Float getPersistentSum() {
        Float sum;
        ServletContext application = getApplication();
        sum = (Float) application.getAttribute("sum");
        if (sum == null) sum = 0.0f;
        return sum;
    }

    private Float getPersistentAvg() {
        Float counter, sum;
        ServletContext application = getApplication();
        counter = (Float) application.getAttribute("counter");
        sum = (Float) application.getAttribute("sum");
        if (counter == null) return 0.0f;
        return sum / counter;
    }

    private Float getPersistentAvgTime(Float addTime) {
        ServletContext application = getApplication();
        Float timeSum = (Float) application.getAttribute("timeSum");
        Float counter = (Float) application.getAttribute("counter");
        if (timeSum == null) timeSum = 0.0f;
        if (counter == null) return 0.0f;

        if (addTime != null) {
            application.setAttribute("timeSum", timeSum + addTime);
        }

        return (Float) application.getAttribute("timeSum") / counter;
    }


    private static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return stringBuilder.toString();
    }

    private ArrayList<Car> cars() {
        ArrayList<Car> zw = (ArrayList<Car>) getApplication().getAttribute("autos");
        if (zw == null) {
            zw = new ArrayList<Car>();
            getApplication().setAttribute("autos", zw);
        }
        return zw;
    }

}