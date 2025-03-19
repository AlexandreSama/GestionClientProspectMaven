package controllers.client;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListeClientController implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "client/listeClient.jsp";
    }
}
