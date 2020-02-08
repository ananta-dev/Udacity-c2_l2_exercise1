package com.udacity.jdnd.course3.lesson2;

import com.udacity.jdnd.course3.lesson2.entity.Order;
import com.udacity.jdnd.course3.lesson2.entity.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OrderTest {

    private static final String PERSISTENCE_UNIT_NAME = "Order";

    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        // STEP 1: Create a factory for the persistence unit
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // STEP 2: Create an EntityManager
        EntityManager em = factory.createEntityManager();

        // STEP 3: Start a transaction
        em.getTransaction().begin();

        // STEP 4: Create an order (entity is in Transient state)
        Order order = new Order();
        order.setCustomerName("John Doe");
        order.setCustomerAddress("324 Winterthur Way, Basingstoke, RG21 7UQ, UK");
        order.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));

        // create order item1
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setItemName("Parachute");
        orderItem1.setItemCount(3);


        // create order item2
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order);
        orderItem2.setItemName("Hand glider");
        orderItem2.setItemCount(3);

        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

        // STEP 5: Persist the order entity
        em.persist(order);

        // NOTE: Order Item is NOT persisted here
        em.getTransaction().commit();

        // entity is persistent now
        System.err.println("Order ID: " + order.getOrderId());
        System.err.println("Order Item ID 1: " + orderItem1.getOrderItemId());
        System.err.println("Order Item ID 2: " + orderItem2.getOrderItemId());

        em.close();

        readOrder(order.getOrderId(), factory);
        runQuery(factory);


        // Let us not delete the order for now...
        // deleteOrder(order.getOrderId(), factory);
    }

    private static void readOrder(Integer orderId, EntityManagerFactory factory) {
        // STEP 1: Create an EntityManager
        EntityManager em = factory.createEntityManager();

        // STEP 2: use the find() method to load an order
        Order thisOrder = em.find(Order.class, orderId);
        System.err.println("Order: " + thisOrder);

        // OrderItem is fetched eagerly by using a JOIN
        thisOrder.getOrderItems().forEach(orderItem -> System.err.println("Order Item:" + orderItem));

        em.close();

    }

    private static void runQuery(EntityManagerFactory factory) {
        EntityManager em = factory.createEntityManager();

/**        Query query = em.createQuery("SELECT Order o, oi FROM Order o JOIN o.OrderItems");

        List<Order> results = query.getResultList();

        for (Order o : results) {
            System.err.println(o.getCustomerName() + " => " + o.getCustomerAddress());
        } */

//        Query query = em.createQuery("SELECT o, i FROM Order o JOIN o.orderItems i");
        Query query = em.createQuery("SELECT o FROM Order o");
        List<Object> resultList = query.getResultList();
        resultList.forEach(System.out::println);
        em.close();
    }

   private static void deleteOrder(Integer orderId, EntityManagerFactory factory) {
       // STEP 1: Create an EntityManager
       EntityManager em = factory.createEntityManager();

       // STEP 2: use the find() method to load an order
       Order order = em.find(Order.class, orderId);
       em.getTransaction().begin();
       em.remove(order);
       em.getTransaction().commit();
       em.close();
       System.err.println("Deleted Order: " + order);

   }
}