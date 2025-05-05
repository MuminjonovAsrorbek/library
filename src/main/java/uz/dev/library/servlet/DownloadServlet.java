package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.Attachment;
import uz.dev.library.model.Book;
import uz.dev.library.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 18:58
 **/

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

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

            String bookIdStr = req.getParameter("id");

            Integer bookId = null;

            if (bookIdStr != null && !bookIdStr.isEmpty()) bookId = Integer.parseInt(bookIdStr);

            if (bookId == null) {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }

            if (user.getRole().equals(Role.MODERATOR)) {

                download(req, resp, bookId);

            } else if (user.getRole().equals(Role.ADMIN)) {

                download(req, resp, bookId);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }

    }

    private void download(HttpServletRequest req, HttpServletResponse resp, Integer bookId) throws IOException, ServletException {
        EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

        Book book = entityManager.find(Book.class, bookId);

        if (book != null) {

            TypedQuery<Attachment> query = entityManager.createQuery("from Attachment a where a.path = :path", Attachment.class);

            query.setParameter("path", book.getFilePath());

            Optional<Attachment> first = query.getResultList().stream().findFirst();

            if (first.isPresent()) {

                Attachment attachment = first.get();

                Path path = Path.of(attachment.getPath());

                resp.setContentType("application/octet-stream");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");
                Files.copy(path, resp.getOutputStream());

            }

        } else {

            req.getRequestDispatcher("index.jsp").forward(req, resp);

        }
    }
}
