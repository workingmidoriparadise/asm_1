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
import khamdd.dtos.SearchDTO;

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

    public ArrayList search(SearchDTO dto, int index) throws Exception {
        ArrayList<ProductDTO> listSearched = null;
        try {
            String sql = "Select tbl_product.productID, tbl_product.productName, tbl_product.price, tbl_product.image, tbl_product.description"
                    + " From tbl_product inner join tbl_productCategory"
                    + " on tbl_product.productCategoryID = tbl_productCategory.productCategoryID"
                    + " Where tbl_product.status = 1 and tbl_product.quantity > 0 and tbl_productCategory.productCategoryName like ? and tbl_product.productName like ? and"
                    + " tbl_product.price >= ? and tbl_product.price <= ? Order By createDate offset ? rows fetch next ? rows only";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + dto.getCategory() + "%");
            ps.setString(2, "%" + dto.getName() + "%");
            ps.setFloat(3, dto.getFromPrice());
            ps.setFloat(4, dto.getToPrice());
            ps.setInt(5, index);
            ps.setInt(6, 6);
            rs = ps.executeQuery();
            listSearched = new ArrayList<>();
            while (rs.next()) {
                ProductDTO proDTO = new ProductDTO(rs.getString("productName"),
                        rs.getString("image"), rs.getString("description"), rs.getFloat("price"));
                listSearched.add(proDTO);
            }
        } finally {
            closeConnection();
        }
        return listSearched;
    }

    public int count(SearchDTO dto) throws Exception {
        int count = 0;
        try {
            String sql = "Select count(tbl_product.productID) as count"
                    + " From tbl_product inner join tbl_productCategory on tbl_product.productCategoryID = tbl_productCategory.productCategoryID"
                    + " Where status = 1 and quantity > 0 and tbl_productCategory.productCategoryName like ? and productName like ? and "
                    + "price >= ? and price <= ?";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + dto.getCategory()+ "%");
            ps.setString(2, "%" + dto.getName() + "%");
            ps.setFloat(3, dto.getFromPrice());
            ps.setFloat(4, dto.getToPrice());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public ArrayList<ProductDTO> getListCategory() throws Exception {
        ArrayList<ProductDTO> listCategory = null;
        try {
            String sql = "Select productCategoryName from tbl_productCategory";
            conn = Connections.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            listCategory = new ArrayList<>();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setCategory(rs.getString("productCategoryName"));
                listCategory.add(dto);
            }
        } finally {
            closeConnection();
        }
        return listCategory;
    }
}
