/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import khamdd.dtos.ProductDTO;
import khamdd.connections.Connections;

/**
 *
 * @author KHAM
 */
public class ProductDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public ArrayList search(String searchName, String fromPrice, String toPrice, String searchCategory, int index) throws Exception {
        ArrayList<ProductDTO> listSearched = null;
        try {
            String sql = "Select productID, productName, price, image, description, productCategoryID"
                    + " From tbl_product "
                    + "Where status = 1 and productCategory like ? and productName like ? and "
                    + "price >= ? and price <= ? Order By createDate offset ? rows fetch next ? rows only";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchCategory + "%");
            ps.setString(2, "%" + searchName + "%");
            ps.setFloat(3, Float.parseFloat(fromPrice));
            if (Float.parseFloat(toPrice) == 0f) {
                ps.setFloat(4, Float.parseFloat(toPrice) + 9999999f);
            } else {
                ps.setFloat(4, Float.parseFloat(toPrice));

            }
            ps.setInt(5, index);
            ps.setInt(6, 6);
            rs = ps.executeQuery();
            listSearched = new ArrayList<>();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO(rs.getString("productID"), rs.getString("productName"),
                        rs.getString("image"), rs.getString("description"), rs.getString("productCategoryID"),
                        rs.getFloat("price"));
                listSearched.add(dto);
            }
        } finally {
            closeConnection();
        }
        return listSearched;
    }

    public ArrayList searchRandom(int index) throws Exception {
        ArrayList<ProductDTO> listSearched = null;
        try {
            String sql = "Select productID, productName, price, image, description, productCategoryID"
                    + " From tbl_product "
                    + "Where status = 1 Order By createDate offset ? rows fetch next ? rows only";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, index);
            ps.setInt(2, 6);
            rs = ps.executeQuery();
            listSearched = new ArrayList<>();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO(rs.getString("productID"), rs.getString("productName"),
                        rs.getString("image"), rs.getString("description"), rs.getString("productCategoryID"),
                        rs.getFloat("price"));
                listSearched.add(dto);
            }
        } finally {
            closeConnection();
        }
        return listSearched;
    }

    public int countProduct() throws Exception {
        int count = 0;
        try {
            String sql = "select count(productID) as count from tbl_product";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

}
