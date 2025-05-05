package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.Book;
import uz.dev.library.model.User;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 16:03
 **/

@WebServlet("/showBook")
public class ShowBookServlet extends HttpServlet {

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

            Integer id = null;

            if (idStr != null && !idStr.isEmpty()) id = Integer.parseInt(idStr);

            EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

            if (user.getRole().equals(Role.MODERATOR)) {

                Book book = entityManager.find(Book.class, id);

                req.setAttribute("role", user.getRole().toString().toLowerCase());

                req.setAttribute("book", book);

                req.getRequestDispatcher("showBook.jsp").forward(req, resp);

            } else if (user.getRole().equals(Role.ADMIN)) {

                Book book = entityManager.find(Book.class, id);

                req.setAttribute("role", "bookOption");

                req.setAttribute("book", book);

                req.getRequestDispatcher("showBook.jsp").forward(req, resp);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }

    }
}
