import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EinnahmenTest {

    Parkhaus parkhaus;

    @BeforeEach
    public void setup() {
        Date d = new Date();
        parkhaus = new Parkhaus(3);
        parkhaus.einparken(new Car(9, d.getTime(),1, "auto"));
        parkhaus.einparken(new Car(4, d.getTime()-(1000*60*60*24*2),2, "auto"));
        parkhaus.einparken(new Car(123, d.getTime()-(1000*60*60*24*8),3, "auto"));
        parkhaus.verlassen(9,2, 200);
        parkhaus.verlassen(4, 3, 200);
        parkhaus.verlassen(123, 7, 200);
    }
/*
    @Test
    public void TageseinnahmenTest() {
        assertEquals(2.0, parkhaus.gibTagesseinnahmen(), 0.01);
    }

    @Test
    public void WocheneinnahmenTest() {
        assertEquals(5.0, parkhaus.gibWocheneinnahmen(), 0.01);
    }
*/
}