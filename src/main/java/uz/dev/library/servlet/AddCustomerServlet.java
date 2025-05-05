package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.validation.ConstraintViolation;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.Book;
import uz.dev.library.model.TakeBook;
import uz.dev.library.model.User;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 16:38
 **/

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {

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

                get(req, resp, user);

            } else if (user.getRole().equals(Role.ADMIN)) {

                get(req, resp, user);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }

    }

    private void get(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String bookIdStr = Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("id")).findFirst().map(Cookie::getValue).orElse(null);

        Integer bookId = null;

        if (bookIdStr != null && !bookIdStr.isEmpty()) bookId = Integer.parseInt(bookIdStr);

        EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

        Book book = entityManager.find(Book.class, bookId);

        if (book.getAmount() <= book.getTakeBook().size()) {

            req.setAttribute("message", "Book is not available");

            if (user.getRole().equals(Role.MODERATOR))
                req.setAttribute("role", user.getRole().toString().toLowerCase());
            else req.setAttribute("role", "bookOption");

            req.getRequestDispatcher("customer.jsp").forward(req, resp);

            return;
        }

        if (user.getRole().equals(Role.MODERATOR))
            req.setAttribute("role", user.getRole().toString().toLowerCase());
        else req.setAttribute("role", "bookOption");

        req.getRequestDispatcher("customer.jsp").forward(req, resp);
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

            String bookIdStr = req.getParameter("bookId");
            String currentBookIdStr = Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().
                    equals("id")).findFirst().map(Cookie::getValue).orElse(null);

            Integer bookId = null;

            if (currentBookIdStr != null && !currentBookIdStr.isEmpty()) bookId = Integer.parseInt(currentBookIdStr);

            if (bookIdStr != null) {

                Cookie cookie = new Cookie("id", bookIdStr);

                cookie.setMaxAge(60 * 60);

                cookie.setPath("/addCustomer");

                resp.addCookie(cookie);

                resp.sendRedirect("/addCustomer");

            } else if (bookId != null) {

                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String phoneNumber = req.getParameter("phone");
                String passport = req.getParameter("passport");
                String returnDateStr = req.getParameter("returnDate");

                Date returnDate = null;

                if (returnDateStr != null && !returnDateStr.isEmpty()) returnDate = Date.valueOf(returnDateStr);

                EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                if (user.getRole().equals(Role.MODERATOR)) {

                    TakeBook takeBook = new TakeBook();
                    takeBook.setFirstName(firstName);
                    takeBook.setLastName(lastName);
                    takeBook.setPhone(phoneNumber);
                    takeBook.setPassport(passport);
                    takeBook.setTakeDate(Date.valueOf(LocalDate.now()));
                    takeBook.setReturnDate(returnDate);
                    takeBook.setBooks(List.of(entityManager.find(Book.class, bookId)));

                    Set<ConstraintViolation<TakeBook>> validate =
                            StartStopListener.validatorFactory.getValidator().validate(takeBook);

                    if (validate.isEmpty()) {

                        entityManager.getTransaction().begin();

                        entityManager.persist(takeBook);

                        entityManager.getTransaction().commit();

                        Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("id")).findFirst().ifPresent(cookie -> {
                            cookie.setMaxAge(0);
                        });

                        resp.sendRedirect("/moderator");

                    } else {

                        for (ConstraintViolation<TakeBook> violation : validate) {

                            String path = violation.getPropertyPath().toString();
                            String message = violation.getMessage();

                            req.setAttribute(path + "Error", message);

                        }

                        req.getRequestDispatcher("customer.jsp").forward(req, resp);

                    }

                } else if (user.getRole().equals(Role.ADMIN)) {

                    TakeBook takeBook = new TakeBook();
                    takeBook.setFirstName(firstName);
                    takeBook.setLastName(lastName);
                    takeBook.setPhone(phoneNumber);
                    takeBook.setPassport(passport);
                    takeBook.setTakeDate(Date.valueOf(LocalDate.now()));
                    takeBook.setReturnDate(returnDate);
                    takeBook.setBooks(List.of(entityManager.find(Book.class, bookId)));

                    Set<ConstraintViolation<TakeBook>> validate =
                            StartStopListener.validatorFactory.getValidator().validate(takeBook);

                    if (validate.isEmpty()) {

                        entityManager.getTransaction().begin();

                        entityManager.persist(takeBook);

                        entityManager.getTransaction().commit();

                        Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("id")).findFirst().ifPresent(cookie -> {
                            cookie.setMaxAge(0);
                        });

                        resp.sendRedirect("/admin");

                    } else {

                        for (ConstraintViolation<TakeBook> violation : validate) {

                            String path = violation.getPropertyPath().toString();
                            String message = violation.getMessage();

                            req.setAttribute(path + "Error", message);

                        }

                        req.getRequestDispatcher("customer.jsp").forward(req, resp);

                    }

                }

            }

        }

    }
}
