package com.shiyuan.bookstore.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "customername")
    private String customername;

    @Column(name = "book_id")
    private int book_id;
    @Column(name = "book_title")
    private String book_title;
    @Column(name = "book_author")
    private String book_author;
    @Column(name = "book_isbn")
    private String book_isbn;

    @Column(name = "number")
    private int number;
    @Column(name = "consignee")
    private String consignee;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;
    @Column(name = "processedby")
    private String processedby;

    @Column(name = "createdat")
    private LocalDateTime createdat;
    @Column(name = "updatedat")
    private LocalDateTime updatedat;

    public Order() {
    }

    public Order(int id, String customername, int book_id, String book_title, String book_author, String book_isbn,
            int number, String consignee, String address, String phone, String status, String processedby,
            LocalDateTime createdat, LocalDateTime updatedat) {
        this.id = id;
        this.customername = customername;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_isbn = book_isbn;
        this.number = number;
        this.consignee = consignee;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.processedby = processedby;
        // this.createdat = createdat;
        // this.updatedat = updatedat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessedby() {
        return processedby;
    }

    public void setProcessedby(String processedby) {
        this.processedby = processedby;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customername=" + customername + ", book_id=" + book_id + ", book_title="
                + book_title + ", book_author=" + book_author + ", book_isbn=" + book_isbn + ", number=" + number
                + ", consignee=" + consignee + ", address=" + address + ", phone=" + phone + ", status=" + status
                + ", processedby=" + processedby + ", createdat=" + createdat + ", updatedat=" + updatedat + "]";
    }

}
