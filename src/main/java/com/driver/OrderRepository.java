package com.driver;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepository {

    HashMap<String,Order> orders=new HashMap<>();
    HashMap<String,DeliveryPartner> partners=new HashMap<>();
    HashMap<DeliveryPartner,List<Order>> pairs=new HashMap<>();
    HashSet<Order> assignedOrders=new HashSet<>();

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public void addPartner(DeliveryPartner partner) {
        partners.put(partner.getId(),partner);
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(partners.containsKey(partnerId) && orders.containsKey(orderId)){
            List<Order> orderList=new ArrayList<>();
            if(pairs.containsKey(partners.get(partnerId)))
                orderList=pairs.get(partners.get(partnerId));
            orderList.add(orders.get(orderId));
            pairs.put(partners.get(partnerId),orderList);
            assignedOrders.add(orders.get(orderId));
            partners.get(partnerId).setNumberOfOrders(orderList.size());
        }
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer count=0;
        if (pairs.containsKey(partners.get(partnerId)))
            count=pairs.get(partners.get(partnerId)).size();
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orderList=new ArrayList<>();
        if(partners.containsKey(partnerId)){
            if(pairs.containsKey(partners.get(partnerId))){
                List<Order> list=pairs.get(partners.get(partnerId));
                for(Order order:list){
                    orderList.add(order.getId());
                }
            }
        }
        return orderList;
    }

    public List<String> getAllOrder() {
        return new ArrayList<>(orders.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orders.size()-assignedOrders.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count=0;
        String[] s=time.split(":");
        int currTime=Integer.parseInt(s[0])*60+Integer.parseInt(s[1]);
        if(pairs.containsKey(partners.get(partnerId))){
            List<Order> list=pairs.get(partners.get(partnerId));
            for(Order order:list){
                if (orders.containsKey(order.getId())){
                    Order currOrder=orders.get(order.getId());
                    if (currTime<currOrder.getDeliveryTime())
                        count++;
                }
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=0;
        List<Order> orderList=pairs.get(partners.get(partnerId));
        for (Order order:orderList)
            time=Math.max(time,order.getDeliveryTime());
        int minutes=time%60;
        int hours=time/60;
        String lastTime="";
        if(hours<10)
            lastTime="0"+hours;
        else
            lastTime+=hours;
        lastTime+=":";
        if (minutes < 10)
            lastTime+="0"+minutes;
        else
            lastTime+=minutes;
        return lastTime;
    }

    public void deletePartnerById(String partnerId) {
        if(pairs.containsKey(partners.get(partnerId))){
            List<Order> orderList=pairs.get(partners.get(partnerId));
            for (Order order:orderList)
                assignedOrders.remove(order);
            pairs.remove(partners.get(partnerId));
            partners.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if (orders.containsKey(orderId)){
            assignedOrders.remove(orders.get(orderId));
            for(List<Order> orderList:pairs.values()){
                for (Order order:orderList){
                    if(order.equals(orders.get(orderId))){
                        orderList.remove(order);
                        orders.remove(orderId);
                        return;
                    }
                }
            }
            orders.remove(orderId);
        }
    }
}
