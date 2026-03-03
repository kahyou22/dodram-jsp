package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 확장자 없는 URL을 해당 JSP로 포워딩하는 필터
 * 예: /about -> /about.jsp, /member/login -> /member/login.jsp
 * 서블릿이 등록되면 서블릿이 우선 처리되므로 자연스럽게 전환 가능
 *
 * UTF-8 인코딩 처리도 함께 수행
 */
@WebFilter("/*")
public class CleanUrlFilter implements Filter {

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // UTF-8 인코딩 설정
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        // context path 제거하여 앱 내 경로 추출
        String path = uri.substring(contextPath.length());

        // 루트 요청은 index.jsp로
        if (path.equals("") || path.equals("/")) {
            request.getRequestDispatcher("/index.jsp").forward(req, res);
            return;
        }

        // 정적 리소스(/assets/)나 WEB-INF는 그대로 통과
        if (path.startsWith("/assets/") || path.startsWith("/WEB-INF/")) {
            chain.doFilter(req, res);
            return;
        }

        // 이미 확장자가 있는 요청 (.jsp, .css, .js 등)은 그대로 통과
        String lastSegment = path.substring(path.lastIndexOf('/') + 1);
        if (lastSegment.contains(".")) {
            chain.doFilter(req, res);
            return;
        }

        // 확장자 없는 요청 -> 같은 이름의 .jsp 파일 존재 확인
        String jspPath = path + ".jsp";
        if (servletContext.getResource(jspPath) != null) {
            request.getRequestDispatcher(jspPath).forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
