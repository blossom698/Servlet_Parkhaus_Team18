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

  <script src='https://ccmjs.github.io/mkaul-components/parkhaus/versions/ccm.parkhaus-7.0.0.js'>
  </script>

  <ccm-parkhaus-7-0-0
          server_url="${pageContext.request.contextPath}/DemoServlet"
          extra_buttons='["Summe","avg","avg Time"]'>

  </ccm-parkhaus-7-0-0>
  </body>
</html>