<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderator Options</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background: #f4f6f8;
            padding: 40px 20px;
        }

        .table thead {
            background-color: #4e54c8;
            color: white;
        }

        .form-section {
            margin-top: 40px;
        }

        .form-control:focus {
            border-color: #4e54c8;
            box-shadow: 0 0 0 0.2rem rgba(78, 84, 200, 0.25);
        }

        .text-danger {
            font-size: 0.9rem;
        }

        .btn-edit {
            background-color: #4e54c8;
            border: none;
        }

        .btn-edit:hover {
            background-color: #3c41b3;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mb-4">Moderator Management</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="text-center">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Password</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="u">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.username}</td>
                    <td>${u.password}</td>
                    <td>
                        <div class="d-flex gap-2 justify-content-center">
                            <button type="button"
                                    class="btn btn-sm btn-edit text-white"
                                    data-id="${u.id}"
                                    data-username="${fn:escapeXml(u.username)}"
                                    data-password="${fn:escapeXml(u.password)}"
                                    onclick="editFunc(this)">
                                Edit
                            </button>
                            <form method="post" action="deleteModerator" class="d-inline">
                                <input type="hidden" name="id" value="${u.id}">
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="form-section">
        <h4 id="form-title">Add New Moderator</h4>
        <form method="post" action="moderatorOption">
            <input type="hidden" name="id" id="id">

            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" id="username" name="username" value="${u.username}" class="form-control">
                <c:if test="${not empty usernameError}">
                    <span class="text-danger"><c:out value="${usernameError}"/></span>
                </c:if>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="text" id="password" name="password" value="${u.password}" class="form-control">
                <c:if test="${not empty passwordError}">
                    <span class="text-danger"><c:out value="${passwordError}"/></span>
                </c:if>
            </div>

            <button type="submit" id="addBtn" class="btn btn-success w-100">Add Moderator</button>
        </form>
    </div>
</div>

<script>
    function editFunc(button) {
        const id = button.dataset.id;
        const username = button.dataset.username;
        const password = button.dataset.password;

        document.getElementById("id").value = id;
        document.getElementById("username").value = username;
        document.getElementById("password").value = password;

        document.getElementById("addBtn").innerText = "Save Changes";
        document.getElementById("form-title").innerText = "Edit Moderator";
    }
</script>

</body>
</html>




