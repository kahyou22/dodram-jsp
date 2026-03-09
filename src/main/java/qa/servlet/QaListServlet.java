package qa.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qa.dao.QaDAO;
import qa.dto.QaDTO;

@WebServlet("/qa/list")
public class QaListServlet extends HttpServlet {

    private QaDAO dao = new QaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<QaDTO> list = dao.getQaList();

        request.setAttribute("qaList", list);

        request.getRequestDispatcher("/service/qa/qa_list.jsp")
               .forward(request, response);
    }
}