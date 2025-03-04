package com.example.delivery;

public class DeliveryService {
    private static final int MAX_DISTANCE = 100; // Максимальное расстояние для доставки
    public double calculateDeliveryCost (double distance, Size size, boolean isFragile, WorkloadLevel workloadLevel) {
        double deliveryCost = 0;

        if (distance <= 0) {
            throw new IllegalArgumentException("Ошибка: Доставка не осуществляется на расстояние 0 км или меньше");
        }

        if (distance > MAX_DISTANCE) {
            throw new IllegalArgumentException("Ошибка: Доставка не осуществляется на расстояние более " + MAX_DISTANCE + " км");
        }

        if (isFragile && distance > 30) {
            throw new IllegalArgumentException("Ошибка: Хрупкие грузы не доставляются на расстояние более 30 км");
        }

        double multiplier = workloadLevel.multiplier;

        deliveryCost = ( calculateCost(distance) + size.getSizeCost() + getFragilityCost(isFragile) ) * multiplier ;

        deliveryCost = Math.max(deliveryCost, 400.0);

        System.out.println("Calculating: cost for distance = " + calculateCost(distance)
                           + ", cost for size = " + size.getSizeCost()
                           + ", cost for fragility = " + getFragilityCost(isFragile)
                           + ", workloadLevel multilplier = " + workloadLevel.multiplier);

        return Math.round(deliveryCost * 100) / 100.0;
    }

    public enum WorkloadLevel {
        VERY_HIGH (1.6),
        HIGH (1.4),
        INCREASED (1.2),
        NORMAL(1);

        private final double multiplier;

        WorkloadLevel(double multiplier) { this.multiplier = multiplier; }

        public double getMultiplier() {
            return multiplier;
        }
    }

    public enum Size {
        SMALL (100),
        LARGE (200);

        private final double sizeCost;

        Size(double sizeCost) { this.sizeCost = sizeCost; }

        public double getSizeCost() { return sizeCost; }
    }

    private double getFragilityCost (boolean isFragile) { return isFragile ? 300 : 0; }

    private static double calculateCost (double cargoDistance) {
        double cargoCost = 0;

        if (cargoDistance > 30) {
            cargoCost = 300;
        }
        if ( cargoDistance> 10 && cargoDistance <= 30) {
            cargoCost = 200;
        }
        if (cargoDistance > 2 && cargoDistance <= 10) {
            cargoCost = 100;
        }
        if (cargoDistance > 0 && cargoDistance <= 2) {
            cargoCost = 50;
        }

        return cargoCost;
    }

}
