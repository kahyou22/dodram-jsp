package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.dao.ProductDAO;
import admin.dao.CategoryDAO;
import admin.dto.ProductDTO;
import admin.dto.CategoryDTO;
import util.JsonUtil;

/**
 * 상품 관리 서블릿
 */
@WebServlet("/admin/products")
public class AdminProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("products", ProductDAO.getInstance().getAll());
        request.setAttribute("categories", CategoryDAO.getInstance().getAll());
        request.setAttribute("ProductStatus", ProductDTO.Status.values());
        request.setAttribute("productsJson", JsonUtil.toJson(ProductDAO.getInstance().getAll()));
        request.setAttribute("categoriesJson", JsonUtil.toJson(CategoryDAO.getInstance().getAll()));
        request.setAttribute("pageTitle", "상품 관리 | 관리자");
        request.setAttribute("currentPath", "/admin/products");
        request.setAttribute("contentPage", "/WEB-INF/admin/pages/products.jsp");
        request.getRequestDispatcher("/WEB-INF/admin/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "saveProduct": {
                String numStr = request.getParameter("productNumber");
                int categoryNumber = parseInt(request.getParameter("categoryNumber"), 0);
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int price = parseInt(request.getParameter("price"), 0);
                String status = request.getParameter("status");
                String thumbnailImage = request.getParameter("thumbnailImage");
                String origin = request.getParameter("origin");
                int weight = parseInt(request.getParameter("weight"), 0);
                String detailImagesStr = request.getParameter("detailImages");
                List<String> detailImages = (detailImagesStr != null && !detailImagesStr.isEmpty())
                        ? Arrays.stream(detailImagesStr.split("\\|\\|\\|"))
                            .map(String::trim).filter(s -> !s.isEmpty())
                            .collect(Collectors.toList())
                        : Collections.emptyList();

                if (numStr != null && !numStr.isEmpty()) {
                    // 수정
                    int productNumber = Integer.parseInt(numStr);
                    boolean ok = ProductDAO.getInstance().update(productNumber, categoryNumber,
                            name, description, price, status, thumbnailImage, origin, weight, detailImages);
                    out.print(ok
                            ? "{\"success\":true,\"message\":\"상품이 수정되었습니다.\"}"
                            : "{\"success\":false,\"message\":\"상품을 찾을 수 없습니다.\"}");
                } else {
                    // 추가
                    ProductDTO created = ProductDAO.getInstance().add(categoryNumber,
                            name, description, price, status, thumbnailImage, origin, weight, detailImages);
                    out.print("{\"success\":true,\"message\":\"상품이 추가되었습니다.\",\"productNumber\":" + created.getProductNumber() + "}");
                }
                break;
            }
            case "deleteProduct": {
                int productNumber = parseInt(request.getParameter("productNumber"), 0);
                boolean ok = ProductDAO.getInstance().delete(productNumber);
                out.print(ok
                        ? "{\"success\":true,\"message\":\"상품이 삭제되었습니다.\"}"
                        : "{\"success\":false,\"message\":\"상품을 찾을 수 없습니다.\"}");
                break;
            }
            case "saveCategory": {
                String numStr = request.getParameter("categoryNumber");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String icon = request.getParameter("icon");

                if (numStr != null && !numStr.isEmpty()) {
                    int categoryNumber = Integer.parseInt(numStr);
                    boolean ok = CategoryDAO.getInstance().update(categoryNumber, name, description, icon);
                    out.print(ok
                            ? "{\"success\":true,\"message\":\"카테고리가 수정되었습니다.\"}"
                            : "{\"success\":false,\"message\":\"카테고리를 찾을 수 없습니다.\"}");
                } else {
                    CategoryDTO created = CategoryDAO.getInstance().add(name, description, icon);
                    out.print("{\"success\":true,\"message\":\"카테고리가 추가되었습니다.\",\"categoryNumber\":" + created.getCategoryNumber() + "}");
                }
                break;
            }
            case "deleteCategory": {
                int categoryNumber = parseInt(request.getParameter("categoryNumber"), 0);
                boolean ok = CategoryDAO.getInstance().delete(categoryNumber);
                out.print(ok
                        ? "{\"success\":true,\"message\":\"카테고리가 삭제되었습니다.\"}"
                        : "{\"success\":false,\"message\":\"카테고리를 찾을 수 없습니다.\"}");
                break;
            }
            default:
                response.setStatus(400);
                out.print("{\"success\":false,\"message\":\"알 수 없는 action입니다.\"}");
        }
    }

    private static int parseInt(String s, int defaultVal) {
        try { return Integer.parseInt(s); } catch (Exception e) { return defaultVal; }
    }
}
