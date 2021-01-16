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
        String url = GUEST;
        float min, max;
        min = Float.MIN_VALUE;
        max = Float.MAX_VALUE;
        try {
            session.setAttribute("errorInput", false);
            String searchByName = request.getParameter("txtSearchByName");
            String fromPrice = request.getParameter("txtFromPrice");
            String toPrice = request.getParameter("txtToPrice");
            String searchByCategory = request.getParameter("txtSearchCategory");
            if (!fromPrice.equals("")) {
                min = Float.parseFloat(fromPrice);
            } else {
                fromPrice = "" + min;
            }
            if (!toPrice.equals("")) {
                max = Float.parseFloat(toPrice);
            } else {
                toPrice = "" + max;
            }
            try {
                Float.parseFloat(fromPrice);
                Float.parseFloat(toPrice);
            } catch (Exception e) {
                session.setAttribute("errorInput", true);
                request.getRequestDispatcher(url).forward(request, response);
            }
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setName(searchByName);
            searchDTO.setCategory(searchByCategory);
            searchDTO.setFromPrice(min);
            searchDTO.setToPrice(max);

            int index = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", request.getParameter("page"));
            if (index != 1) {
                index = (index - 1) * 6;
            }
            ProductDAO dao = new ProductDAO();
            ArrayList<ProductDTO> listSearched = null;
            listSearched = dao.search(searchDTO, index);
            session.setAttribute("listSearched", listSearched);
            int pageCount = dao.count(searchDTO);
            if (pageCount % 6 != 0) {
                pageCount = pageCount / 6 + 1;
            } else {
                pageCount = pageCount / 6;
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
