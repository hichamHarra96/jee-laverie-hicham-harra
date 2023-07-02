package org.eclipse.jakarta.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.eclipse.jakarta.dao.UserDao;
import org.eclipse.jakarta.model.User;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/authen")
public class AuthServlet extends HttpServlet {
    @Inject
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");


        if (action != null && action.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user= userDao.getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                String token = UUID.randomUUID().toString();

                session.setAttribute("token", token);

                response.setHeader("Authorization", "Bearer " + token);

                response.setStatus(HttpServletResponse.SC_OK);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WelcomeServlet");

                dispatcher.forward(request, response);

            }

        } else if (action != null && action.equals("signup")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // verifs si les params non null
            User user= new User();
            user.setUsername(username);
            user.setPassword(password);
             userDao.addUser(user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WelcomeServlet");

            dispatcher.forward(request, response);
        }

        try {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
