package admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dao.OrderDAO;
import admin.dto.OrderDTO;
import util.JsonUtil;

/**
 * 주문 내역 서블릿
 */
@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("orders", OrderDAO.getInstance().getAllEnriched());
        request.setAttribute("OrderStatus", OrderDTO.State.values());
        request.setAttribute("ordersJson", JsonUtil.toJson(OrderDAO.getInstance().getAllEnriched()));
        request.setAttribute("OrderStatusJson", JsonUtil.toJson(OrderDTO.State.values()));
        request.setAttribute("pageTitle", "주문 내역 | 관리자");
        request.setAttribute("currentPath", "/admin/orders");
        request.setAttribute("contentPage", "/WEB-INF/admin/pages/orders.jsp");
        request.getRequestDispatcher("/WEB-INF/admin/layout.jsp").forward(request, response);
    }
}
