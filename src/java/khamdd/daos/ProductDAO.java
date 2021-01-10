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

    public ArrayList searchByName(String nameSearched) throws Exception {
        ArrayList<ProductDTO> listSearch = null;
        try {
            String sql = "Select productID, productName, price, image, description, productCategory"
                    + " From tbl_product "
                    + "Where status = 1 and quantity > 0 and productName like ?";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nameSearched + "%");
            rs = ps.executeQuery();
            listSearch = new ArrayList<>();
            while (rs.next()) {
                ProductDTO pro = new ProductDTO(
                        rs.getString("productID"), rs.getString("productName"),
                        rs.getString("image"), rs.getString("description"),
                        rs.getString("productCategory"), rs.getFloat("price")
                );
                listSearch.add(pro);
            }
        } finally {
            closeConnection();
        }
        return listSearch;
    }

    public ArrayList searchByPriceRange(float fromPrice, float toPrice) throws Exception {
        ArrayList<ProductDTO> listSearched = null;
        try {
            String sql = "Select productID, productName, price, image, description, productCategory"
                    + " From tbl_product "
                    + "Where status = 1 and price between ? and ? order by createDate";

            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, fromPrice);
            ps.setFloat(2, toPrice);
            rs = ps.executeQuery();
            listSearched = new ArrayList<>();
            while (rs.next()) {
                ProductDTO pro = new ProductDTO(
                        rs.getString("productID"), rs.getString("productName"),
                        rs.getString("image"), rs.getString("description"),
                        rs.getString("productCategory"), rs.getFloat("price")
                );
                listSearched.add(pro);
            }
        } finally {
            closeConnection();
        }
        return listSearched;

    }

//    public ArrayList get20FirstProducts() throws Exception {
//        ArrayList<ProductDTO> listProduct = null;
//        try {
//            String sql = "Select productID, productName, price, image, description, productCategory"
//                    + " From tbl_product Limit 10";
//            conn = Connections.getConnection();
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            listProduct = new ArrayList<>();
//
//            while (rs.next()) {
//                ProductDTO pro = new ProductDTO(
//                        rs.getString("productID"), rs.getString("productName"),
//                        rs.getString("image"), rs.getString("description"),
//                        rs.getString("productCategory"), rs.getFloat("price")
//                );
//                listProduct.add(pro);
//            }
//        } finally {
//            closeConnection();
//        }
//        return listProduct;
//    }
}
