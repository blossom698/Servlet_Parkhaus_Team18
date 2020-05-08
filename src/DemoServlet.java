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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        System.out.println( request.getQueryString() );
        //String[] Parameter = request.getQueryString().split("=");
        String Command = request.getQueryString();
        if ("Summe".equals(Command)) {
            Float sum = getPersistentSum();
            PrintWriter out = response.getWriter();
            out.println(sum);
            System.out.println("Summe = "+sum);
        }*/
        String[] requestParamString = request.getQueryString().split("=");
        String command = requestParamString[0];
        String param = requestParamString[1];
        if ( "cmd".equals( command ) && "sum".equals( param ) ) {
            Float sum = getPersistentSum();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(sum);

            System.out.println("sum = " + sum);
        }else {
            System.out.println( "Invalid Command: " + request.getQueryString() );
        }
    }
    private ServletContext getApplication(){
        return getServletConfig().getServletContext();
    }

    private Float getPersistentSum(){
        Float sum;
        ServletContext application = getApplication();
        sum = (Float)application.getAttribute("sum");
        if ( sum == null ) sum = 0.0f;
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

}
