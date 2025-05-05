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
import java.util.List;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 21:28
 **/

@WebServlet("/bookOption")
public class BookOptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session == null) {

            req.getRequestDispatcher("index.jsp").forward(req, resp);

            return;

        }

        User user = (User) session.getAttribute("user");

        EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

        if (Objects.nonNull(user) && user.getRole().equals(Role.ADMIN)) {

            entityManager.getTransaction().begin();

            List<Book> books = entityManager.createQuery("from Book order by id", Book.class).getResultList();

            req.setAttribute("books", books);

            req.getRequestDispatcher("bookOption.jsp").forward(req, resp);

        } else {

            req.getRequestDispatcher("index.jsp").forward(req, resp);

        }

    }
}
