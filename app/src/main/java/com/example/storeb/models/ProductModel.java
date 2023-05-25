package com.example.storeb.models;

import java.util.Objects;

public class ProductModel {
    int product_id;
    String name;
    String description;
    Float price;
    String image;
    String barcode;
    int quantity;
    boolean hasScanned;

    public ProductModel(int product_id, String name, String description, Float price, String image, String barcode, int quantity) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.barcode = barcode;
        this.quantity = quantity;
        this.hasScanned = false;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isHasScanned() {
        return hasScanned;
    }

    public void setHasScanned(boolean hasScanned) {
        this.hasScanned = hasScanned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return getProduct_id() == that.getProduct_id() && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getImage(), that.getImage()) && Objects.equals(getBarcode(), that.getBarcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct_id(), getName(), getDescription(), getPrice(), getImage(), getBarcode(), getQuantity(), isHasScanned());
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", barcode='" + barcode + '\'' +
                ", quantity=" + quantity +
                ", hasScanned=" + hasScanned +
                '}';
    }
}


