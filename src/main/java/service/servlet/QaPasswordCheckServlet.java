package service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.QaDAO;
import service.dto.QaDTO;

@WebServlet("/qa/checkPassword")
public class QaPasswordCheckServlet extends HttpServlet {

    private QaDAO dao = new QaDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long qaNum = Long.parseLong(request.getParameter("qaNum"));
        String inputPassword = request.getParameter("guestPassword");

        QaDTO qa = dao.findById(qaNum);
        if (qa == null) {
            response.getWriter().write("fail");
            return;
        }

        // 회원 글이면 비밀번호 필요 없음
        if (qa.getUserNum() != null) {
            response.getWriter().write("ok");
            return;
        }

        // 비회원 글 비밀번호 확인
        if (qa.getGuestPassword().equals(inputPassword)) { // 나중에 BCrypt 가능
            response.getWriter().write("ok");
        } else {
            response.getWriter().write("fail");
        }
    }
}
