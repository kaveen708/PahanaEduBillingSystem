<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        /* Full page dark background with wallpaper */
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: url('images/2.jpg') no-repeat center center fixed;
            background-size: cover;
            color: #ffffff;
        }

        /* Overlay for dark effect */
        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.7); /* dark overlay */
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Login form box */
        .login-box {
            background-color: rgba(20, 20, 20, 0.9);
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.5);
            width: 350px;
            text-align: center;
        }

        .login-box h2 {
            margin-bottom: 25px;
            color: #00d1b2;
        }

        .login-box input[type="text"],
        .login-box input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: none;
            border-radius: 8px;
            background-color: #333;
            color: #fff;
        }

        .login-box input::placeholder {
            color: #bbb;
        }

        .login-box button {
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

        .login-box button:hover {
            background-color: #00b89c;
        }

        /* SweetAlert2 custom styling */
        .my-popup {
            background: #1e1e1e !important;
            color: #eee !important;
            border-radius: 12px !important;
            padding: 20px !important;
        }
        .my-title {
            color: #00d1b2 !important;
            font-size: 1.5rem !important;
            font-weight: bold !important;
        }
        .my-content {
            color: #ccc !important;
            font-size: 1rem !important;
        }
        .my-confirm {
            background-color: #00d1b2 !important;
            color: #000 !important;
            border-radius: 8px !important;
            font-weight: bold !important;
            padding: 10px 25px !important;
        }
        .my-confirm:hover {
            background-color: #00b89c !important;
        }
        .my-success-icon {
            border-color: #00d1b2 !important;
            color: #00d1b2 !important;
        }
        .my-error-icon {
            border-color: #e74c3c !important;
            color: #e74c3c !important;
        }

        /* Responsive */
        @media (max-width: 400px) {
            .login-box {
                width: 90%;
                padding: 25px;
            }
        }
    </style>
</head>
<body>
<div class="overlay">
    <div class="login-box">
        <h2>Pahana Edu Login</h2>
        <form action="login" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</div>

<%
    HttpSession sess = request.getSession(false);
    if (sess != null) {
        String regSuccess = (String) sess.getAttribute("reg_success");
        String loginError = (String) sess.getAttribute("login_error");
        String loginSuccess = (String) sess.getAttribute("login_success");

        if (regSuccess != null) {
%>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Success',
        text: '<%= regSuccess %>',
        customClass: {
            popup: 'my-popup',
            title: 'my-title',
            htmlContainer: 'my-content',
            confirmButton: 'my-confirm',
            icon: 'my-success-icon'
        }
    });
</script>
<%
        sess.removeAttribute("reg_success");
    }

    if (loginError != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Login Failed',
        text: '<%= loginError %>',
        customClass: {
            popup: 'my-popup',
            title: 'my-title',
            htmlContainer: 'my-content',
            confirmButton: 'my-confirm',
            icon: 'my-error-icon'
        }
    });
</script>
<%
        sess.removeAttribute("login_error");
    }

    if (loginSuccess != null) {
%>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Login Successful',
        text: '<%= loginSuccess %>',
        customClass: {
            popup: 'my-popup',
            title: 'my-title',
            htmlContainer: 'my-content',
            confirmButton: 'my-confirm',
            icon: 'my-success-icon'
        }
    }).then(() => {
        window.location.href = 'dashboard.jsp';
    });
</script>
<%
            sess.removeAttribute("login_success");
        }
    }
%>
</body>
</html>
