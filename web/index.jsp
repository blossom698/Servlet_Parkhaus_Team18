<%--
  Created by IntelliJ IDEA.
  User: Wiete
  Date: 07.05.20
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <script src="https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-9.1.3.js"></script>

  <ccm-parkhaus-9-1-3
          server_url="${pageContext.request.contextPath}/DemoServlet"
          name="Parkhaus"
          license_max="30"
          client_categories='["Eingeschraenkte","Frauen","Familie","beliebig","beliebig","beliebig","beliebig","beliebig","beliebig","beliebig","beliebig","beliebig"]'
          extra_buttons='["Summe","Durchschnittlicher Betrag","Durchschnittliche Parkzeit","Tageseinnahmen","Wocheneinnahmen","Anzahl freier Plaetze"]'
          extra_charts='["Parkdauerdiagramm","Parkplatzbesetzungsdiagramm","Fahrzeugtypendiagramm"]'
          hide_table>

  </ccm-parkhaus-9-1-3>
  </body>
</html>