package fr.djinn.gestionclientprospect;

import java.io.*;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Accueil", value = "/accueil")
public class HelloServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(HelloServlet.class.getName());

    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response)
    {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response)
    {
        processRequest(request, response);
    }

    public void destroy() {
    }

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) {
        try {
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.severe("pb forward" + e.getMessage());
        }
    }
}