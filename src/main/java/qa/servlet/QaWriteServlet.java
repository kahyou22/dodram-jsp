package qa.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qa.dao.QaDAO;
import qa.dto.QaDTO;

@WebServlet("/qa/write")
public class QaWriteServlet extends HttpServlet {

    private QaDAO dao = new QaDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	request.getRequestDispatcher("/service/qa/qa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        QaDTO dto = new QaDTO();
        dto.setType(request.getParameter("type"));
        dto.setTitle(request.getParameter("title"));
        dto.setContent(request.getParameter("content"));
        dto.setGuestName(request.getParameter("guest_name"));
        dto.setGuestPassword(request.getParameter("guest_password"));
        String email = request.getParameter("email_id") + "@" + request.getParameter("email_domain");
        dto.setGuestEmail(email);

        int result = dao.insertQa(dto);

        if (result > 0) {
            response.sendRedirect(request.getContextPath() + "/service/qa/qa_list.jsp");
        } else {
            response.getWriter().println("<script>alert('글 등록 실패'); history.back();</script>");
        }
    }
}