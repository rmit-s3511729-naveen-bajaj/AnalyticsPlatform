<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Client</title>
 <script type="text/javascript">
      var wsocket;      
      function connect() {         
 		wsocket = new WebSocket("ws://localhost:8080/WS1/ratesrv");       
          wsocket.onmessage = onMessage;          
      }
      function onMessage(evt) {             
         document.getElementById("rate").innerHTML=evt.data;          
      }
 window.addEventListener("load", connect, false);
  </script>
</head>
<body>
<table>
<tr>
<td> <label id="rateLbl">Current Rate:</label></td>
<td> <label id="rate">0</label></td>
</tr>
</table>
</body>
</html>
