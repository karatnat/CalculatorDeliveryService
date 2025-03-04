import com.example.delivery.DeliveryService;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Feature("Калькулятор стоимости доставки")
public class DeliveryServiceTests {
    private final DeliveryService service = new DeliveryService();

    @ParameterizedTest
    @DisplayName("Разные варианты комбинаций параметров доставки")
    @CsvSource({
            "0.1, SMALL, false, NORMAL, 400",
            "1.9, LARGE, true, NORMAL, 550",
            "2, SMALL, false, INCREASED, 400",
            "1.5, LARGE, true, HIGH, 770",
            "0.5, SMALL, false, VERY_HIGH, 400",
            "2.1, SMALL, true, VERY_HIGH, 800",
            "5, LARGE, false, NORMAL, 400",
            "9.9, SMALL, true, INCREASED, 600",
            "10, LARGE, false, HIGH, 420",
            "10.1, LARGE, true, HIGH, 980",
            "19.1, SMALL, false, VERY_HIGH, 480",
            "29.9, LARGE, true, NORMAL, 700",
            "30, SMALL, false, INCREASED, 400",
            "100, LARGE, false, HIGH, 700",
            "30.1, LARGE, false, NORMAL, 500",
            "30, SMALL, true, INCREASED, 720",
    })
    @Tag("Positive")
    void testCombinationCases (double distance, DeliveryService.Size size, boolean isFragile, DeliveryService.WorkloadLevel workloadLevel, double expected) {
        double result = service.calculateDeliveryCost(distance, size, isFragile, workloadLevel);
        assertEquals(expected, result, 0.01);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Появление исключения для хрупких грузов при доставке более 30 км")
    void testFragileCargoExceedsDistance() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDeliveryCost(30.1, DeliveryService.Size.SMALL, true, DeliveryService.WorkloadLevel.INCREASED);
        });

        Assertions.assertEquals("Ошибка: Хрупкие грузы не доставляются на расстояние более 30 км", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Появление исключения при расстоянии 0 км")
    void testDistanceEqualZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDeliveryCost(0, DeliveryService.Size.SMALL, true, DeliveryService.WorkloadLevel.INCREASED);
        });

        Assertions.assertEquals("Ошибка: Доставка не осуществляется на расстояние 0 км или меньше", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Появление исключения при расстоянии менее 0 км")
    void testDistanceEqualNegative() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDeliveryCost(-1, DeliveryService.Size.SMALL, true, DeliveryService.WorkloadLevel.INCREASED);
        });

        Assertions.assertEquals("Ошибка: Доставка не осуществляется на расстояние 0 км или меньше", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Появление исключения при расстоянии более 100 км")
    void testDistanceMoreThenHundred() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDeliveryCost(100.1, DeliveryService.Size.SMALL, true, DeliveryService.WorkloadLevel.INCREASED);
        });

        Assertions.assertEquals("Ошибка: Доставка не осуществляется на расстояние более 100 км", thrown.getMessage());
    }

}
