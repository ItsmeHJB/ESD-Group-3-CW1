<%-- 
    Document   : login
    Created on : 02-Dec-2020, 14:08:37
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css">
        <title>SmartCare Login</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center">SmartCare Login</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note"></p>
              <div class="container">
                <form action="" method="post">
                  <label for="uname"><b>Username</b></label>
                  <input type="text" placeholder="Enter Username" name="uname" required>
                  <label for="psw"><b>Password</b></label>
                  <input type="password" placeholder="Enter Password" name="psw" required>
                  <button type="submit" name="login" onclick="verify_login()">Login</button>
                </form>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>
