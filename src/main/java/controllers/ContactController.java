package controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ContactController implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        return "contact.jsp" ;
    }
}