package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.User;

import java.io.IOException;
import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 5/1/25 20:37
 **/

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("index.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Set<ConstraintViolation<User>> validate = StartStopListener.validatorFactory.getValidator().validate(user);
        EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

        if (validate.isEmpty()) {

            entityManager.getTransaction().begin();

            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);

            query.setParameter("username", username);
            query.setParameter("password", password);

            if (!query.getResultList().isEmpty()) {

                User singleResult = query.getSingleResult();

                if (singleResult.getRole().equals(Role.ADMIN)) {

                    req.getSession().setAttribute("user", singleResult);
                    resp.sendRedirect("/admin");

                } else {

                    req.getSession().setAttribute("user", singleResult);
                    resp.sendRedirect("/moderator");

                }

            } else {

                req.setAttribute("error", "Username or password is incorrect!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        } else {

            for (ConstraintViolation<User> violation : validate) {

                String message = violation.getMessage();
                String propertyPath = violation.getPropertyPath().toString();

                if (propertyPath.equals("username")) {
                    req.setAttribute("usernameError", message);
                } else if (propertyPath.equals("password")) {
                    req.setAttribute("passwordError", message);
                }

            }

            req.getRequestDispatcher("index.jsp").forward(req, resp);

        }

    }
}
