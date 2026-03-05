package qa.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qa/write")
public class QaWriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 파라미터 수집
        String type = request.getParameter("type"); // 말머리
        String guestName = request.getParameter("guest_name");
        String guestPassword = request.getParameter("guest_password");
        String emailId = request.getParameter("email_id");
        String emailDomain = request.getParameter("email_domain");
        String guestEmail = emailId;
        if (emailDomain != null && !emailDomain.isEmpty()) {
            guestEmail += "@" + emailDomain;
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 비회원이므로 user_num은 null
        Long userNum = null;

        // 2. DB 저장
        String sql = "INSERT INTO qa (type, title, content, user_num, guest_name, guest_password, guest_email) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            if (userNum != null) {
                pstmt.setLong(4, userNum);
            } else {
                pstmt.setNull(4, java.sql.Types.BIGINT);
            }
            pstmt.setString(5, guestName);
            pstmt.setString(6, guestPassword); // 나중에 BCrypt로 암호화 가능
            pstmt.setString(7, guestEmail);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 저장 성공 시 목록 페이지로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/service/qa/qa_list.jsp");
            } else {
                // 실패 시
                response.getWriter().println("글 작성 실패!");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
