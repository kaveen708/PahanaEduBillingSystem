<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: url('images/2.jpg') no-repeat center center fixed;
            background-size: cover;
            color: #ffffff;
        }

        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.7);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column; /* allow dashboard button above form */
        }

        /* Back to Dashboard Button */
        .dashboard-btn {
            margin-bottom: 20px;
            padding: 10px 20px;
            background-color: black;
            color: #fff;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
        .dashboard-btn:hover {
            background-color: #333;
        }

        .register-box {
            background-color: rgba(20, 20, 20, 0.9);
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.5);
            width: 350px;
            text-align: center;
        }

        .register-box h2 {
            margin-bottom: 25px;
            color: #00d1b2;
        }

        .register-box input[type="text"],
        .register-box input[type="password"],
        .register-box select {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: none;
            border-radius: 8px;
            background-color: #333;
            color: #fff;
        }

        .register-box input::placeholder {
            color: #bbb;
        }

        .register-box button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 8px;
            background-color: #00d1b2;
            color: #000;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .register-box button:hover {
            background-color: #00b89c;
        }

        @media (max-width: 400px) {
            .register-box {
                width: 90%;
                padding: 25px;
            }
        }
    </style>
    <script>
        function backToDashboard() {
            window.location.href = 'dashboard.jsp';
        }
    </script>
</head>
<body>
<div class="overlay">
    <!-- Back to Dashboard Button -->
    <button class="dashboard-btn" onclick="backToDashboard()">&#8592; Back to Dashboard</button>

    <div class="register-box">
        <h2>Pahana Edu Registration</h2>
        <form action="register" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <select name="role" required>
                <option value="">-- Select Role --</option>
                <option value="staff">Staff</option>
                <option value="admin">Admin</option>
            </select>
            <button type="submit">Register</button>
        </form>
    </div>
</div>

<%
    String regSuccess = (String) session.getAttribute("reg_success");
    if (regSuccess != null) {
%>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Registered!',
        text: '<%= regSuccess %>'
    }).then(() => { window.location.href = 'login.jsp'; });
</script>
<%
        session.removeAttribute("reg_success");
    }

    String regError = (String) request.getAttribute("reg_error");
    if (regError != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Registration Failed',
        text: '<%= regError %>'
    });
</script>
<%
    }
%>
</body>
</html>
