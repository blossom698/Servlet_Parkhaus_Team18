import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.json.*;

@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Parkhaus parkhaus = new Parkhaus(10);
        getApplication().setAttribute("parkhaus", parkhaus);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = getBody(request);

        System.out.println(body);

        String[] params = body.split(",");
        String event = params[0];

        Parkhaus p = (Parkhaus) getApplication().getAttribute("parkhaus");

        switch (event) {
            case "change_Max":
                p.changeSize(Integer.parseInt(params[2]));
                break;

            case "enter":
                p.einparken(new Car(Integer.parseInt(params[1]), Long.parseLong(params[2]),  Integer.parseInt(params[7])));
                break;

            case "leave":
                p.verlassen(Integer.parseInt(params[1]), Double.parseDouble(params[4])/100.0, Long.parseLong(params[3]));
/*
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println(sum);
 */
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requestParamString = request.getQueryString().split("=");
        String command = requestParamString[0];
        String param = requestParamString[1];
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        Parkhaus parkhaus = (Parkhaus) getApplication().getAttribute("parkhaus");

        switch (request.getParameter("cmd")) {
            case "Summe":
                Double sum = parkhaus.toStream().mapToDouble(x->x.betrag).sum();
                out.println(sum);
                System.out.println("sum = " + sum);
                break;
            case "avg":
                Double avg = parkhaus.toStream().mapToDouble(x->x.betrag).average().orElse(0.0);
                out.println(avg);
                System.out.println("avg = " + avg);
                break;
            case "avg_time":
                Double avg_time = parkhaus.toStream().mapToDouble(x->x.dauer).average().orElse(0.0);
                out.println(avg_time);
                System.out.println("avg_time = " + avg_time);
                break;

            case "config":
                int Max = 10;
                out.println(Max + ",5,23,100,10");
                break;

            case "cars":

                out.println(parkhaus.asIDString());
                break;

            case "chart":

                JsonObject root = Json.createObjectBuilder()
                        .add("data", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("x", parkhaus.asNrArray())
                                        .add("y", parkhaus.asDurationArray())
                                        .add("type", "bar")
                                        .add("name", "Duration")
                                )
                                .add(Json.createObjectBuilder()
                                        .add("x", parkhaus.asNrArray())
                                        .add("y", parkhaus.asBeginArray())
                                        .add("type", "bar")
                                        .add("name", "Begin")
                                )
                                .add(Json.createObjectBuilder()
                                        .add("x", parkhaus.asNrArray())
                                        .add("y", parkhaus.asEndArray())
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
}