/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khamdd.daos.OrderDAO;
import khamdd.dtos.CartObj;
import khamdd.dtos.MyOrderDTO;
import khamdd.dtos.OrderDTO;
import khamdd.dtos.OrderDetailsDTO;

/**
 *
 * @author KHAM
 */
public class ConfirmCartController extends HttpServlet {

    private static final String SUCCESS = "view.jsp";
    private static final String FAILED = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAILED;
        try {
            HttpSession session = request.getSession();
            CartObj cart = (CartObj) session.getAttribute("cart");
            HashMap<String, MyOrderDTO> listOrder = cart.getCart();
            OrderDTO orderDTO = new OrderDTO();
            OrderDetailsDTO orderDetailDTO = new OrderDetailsDTO();
            String productID = null;
            OrderDAO dao = new OrderDAO();
            float price = 0;
            int quantity = 0;
            Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            orderDTO.setOrderDate(date);
            orderDTO.setTotal((Float) session.getAttribute("sum"));
            orderDTO.setUserID((String) session.getAttribute("userID"));
            int orderID = dao.insertOrder(orderDTO);
            boolean check = false;

            for (MyOrderDTO dto : listOrder.values()) {
                productID = dto.getProductID();
                price = dto.getPrice();
                quantity = dto.getQuantity();
                orderDetailDTO.setPrice(price);
                orderDetailDTO.setProductID(productID);
                orderDetailDTO.setQuantity(quantity);
                orderDetailDTO.setOrderID(orderID);
                check = dao.insertOrderDetail(orderDetailDTO);
            }

            if (check == true) {
                url = SUCCESS;
                session.setAttribute("success", "Success");
            } else {
                session.setAttribute("error", "Order failed");
            }
        } catch (Exception e) {
            log("Error at ConfirmCartController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
