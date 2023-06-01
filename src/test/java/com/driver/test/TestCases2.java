package com.driver.test;

import com.driver.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestCases2 {
    OrderController orderController;
    OrderService orderService;
    OrderRepository orderRepository;

    @BeforeEach
    public void setup(){
        orderController=new OrderController();
        orderService=new OrderService();
        orderRepository=new OrderRepository();
    }

    @Test
    public void addOrderSuccessfull(){
        OrderRepository orderRepository=new OrderRepository();
        OrderService orderService=new OrderService(orderRepository);
        orderController =new OrderController(orderService);
        Order order=new Order("1","20:20");
        orderController.addOrder(order);
        assertEquals(1220,orderController.getOrderById("1").getBody().getDeliveryTime());
    }

    @Test
    public void addPartnerSuccesfull(){
        OrderRepository orderRepository=new OrderRepository();
        OrderService orderService=new OrderService(orderRepository);
        orderController =new OrderController(orderService);
        DeliveryPartner deliveryPartner=new DeliveryPartner("10");
        orderController.addPartner("10");
        assertEquals(0,orderController.getPartnerById("10").getBody().getNumberOfOrders());
    }
    @Test
    public void addPartnerOrderPairSuccesfull(){
        OrderRepository orderRepository=new OrderRepository();
        OrderService orderService=new OrderService(orderRepository);
        orderController =new OrderController(orderService);

        Order order=new Order("1","20:20");
        orderController.addOrder(order);
        DeliveryPartner   deliveryPartner=new DeliveryPartner("10");
        orderController.addPartner("10");

        orderController.addOrderPartnerPair("1","10");
//        orderController.addOrderPartnerPair("2","10");
        assertEquals(1,orderController.getOrderCountByPartnerId ("10").getBody());
    }
}
