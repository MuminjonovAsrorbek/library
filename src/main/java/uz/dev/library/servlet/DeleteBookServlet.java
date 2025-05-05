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
import uz.dev.library.model.TakeBook;
import uz.dev.library.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 21:21
 **/

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

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

            String bookIdStr = req.getParameter("bookId");

            Integer bookId = null;

            if (bookIdStr != null && !bookIdStr.isEmpty()) bookId = Integer.parseInt(bookIdStr);

            EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

            if (user.getRole().equals(Role.ADMIN)) {

                Book book = entityManager.find(Book.class, bookId);

                if (book != null) {

                    entityManager.getTransaction().begin();

                    List<TakeBook> takeBooks = book.getTakeBook();

                    if (takeBooks != null) {
                        for (TakeBook takeBook : takeBooks) {
                            takeBook.getBooks().remove(book);
                        }
                        book.getTakeBook().clear();
                    }

                    entityManager.merge(book);

                    entityManager.remove(book);

                    entityManager.getTransaction().commit();

                    resp.sendRedirect("/bookOption");

                }

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }
    }
}
