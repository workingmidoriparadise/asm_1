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
    
    public String login(String userID, String password) throws Exception {
        String role = "Guest";
        try{
            String sql = "SELECT role FROM tbl_login WHERE status = 1 and userID = ? and password = ?";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                role = rs.getString("role");
            }
        } finally {
            closeConnection();
        }
        return role;
    }
}
