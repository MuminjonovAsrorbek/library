<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #4e54c8, #8f94fb);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 25px rgba(0, 0, 0, 0.1);
        }

        .form-control:focus {
            box-shadow: 0 0 0 0.2rem rgba(78, 84, 200, 0.25);
            border-color: #4e54c8;
        }

        .btn-primary {
            background-color: #4e54c8;
            border-color: #4e54c8;
        }

        .btn-primary:hover {
            background-color: #3c41b3;
        }

        .error {
            font-size: 0.85rem;
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card p-4">
                <h3 class="text-center mb-3">Login</h3>
                <form action="login" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username"
                               value="${param.username != null ? param.username : ''}">
                        <div class="error">${usernameError != null ? usernameError : ''}</div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password">
                        <div class="error">${passwordError != null ? passwordError : ''}</div>
                    </div>

                    <div class="error text-center mb-2">${error != null ? error : ''}</div>

                    <button type="submit" class="btn btn-primary w-100">Login</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
