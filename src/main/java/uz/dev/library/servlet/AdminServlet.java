package uz.dev.library.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.dev.library.enums.Role;
import uz.dev.library.model.User;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 5/2/25 19:26
 **/

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

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

                req.getRequestDispatcher("admin.jsp").forward(req, resp);

            } else {

                req.getRequestDispatcher("index.jsp").forward(req, resp);

            }

        }

    }
}
