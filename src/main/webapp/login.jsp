<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial; background: #f7f7f7; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .login-box { background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.2); width: 300px; }
        .error { color: red; margin-bottom: 10px; }
        .success { color: green; margin-bottom: 10px; }
        input { width: 100%; padding: 10px; margin: 6px 0; border: 1px solid #ccc; border-radius: 5px; }
        button { width: 100%; padding: 10px; background: #28a745; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #218838; }
    </style>
</head>
<body>
<div class="login-box">
    <h2>Login</h2>

    <!-- Error message -->
    <c:if test="${not empty errorMsg}">
        <div class="error">${errorMsg}</div>
    </c:if>

    <form action="login" method="post">
        <input type="text" name="username" placeholder="Enter Username" required>
        <input type="password" name="password" placeholder="Enter Password" required>
        <button type="submit">Login</button>
    </form>

    <p>Don't have an account? <a href="register.jsp">Register here</a></p>
</div>
</body>
</html>
