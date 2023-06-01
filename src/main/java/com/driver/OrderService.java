package com.driver;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class OrderService {
    @Autowired
    OrderRepository orderRepositary;

    public void addOrder(Order order) {
        orderRepositary.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner=new DeliveryPartner(partnerId);
        orderRepositary.addPartner(partner);
    }

    public Order getOrderById(String orderId) {
        return orderRepositary.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepositary.getPartnerById(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepositary.addOrderPartnerPair(orderId,partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepositary.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepositary.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrder() {
        return orderRepositary.getAllOrder();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepositary.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepositary.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepositary.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepositary.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepositary.deleteOrderById(orderId);
    }
}
