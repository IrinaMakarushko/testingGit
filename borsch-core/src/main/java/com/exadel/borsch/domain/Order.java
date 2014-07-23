package com.exadel.borsch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02.08.13
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class Order {

    private int orderId;
    private String name;
    private int orderCost;
    private boolean paid;


    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
