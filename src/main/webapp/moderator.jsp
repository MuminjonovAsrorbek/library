<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
    <title>Moderator Panel</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 40px;
        }
        .card {
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="container">

    <div class="card mb-4">
        <div class="card-header bg-dark text-white">
            <h5 class="mb-0">📚 Book List</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-light">
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
                                    <button type="submit" class="btn btn-sm btn-outline-success">⬇</button>
                                </form>
                            </c:if>
                        </td>
                        <td>${b.user.id}</td>
                        <td>
                            <form method="post" action="showBook" class="d-inline">
                                <input type="hidden" name="id" value="${b.id}">
                                <button type="submit" class="btn btn-sm btn-outline-danger">👁 Show</button>
                            </form>

                            <button type="button"
                                    class="btn btn-sm btn-outline-primary"
                                    data-id="${b.id}"
                                    data-title="${fn:escapeXml(b.title)}"
                                    data-author="${fn:escapeXml(b.author)}"
                                    data-amount="${b.amount}"
                                    onclick="editFunc(this)">
                                ✏ Edit
                            </button>

                            <form method="post" action="addCustomer" class="d-inline">
                                <input type="hidden" name="bookId" value="${b.id}">
                                <button type="submit" class="btn btn-sm btn-outline-success">➕ Customer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">➕ Add / ✏ Edit Book</h5>
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

                <button type="submit" id="addBtn" class="btn btn-success w-100">Add Book</button>
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

        document.getElementById("addBtn").textContent = "Save Book";
        document.getElementById("addBtn").classList.remove("btn-success");
        document.getElementById("addBtn").classList.add("btn-warning");
    }
</script>

</body>
</html>
