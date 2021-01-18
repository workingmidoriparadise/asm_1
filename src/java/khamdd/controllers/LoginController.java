/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khamdd.daos.RegistrationDAO;
import khamdd.dtos.RegistrationDTO;
import khamdd.dtos.RegistrationErrorObject;

/**
 *
 * @author KHAM
 */
public class LoginController extends HttpServlet {
    private static final String MEMBER = "member.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String ERROR = "error.jsp";
    private static final String INVALID = "index.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            RegistrationErrorObject errorObj = new RegistrationErrorObject();
            boolean check = true;
            if(userID.length() == 0) {
                check = false;
                errorObj.setUserIDError("UserID can't be blank");
            }
            if(password.length() == 0) {
                check = false;
                errorObj.setPasswordError("Password can't be blank");
            }
            
            if(check == true) {
                HttpSession session = request.getSession();
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO dto = dao.login(userID, password);
                String role = dto.getRole();
                String fullname = dto.getFullname();
                session.setAttribute("role", role);
                session.setAttribute("fullname", fullname);
                session.setAttribute("userID", dto.getUserID());
                if(role.equals("member")) {
                    url = MEMBER;
                }
                else if(role.equals("admin")) {
                    url = ADMIN;
                }
                else {
                    request.setAttribute("ERROR", "UserID or Password is invalid");
                }
            } else {
                request.setAttribute("INVALID", errorObj);
                url = INVALID;
            }
            
        } catch (Exception e) {
            log("Error at LoginController: " + e.getMessage());
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
