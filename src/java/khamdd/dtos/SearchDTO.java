/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khamdd.dtos;

import java.io.Serializable;

/**
 *
 * @author KHAM
 */
public class SearchDTO implements Serializable{
    String name, category, fromPrice, toPrice;

    public SearchDTO() {
    }

    public SearchDTO(String name, String category, String fromPrice, String toPrice) {
        this.name = name;
        this.category = category;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(String fromPrice) {
        this.fromPrice = fromPrice;
    }

    public String getToPrice() {
        return toPrice;
    }

    public void setToPrice(String toPrice) {
        this.toPrice = toPrice;
    }
    
    
}
