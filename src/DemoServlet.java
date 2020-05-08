import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {

    private ServletContext getApplication(){
        return getServletConfig().getServletContext();
    }

    private Float getPersistentSum() {
        Float sum;
        ServletContext application = getApplication();
        sum = (Float) application.getAttribute("sum");
        if (sum == null) sum = 0.0f;
        return sum;
    }

    private static String getBody(HttpServletRequest request) throws IOException {
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



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("Summe") != null) {


            Float sum = getPersistentSum();
            String body = getBody(request);
            System.out.println(body);
            String[] params = body.split(",");
            String event = params[0];
            String priceString = params[5];
            if (!"_".equals(priceString)) {
                // strip € in front, parse the number behind
                float price = Float.parseFloat(priceString.split(" ")[2]);
                sum += price;
                // store sum persistently in ServletContext
                getApplication().setAttribute("sum", sum);
            }
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(sum);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println( request.getQueryString() );
        //String[] Parameter = request.getQueryString().split("=");
        String Command = request.getQueryString();
        if ("Summe".equals(Command)) {
            Float sum = getPersistentSum();
            PrintWriter out = response.getWriter();
            out.println(sum);
            System.out.println("Summe = "+sum);
        }
    }
}
