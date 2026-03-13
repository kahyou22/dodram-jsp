package service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.EventDao;
import service.dto.EventDto;

@WebServlet("/event/list")
public class EventListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EventDao dao = new EventDao();
            List<EventDto> eventList = dao.getAllEvents();

            request.setAttribute("eventList", eventList);
            request.getRequestDispatcher("/service/event/event.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}