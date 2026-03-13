package service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.EventDao;
import service.dto.EventDto;

@WebServlet("/event/view")
public class EventViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("id"));
        try {
            EventDao dao = new EventDao();
            EventDto event = dao.getEventById(eventId);

            request.setAttribute("event", event);
            request.getRequestDispatcher("/service/event/event_view.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
    
