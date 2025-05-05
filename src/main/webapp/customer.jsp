<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
        }
    </style>
</head>
<body>

<div class="container form-container">
    <a href="${role}" class="btn btn-outline-secondary mb-4">⬅ Back</a>

    <c:if test="${empty message}">
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Add Customer</h4>
            </div>
            <div class="card-body">
                <form action="addCustomer" method="post">

                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" id="firstName" name="firstName" class="form-control">
                        <c:if test="${not empty firstNameError}">
                            <span class="text-danger"><c:out value="${firstNameError}"/></span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" id="lastName" name="lastName" class="form-control">
                        <c:if test="${not empty lastNameError}">
                            <span class="text-danger"><c:out value="${lastNameError}"/></span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone Number</label>
                        <input type="text" id="phone" name="phone" class="form-control">
                        <c:if test="${not empty phoneError}">
                            <span class="text-danger"><c:out value="${phoneError}"/></span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="passport" class="form-label">Passport</label>
                        <input type="text" id="passport" name="passport" class="form-control">
                        <c:if test="${not empty passportError}">
                            <span class="text-danger"><c:out value="${passportError}"/></span>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="returnDate" class="form-label">Return Date</label>
                        <input type="date" id="returnDate" name="returnDate" class="form-control">
                        <c:if test="${not empty returnDateError}">
                            <span class="text-danger"><c:out value="${returnDateError}"/></span>
                        </c:if>
                    </div>

                    <button type="submit" id="addBtn" class="btn btn-success w-100">Add Customer</button>

                </form>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="alert alert-info mt-4" role="alert">
            <h4 class="alert-heading">${message}</h4>
        </div>
    </c:if>

</div>

</body>
</html>
