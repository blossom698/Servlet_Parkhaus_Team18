import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkhausTest {

    Parkhaus parkhaus;
    Parkhaus parkhaus2;

    @BeforeEach
    public void setUp(){

        parkhaus = new Parkhaus(10);
        parkhaus2= new Parkhaus(10);
        Car a1 = new Car(1, 6000L,  1);
        Car a2 = new Car(2, 7000L,  2);
        Car a3 = new Car(3, 8000L,  4);

        parkhaus2.einparken(a1);
        parkhaus2.einparken(a2);
        parkhaus2.einparken(a3);
        parkhaus2.verlassen(1,6100,100);
        parkhaus2.verlassen(2,7200,200);
        parkhaus2.verlassen(4,8300,300);
    }

    @Test
    @DisplayName("Ein Auto soll korrekt ein- und ausgeparkt werden")
    public void test_ein_ausparken(){
        Car auto = new Car(1, 200, 1);
        parkhaus.einparken(auto);
        assertEquals(9, parkhaus.gibfreieplaetze());
        parkhaus.verlassen(1, 1.0,3L);
        assertEquals(10, parkhaus.gibfreieplaetze());
    }

    @Test
    @DisplayName("asNrArray soll die IDs aller Autos ausgeben")
    public void asNrArray_test() {

        String exp = "[\"Car1\",\"Car2\",\"Car3\"]";
        assertEquals(exp, parkhaus2.asNrArray().build().toString());

    }

    @Test
    @DisplayName("asDurationArray soll die Parkdauer aller Autos ausgeben")
    public void asDurationArray_test() {

        assertEquals("[100,200,300]", (parkhaus2.asDurationArray().build()).toString());
    }

    @Test
    @DisplayName("asBeginArray soll die Ankunftszeit aller Autos ausgeben")
    public void asBeginArray_test() {

        assertEquals("[6000,7000,8000]", (parkhaus2.asBeginArray().build()).toString());
    }

    @Test
    @DisplayName("asEndArray soll die Abfahrtszeit aller Autos ausgeben")
    public void asEndArray_test() {

        assertEquals("[6100,7200,8300]", (parkhaus2.asEndArray().build()).toString());
    }

    @Test
    @DisplayName("gibTageseinnahmen soll die korrekten Tageseinnahmen ausgaben")
    public void gibTageseinnahmen_test() {

        assertEquals( 6100+7200+8300, parkhaus2.gibTagesseinnahmen());
    }

}