package com.corporation.magazin.models;

public class ModelProduct {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Double count;
    private Integer id_podcategory;

    public ModelProduct(Integer id, String name, String description, Double price, Double count, Integer id_podcategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.id_podcategory = id_podcategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Integer getId_podcategory() {
        return id_podcategory;
    }

    public void setId_podcategory(Integer id_podcategory) {
        this.id_podcategory = id_podcategory;
    }

    @Override
    public String toString() {
        return "ModelProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", id_podcategory=" + id_podcategory +
                '}';
    }
}
