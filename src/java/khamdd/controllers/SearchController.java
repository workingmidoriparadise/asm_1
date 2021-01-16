/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khamdd.daos.ProductDAO;
import khamdd.dtos.ProductDTO;
import khamdd.dtos.SearchDTO;
import khamdd.dtos.SearchErrorObject;

/**
 *
 * @author KHAM
 */
public class SearchController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String MEMBER = "member.jsp";
    private static final String GUEST = "index.jsp";
    private static final String ADMIN = "admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String url = ERROR;

        try {
            url = GUEST;
            boolean check = true;
            String searchByName = request.getParameter("txtSearchByName");
            String fromPrice = request.getParameter("txtFromPrice");
            String toPrice = request.getParameter("txtToPrice");
            String searchByCategory = request.getParameter("txtSearchCategory");
            if (fromPrice.equals("")) {
                fromPrice = "0" + fromPrice;
            }
            if (toPrice.equals("")) {
                toPrice = "0" + toPrice;
            }
            try {
                Float.parseFloat(fromPrice);
            } catch (Exception e) {
                SearchErrorObject searchError = new SearchErrorObject();
                searchError.setErrorFromPrice("Your value is invalid (number only)");
                session.setAttribute("searchError", searchError);
                request.getRequestDispatcher(url).forward(request, response);
            }
            try {
                Float.parseFloat(toPrice);
            } catch (Exception e) {
                SearchErrorObject searchError = new SearchErrorObject();
                searchError.setErrorToPrice("Your value is invalid (number only)");
                session.setAttribute("searchError", searchError);
                request.getRequestDispatcher(url).forward(request, response);
            }
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setName(searchByName);
            searchDTO.setCategory(searchByCategory);
            if (fromPrice.equals("0")) {
                searchDTO.setFromPrice("");
            } else {
                searchDTO.setFromPrice(fromPrice);
            }
            if (toPrice.equals("0")) {
                searchDTO.setToPrice("");
            } else {
                searchDTO.setToPrice(toPrice);
            }
            session.setAttribute("searchDTO", searchDTO);
            int index = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", request.getParameter("page"));
            if (index != 1) {
                index = (index - 1) * 6 + 1;
            }
            ProductDAO dao = new ProductDAO();
            ArrayList<ProductDTO> listSearched = null;
            listSearched = dao.search(searchByName, fromPrice, toPrice, searchByCategory, index);
            session.setAttribute("listSearched", listSearched);
            int pageCount = dao.count(searchByName, fromPrice, toPrice, searchByCategory) / 6;
            if (pageCount == 0) {
                pageCount++;
            }
            session.setAttribute("pageCount", pageCount);
        } catch (Exception e) {
            log("Error at SearchController: " + e.getMessage());
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
