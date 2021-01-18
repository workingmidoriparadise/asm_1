/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import khamdd.connections.Connections;
import khamdd.dtos.RegistrationDTO;

/**
 *
 * @author KHAM
 */
public class RegistrationDAO {
    ResultSet rs = null;
    PreparedStatement ps = null;
    Connection conn = null;
    
    private void closeConnection() throws Exception {
        if(rs != null) {
            rs.close();
        }
        if(ps != null) {
            ps.close();
        }
        if(conn != null) {
            conn.close();
        }
    }
    
    public RegistrationDTO login(String userID, String password) throws Exception {
        RegistrationDTO dto = null;
        try{
            String sql = "SELECT userID, role, fullname FROM tbl_user WHERE status = 1 and userID = ? and password = ?";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, password);
            rs = ps.executeQuery();
            dto = new RegistrationDTO();
            if(rs.next()){
                dto.setRole(rs.getString("role"));
                dto.setFullname(rs.getString("fullname"));
                dto.setUserID(rs.getString("userID"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
