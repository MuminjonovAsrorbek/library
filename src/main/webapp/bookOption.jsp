<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
    <title>Book Option</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="bg-light">

<div class="container my-5">
    <div class="card shadow rounded">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">Books Management</h3>
        </div>
        <div class="card-body">
            <table class="table table-striped table-hover align-middle text-center">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Amount</th>
                    <th>Download</th>
                    <th>User ID</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${books}" var="b">
                    <tr>
                        <td>${b.id}</td>
                        <td><c:out value="${b.title}"/></td>
                        <td><c:out value="${b.author}"/></td>
                        <td><c:out value="${b.amount}"/></td>
                        <td>
                            <c:if test="${not empty b.filePath}">
                                <form method="get" action="download" class="d-inline">
                                    <input type="hidden" name="id" value="${b.id}">
                                    <button type="submit" class="btn btn-sm btn-success">Download</button>
                                </form>
                            </c:if>
                        </td>
                        <td>${b.user.id}</td>
                        <td>
                            <form method="post" action="showBook" class="d-inline">
                                <input type="hidden" name="id" value="${b.id}">
                                <button type="submit" class="btn btn-sm btn-danger">Show</button>
                            </form>

                            <button type="button"
                                    class="btn btn-sm btn-primary"
                                    data-id="${b.id}"
                                    data-title="${fn:escapeXml(b.title)}"
                                    data-author="${fn:escapeXml(b.author)}"
                                    data-amount="${b.amount}"
                                    onclick="editFunc(this)">
                                Edit
                            </button>

                            <form method="post" action="addCustomer" class="d-inline">
                                <input type="hidden" name="bookId" value="${b.id}">
                                <button type="submit" class="btn btn-sm btn-warning">Add</button>
                            </form>

                            <form method="post" action="deleteBook" class="d-inline">
                                <input type="hidden" name="bookId" value="${b.id}">
                                <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card shadow rounded mt-5">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0" id="formTitle">Add New Book</h4>
        </div>
        <div class="card-body">
            <form method="post" action="addBook" enctype="multipart/form-data">
                <input type="hidden" id="id" name="id" value="${b.id}">

                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" id="title" name="title" value="${b.title}" class="form-control">
                    <c:if test="${not empty titleError}">
                        <span class="text-danger"><c:out value="${titleError}"/></span>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="author" class="form-label">Author</label>
                    <input type="text" id="author" name="author" value="${b.author}" class="form-control">
                    <c:if test="${not empty authorError}">
                        <span class="text-danger"><c:out value="${authorError}"/></span>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="amount" class="form-label">Amount</label>
                    <input type="number" id="amount" name="amount" value="${b.amount}" class="form-control">
                    <c:if test="${not empty amountError}">
                        <span class="text-danger"><c:out value="${amountError}"/></span>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="file" class="form-label">Upload File</label>
                    <input type="file" id="file" name="file" class="form-control">
                </div>

                <button type="submit" id="addBtn" class="btn btn-success">Add</button>
            </form>
        </div>
    </div>
</div>

<script>
    function editFunc(button) {
        const id = button.dataset.id;
        const title = button.dataset.title;
        const author = button.dataset.author;
        const amount = button.dataset.amount;

        document.getElementById("id").value = id;
        document.getElementById("title").value = title;
        document.getElementById("author").value = author;
        document.getElementById("amount").value = amount;

        document.getElementById("add    Btn").textContent = "Save Book";
        document.getElementById("formTitle").textContent = "Edit Book";
    }
</script>

</body>
</html>
