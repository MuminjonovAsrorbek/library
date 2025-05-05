package uz.dev.library.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import uz.dev.library.config.StartStopListener;
import uz.dev.library.enums.Role;
import uz.dev.library.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 19:29
 **/

@WebServlet("/moderatorOption")
public class ModeratorOptionServlet extends HttpServlet {

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

            if (user.getRole().equals(Role.ADMIN)) {

                EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                getModerators(req, entityManager);

                req.getRequestDispatcher("moderatorOption.jsp").forward(req, resp);

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
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Integer id = null;

            if (idStr != null && !idStr.isEmpty()) id = Integer.parseInt(idStr);

            if (user.getRole().equals(Role.ADMIN)) {

                User moderator = new User();
                if (id == null) {

                    moderator.setUsername(username);
                    moderator.setPassword(password);
                    moderator.setRole(Role.MODERATOR);

                    EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                    Set<ConstraintViolation<User>> validate =
                            StartStopListener.validatorFactory.getValidator().validate(moderator);

                    if (validate.isEmpty()) {

                        entityManager.getTransaction().begin();
                        entityManager.persist(moderator);
                        entityManager.getTransaction().commit();

                        resp.sendRedirect("/moderatorOption");
                    } else {

                        for (ConstraintViolation<User> violation : validate) {

                            String message = violation.getMessage();
                            Path propertyPath = violation.getPropertyPath();

                            req.setAttribute(propertyPath.toString() + "Error", message);

                        }

                        getModerators(req, entityManager);

                        req.getRequestDispatcher("moderatorOption.jsp").forward(req, resp);

                    }
                } else {

                    moderator.setId(id);
                    moderator.setUsername(username);
                    moderator.setPassword(password);
                    moderator.setRole(Role.MODERATOR);

                    EntityManager entityManager = StartStopListener.sessionFactory.createEntityManager();

                    Set<ConstraintViolation<User>> validate =
                            StartStopListener.validatorFactory.getValidator().validate(moderator);

                    if (validate.isEmpty()) {

                        entityManager.getTransaction().begin();
                        entityManager.merge(moderator);
                        entityManager.getTransaction().commit();

                        resp.sendRedirect("/moderatorOption");
                    } else {

                        for (ConstraintViolation<User> violation : validate) {
                            String message = violation.getMessage();
                            Path propertyPath = violation.getPropertyPath();

                            req.setAttribute(propertyPath.toString() + "Error", message);

                            getModerators(req, entityManager);

                            req.getRequestDispatcher("moderatorOption.jsp").forward(req, resp);
                        }

                    }

                }

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }

    }

    private static void getModerators(HttpServletRequest req, EntityManager entityManager) {
        List<User> users = entityManager.createQuery("from User order by id", User.class).getResultList();

        List<User> usersList = users.stream().filter(u -> u.getRole().equals(Role.MODERATOR)).filter(user ->
                !user.isDeleted()).toList();

        req.setAttribute("users", usersList);
    }
}
