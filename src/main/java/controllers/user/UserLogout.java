package controllers.user;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserLogout implements ICommand {
    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("logout", "Vous êtes désormais déconnecté");
        return "index.jsp";
    }
}
