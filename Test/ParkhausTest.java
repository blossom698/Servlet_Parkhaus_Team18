import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ParkhausTest {

    Parkhaus parkhaus;
    Parkhaus parkhaus2;
    Date d;

    @BeforeEach
    public void setUp(){
        d = new Date();
        parkhaus = new Parkhaus(10);
        parkhaus2= new Parkhaus(10);
        Car a1 = new Car(1, d.getTime(),  1, "Frauen");
        Car a2 = new Car(2, d.getTime(),  2, "Familie");
        Car a3 = new Car(3, d.getTime(),  4, "any");

        parkhaus2.einparken(a1);
        parkhaus2.einparken(a2);
        parkhaus2.einparken(a3);
        parkhaus2.verlassen(1,6100,100);
        parkhaus2.verlassen(2,7200,200);
        parkhaus2.verlassen(3,8300,300);
    }

    @Test
    @DisplayName("Ein Auto soll korrekt ein- und ausgeparkt werden")
    public void test_ein_ausparken(){
        Car auto = new Car(1, 200, 1, "Frauen");
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

        assertEquals("["+d.getTime()+","+d.getTime()+","+d.getTime()+"]", (parkhaus2.asBeginArray().build()).toString());
    }

    @Test
    @DisplayName("asEndArray soll die Abfahrtszeit aller Autos ausgeben")
    public void asEndArray_test() {

        assertEquals("["+(d.getTime()+100)+","+(d.getTime()+200)+","+(d.getTime()+300)+"]", (parkhaus2.asEndArray().build()).toString());
    }

    @Test
    @DisplayName("gibTageseinnahmen soll die korrekten Tageseinnahmen ausgaben")
    public void gibTageseinnahmen_test() {
        assertEquals( 6100+7200+8300, parkhaus2.gibTagesseinnahmen());
    }

    @Disabled
    @Test
    @DisplayName("gibWocheneinnahmen soll die korrekten Wocheneinnahmen ausgeben")
    public void gibWocheneinnahmen_test() {
        assertEquals(6100+7200+8300, parkhaus2.gibWocheneinnahmen());
        try {
            TimeUnit.MILLISECONDS.sleep(24*7*100);
        } catch (Exception e) {}
        assertEquals(0,parkhaus2.gibWocheneinnahmen());
    }

    @Test
    @DisplayName("gibBetrag soll die korrekten Werte nach gewissen Zeiten liefern.")
    public void gibBetrag_test() {
        parkhaus.einparken(new Car(4, new Date().getTime(),4,"Frauen"));
        System.out.println(parkhaus.gibBetrag(4));
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e) {}
        System.out.println(parkhaus.gibBetrag(4));
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (Exception e) {}
        System.out.println(parkhaus.gibBetrag(4));
    }

}