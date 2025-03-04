package com.example;

import com.example.delivery.DeliveryService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DeliveryService service = new DeliveryService();
        try {
            double deliveryCost1 = service.calculateDeliveryCost(
                    100,
                    DeliveryService.Size.LARGE,
                    false,
                    DeliveryService.WorkloadLevel.HIGH);
            System.out.println("deliveryCost1 = " + deliveryCost1);
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }

    }
}