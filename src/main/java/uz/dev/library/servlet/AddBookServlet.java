package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.validation.ConstraintViolation;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.Attachment;
import uz.dev.library.model.Book;
import uz.dev.library.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Created by: asrorbek
 * DateTime: 5/1/25 21:32
 **/

@MultipartConfig
@WebServlet("/addBook")
public class AddBookServlet extends HttpServlet {

    private static final Path ROOT_PATH = new File("/home/asrorbek/IdeaProjects/Modul 7/library/files").toPath();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {

            req.getRequestDispatcher("index.jsp").forward(req, resp);

        } else {

            if (user.getRole().equals(Role.MODERATOR)) {

                EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                entityManager.getTransaction().begin();

                List<Book> books = entityManager.createQuery("from Book order by id", Book.class).getResultList();

                req.setAttribute("books", books);

                req.getRequestDispatcher("moderator.jsp").forward(req, resp);

            } else if (user.getRole().equals(Role.ADMIN)) {

                EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                entityManager.getTransaction().begin();

                List<Book> books = entityManager.createQuery("from Book order by id", Book.class).getResultList();

                req.setAttribute("books", books);

                req.getRequestDispatcher("moderator.jsp").forward(req, resp);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {

            req.getRequestDispatcher("index.jsp").forward(req, resp);

        } else {

            String idStr = req.getParameter("id");
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            String amountStr = req.getParameter("amount");
            Part part = req.getPart("file");

            Integer amount = null;
            Integer id = null;

            if (amountStr != null && !amountStr.isEmpty()) amount = Integer.parseInt(amountStr);
            if (idStr != null && !idStr.isEmpty()) id = Integer.parseInt(idStr);

            EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

            if (user.getRole().equals(Role.MODERATOR)) {

                page(req, resp, idStr, title, author, amount, user, part, entityManager, id);

            } else if (user.getRole().equals(Role.ADMIN)) {

                page(req, resp, idStr, title, author, amount, user, part, entityManager, id);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }


        }

    }

    private static void page(HttpServletRequest req, HttpServletResponse resp, String idStr, String title, String author, Integer amount, User user, Part part, EntityManager entityManager, Integer id) throws IOException, ServletException {
        if (idStr == null || idStr.isEmpty()) {

            addBook(req, resp, title, author, amount, user, part, entityManager);

        } else {

            editBook(req, resp, entityManager, id, title, author, amount, part, user);

        }
    }

    private static void editBook(HttpServletRequest req, HttpServletResponse resp, EntityManager entityManager, Integer id, String title, String author, Integer amount, Part part, User user) throws IOException, ServletException {
        Book book = entityManager.find(Book.class, id);

        book.setTitle(title);
        book.setAuthor(author);
        book.setAmount(amount);

        if (part.getSize() > 0) {
            book.setFilePath(downloadFile(part, entityManager));
        }

        Set<ConstraintViolation<Book>> validate = StartStopListener.validatorFactory.getValidator().validate(book);

        if (validate.isEmpty()) {

            entityManager.getTransaction().begin();

            entityManager.merge(book);

            entityManager.getTransaction().commit();

            if (user.getRole().equals(Role.MODERATOR))
                resp.sendRedirect("/" + user.getRole().toString().toLowerCase());
            else resp.sendRedirect("/bookOption");
        } else {

            getError(req, resp, entityManager, validate, user);

        }
    }

    private static void getError(HttpServletRequest req, HttpServletResponse resp, EntityManager entityManager, Set<ConstraintViolation<Book>> validate, User user) throws ServletException, IOException {
        for (ConstraintViolation<Book> violation : validate) {

            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            req.setAttribute(path + "Error", message);

        }

        List<Book> books = entityManager.createQuery("from Book order by id", Book.class).getResultList();

        req.setAttribute("books", books);

        if (user.getRole().equals(Role.MODERATOR))
            req.getRequestDispatcher(user.getRole().toString().toLowerCase() + ".jsp").forward(req, resp);
        else req.getRequestDispatcher("bookOption.jsp").forward(req, resp);

    }

    private static void addBook(HttpServletRequest req, HttpServletResponse resp, String title, String author, Integer amount, User user, Part part, EntityManager entityManager) throws IOException, ServletException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setAmount(amount);
        book.setFilePath(null);
        book.setUser(user);

        Set<ConstraintViolation<Book>> validate = StartStopListener.validatorFactory.getValidator().validate(book);

        if (validate.isEmpty()) {

            if (part.getSize() > 0) book.setFilePath(downloadFile(part, entityManager));

            entityManager.getTransaction().begin();

            entityManager.persist(book);

            entityManager.getTransaction().commit();

            if (user.getRole().equals(Role.MODERATOR))
                resp.sendRedirect("/" + user.getRole().toString().toLowerCase());
            else resp.sendRedirect("/bookOption");

        } else {

            getError(req, resp, entityManager, validate, user);

        }
    }

    private static String downloadFile(Part part, EntityManager entityManager) throws IOException {
        if (part.getSize() > 0) {

            String originalName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            long size = part.getSize();

            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            Month month = now.getMonth();

            Path yearPath = ROOT_PATH.resolve(String.valueOf(year));
            Path monthPath = yearPath.resolve(month.name());

            Files.createDirectories(monthPath);

            InputStream inputStream = part.getInputStream();

            String extension = originalName.substring(originalName.lastIndexOf("."));

            String fileNewName = UUID.randomUUID() + extension;

            Path path = monthPath.resolve(fileNewName);

            Files.copy(inputStream, path);

            Attachment attachment = new Attachment(
                    path.toString(),
                    originalName,
                    contentType,
                    size
            );

            entityManager.getTransaction().begin();

            entityManager.persist(attachment);

            entityManager.getTransaction().commit();

            return path.toString();

        }

        return null;
    }
}
