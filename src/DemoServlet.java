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

        // Initialisieren des Parkhauses
        Parkhaus parkhaus = new Parkhaus(10);
        getApplication().setAttribute("parkhaus", parkhaus);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = getBody(request);

        System.out.println(body);

        String[] params = body.split(",");
        String event = params[0];

        Parkhaus p = (Parkhaus) getApplication().getAttribute("parkhaus");

        //Abhängig vom Kommando wird verschiedenes durchgeführt.
        switch (event) {
            case "change_Max":
                p.changeSize(Integer.parseInt(params[2]));
                break;

            case "enter":
                p.einparken(new Car(Integer.parseInt(params[1]), Long.parseLong(params[2]),  Integer.parseInt(params[7]),params[8]));
                break;

            case "leave":
                p.verlassen(Integer.parseInt(params[1]), Double.parseDouble(params[4])/100.0, Long.parseLong(params[3]));
                break;

            case "occupied":
                /* Da die Nachrichten in der Form "occupied, car(25)" ankommen, und wir in params[1]
                    nur noch "Car(25)" stehen haben und die ID isolieren wollen, so spliten wir erst
                    bei "(" und dann bei ")" wobei diese doppelten backslashes für ein Backslash stehen.
                    Ergebnis beim Beispiel: 25 im parseInt.
                */
                int id = Integer.parseInt(params[1].split("\\(")[1].split("\\)")[0]);
                p.verlassen(id, 0,0);
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
        IView tmp;
        switch (request.getParameter("cmd")) {
            case "Summe":
                Double sum = parkhaus.eintraegeToStream().mapToDouble(x->x.betrag).sum();
                out.println(sum);
                System.out.println("sum = " + sum);
                break;

            case "Durchschnittlicher Betrag":
                //Runden auf zwei Nachkommastellen, wegen Euro bzw. Centanzahl (Erst auf Cent bringen, dann den Rest abschneiden, dann auf Euro wieder wechseln).
                Double avg = ((int)(parkhaus.eintraegeToStream().mapToDouble(x->x.betrag).average().orElse(0.0)*100))/100.0;
                out.println(avg);
                System.out.println("Durchschnittlicher Betrag = " + avg);
                break;

            case "Durchschnittliche Parkzeit":
                Double avg_time = ((int)(parkhaus.eintraegeToStream().mapToDouble(x->x.dauer).average().orElse(0.0)*100))/100.0;
                out.println(avg_time);
                System.out.println("Durchschnittliche Parkzeit = " + avg_time);
                break;

            case "Tageseinnahmen":
                tmp = (IView) getApplication().getAttribute("TageseinnahmenView");
                if(tmp==null){
                    tmp = new TageseinnahmenView(parkhaus);
                    getApplication().setAttribute("TageseinnahmenView", tmp);
                    parkhaus.anmelden(tmp);
                    parkhaus.benachrichtigeviews();
                }
                Double tageseinnahmen = tmp.getValue();
                out.println(tageseinnahmen);
                System.out.println("Tageseinnahmen = " + tageseinnahmen);
                break;

            case "Wocheneinnahmen":
                tmp = (IView) getApplication().getAttribute("WocheneinnahmenView");
                if(tmp==null){
                    tmp = new WocheneinnahmenView(parkhaus);
                    getApplication().setAttribute("WocheneinnahmenView", tmp);
                    parkhaus.anmelden(tmp);
                    parkhaus.benachrichtigeviews();
                }
                Double wocheneinnahmen = tmp.getValue();
                out.println(wocheneinnahmen);
                System.out.println("Wocheneinnahmen = " + wocheneinnahmen);
                break;

            case "Anzahl freier Plaetze":
                tmp = (IView) getApplication().getAttribute("FreieplaetzeView");
                if(tmp==null){
                    tmp = new FreieplaetzeView(parkhaus);
                    getApplication().setAttribute("FreieplaetzeView", tmp);
                    parkhaus.anmelden(tmp);
                    parkhaus.benachrichtigeviews();
                }
                int frei = (int) tmp.getValue();
                out.println(frei);
                System.out.println("FreieplaetzeView = " + frei);
                break;

            case "config":
                int Max = 10;
                out.println(Max + ",5,23,100,10");
                break;

            case "cars":
                out.println(parkhaus.asIDString());
                break;

            case "Parkdauerdiagramm":
                JsonObject root = Json.createObjectBuilder()
                        .add("data", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("x", parkhaus.asNrArray())
                                        .add("y", parkhaus.asDurationArray())
                                        .add("type", "bar")
                                        .add("name", "Duration")
                                )
                        ).build();
                out.println(root.toString());
                System.out.println(root.toString());
                break;


            case "Parkplatzbesetzungsdiagramm":
                JsonObject root2 = Json.createObjectBuilder()
                        .add("data", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("values", parkhaus.asCategories())
                                        .add("labels", Json.createArrayBuilder().add("beliebig").add("Familie").add("Frauen").add("Eingeschraenkte"))
                                        .add("type", "pie")
                                        .add("name", "Duration")
                                )

                        ).build();
                out.println(root2.toString());
                System.out.println(root2.toString());
                break;

            case "Fahrzeugtypendiagramm":
                JsonObject root3 = Json.createObjectBuilder()
                        .add("data", Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("values", parkhaus.asTyps())
                                        .add("labels", Json.createArrayBuilder().add("pkw").add("pickup").add("suv").add("zweirad").add("trike").add("quad"))
                                        .add("type", "pie")
                                        .add("name", "Typen")
                                )

                        ).build();
                out.println(root3.toString());
                System.out.println(root3.toString());
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