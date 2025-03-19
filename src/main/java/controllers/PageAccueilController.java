package controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.Contract;

public class PageAccueilController implements ICommand {

    @Contract(pure = true)
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        return "index.jsp" ;
    }
}