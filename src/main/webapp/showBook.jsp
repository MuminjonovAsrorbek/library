<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Show Book</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background-color: #f8f9fa;
            padding: 40px 20px;
        }

        .table thead {
            background-color: #343a40;
            color: white;
        }

        .btn-back {
            margin-bottom: 20px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 5px;
            display: inline-block;
        }

        .btn-back:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>

<div class="container">
    <a href="${role}" class="btn-back">&larr; Back</a>

    <h2 class="mb-4">Taken Book List</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle text-center">
            <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Phone</th>
                <th>Passport</th>
                <th>Take Date</th>
                <th>Return Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${book.takeBook}" var="t">
                <tr>
                    <td>${t.id}</td>
                    <td>${t.firstName}</td>
                    <td>${t.lastName}</td>
                    <td>${t.phone}</td>
                    <td>${t.passport}</td>
                    <td>${t.takeDate}</td>
                    <td>${t.returnDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
