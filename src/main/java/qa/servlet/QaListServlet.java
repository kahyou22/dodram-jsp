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

		// 1. DAO에서 목록 가져오기
		List<QaDTO> qaList = dao.getQaList();

		// 2. request에 속성으로 저장
		request.setAttribute("qaList", qaList);

		// 3. JSP로 포워딩
		request.getRequestDispatcher("/service/qa/qa_list.jsp").forward(request, response);
	}
}
