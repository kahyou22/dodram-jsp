package service.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.QaDAO;
import service.dto.QaDTO;

public class FaqListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QaDAO dao = new QaDAO();
    private static final int PAGE_SIZE = 10; // 페이지당 글 수

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category"); // 카테고리
        String pageParam = request.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int offset = (page - 1) * PAGE_SIZE;

        // DAO에서 데이터 가져오기
        List<QaDTO> faqList = dao.getQaList(category, null, null, offset, PAGE_SIZE);
        int totalCount = dao.getQaCount(category, null, null);
        int totalPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);

        // JSP로 전달
        request.setAttribute("faqList", faqList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("category", category);

        RequestDispatcher rd = request.getRequestDispatcher("/faq/faq_list.jsp");
        rd.forward(request, response);
    }
}