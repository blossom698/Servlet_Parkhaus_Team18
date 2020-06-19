import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    ArrayList<Car> autos = new ArrayList<Car>();

    @BeforeEach
    public void setUp() {

        Car a1 = new Car(1, 6000L,  1);
        Car a2 = new Car(2, 7000L,  2);
        Car a3 = new Car(3, 8000L,  4);

        autos.add(a1);
        autos.add(a2);
        autos.add(a3);
    }

    @Test
    @DisplayName("asNrArray soll die IDs aller Autos ausgeben")
    public void asNrArray_test() {

        String exp = "[\"Car1\",\"Car2\",\"Car3\"]";
        assertEquals(exp, Car.asNrArray(autos).build().toString());

    }

    @Test
    @DisplayName("asDurationArray soll die Parkdauer aller Autos ausgeben")
    public void asDurationArray_test() {

        assertEquals("[100,200,300]", (Car.asDurationArray(autos).build()).toString());
    }

    @Test
    @DisplayName("asBeginArray soll die Ankunftszeit aller Autos ausgeben")
    public void asBeginArray_test() {

        assertEquals("[6000,7000,8000]", (Car.asBeginArray(autos).build()).toString());
    }

    @Test
    @DisplayName("asEndArray soll die Abfahrtszeit aller Autos ausgeben")
    public void asEndArray_test() {

        assertEquals("[6100,7200,8300]", (Car.asEndArray(autos).build()).toString());
    }

}