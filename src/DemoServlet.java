import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Float counter = (Float) getApplication().getAttribute("counter");
        if (counter==null) counter = 0.0f;
        Float sum = getPersistentSum();
        String body = getBody(request);

        System.out.println(body);

        String[] params = body.split(",");
        String event = params[0];

        if (event.equals("leave")) {
            String priceString = params[4];
            String timeString = params[3];
            Float timeAvg = getPersistentTimeAvg(Float.parseFloat(timeString));
            counter++;
            getApplication().setAttribute("counter", counter);

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
        if ("cmd".equals(command) && "Summe".equals(param)) {
            Float sum = getPersistentSum();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(sum);

            System.out.println("sum = " + sum);
        } else if("cmd".equals(command)&& "avg".equals(param)) {
            Float avg = getPersistentAvg();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(avg);
            System.out.println("avg = "+avg);

        } else {
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


    private Float getPersistentAvg(){
        Float counter,sum;
        ServletContext application = getApplication();
        counter  = (Float) application.getAttribute("counter");
        sum = (Float) application.getAttribute("sum");
        if ( counter == null ) return  0.0f;
        return sum / counter ;
    }

    private Float getPersistentTimeAvg(Float addTime) {
        ServletContext application = getApplication();
        Float timeSum = (Float) application.getAttribute("timeSum");
        Float counter = (Float) application.getAttribute("counter");
        if (timeSum == null) timeSum = 0.0f;
        if (counter == null) return  0.0f;
        if (addTime != null) {
            application.setAttribute("timeSum", timeSum+addTime);
        }
        return timeSum/counter;
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
