import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest {

    Parkhaus parkhaus;
    Date d;
    TageseinnahmenView tageseinnahmenView;
    WocheneinnahmenView wocheneinnahmenView;
    FreieplaetzeView freieplaetzeView;

    @BeforeEach
    public void setUp() {
        d = new Date();
        parkhaus = new Parkhaus(10);
        tageseinnahmenView = new TageseinnahmenView(parkhaus);
        wocheneinnahmenView = new WocheneinnahmenView(parkhaus);
        freieplaetzeView = new FreieplaetzeView(parkhaus);

        parkhaus.anmelden(tageseinnahmenView);
        parkhaus.anmelden(wocheneinnahmenView);
        parkhaus.anmelden(freieplaetzeView);

        Car a1 = new Car(1, d.getTime(), 1, "Frauen");
        Car a2 = new Car(2, d.getTime(), 2, "Familie");

        parkhaus.einparken(a1);
        parkhaus.einparken(a2);
        parkhaus.verlassen(1, 6100, 100);
        parkhaus.verlassen(2, 7200, 200);
    }

    @Test
    @DisplayName("Tageseinnahmen sollen korrekt berechnet werden")
    public void tageseinnahmenViewTest() {

        assertEquals(6100 + 7200, tageseinnahmenView.getValue());
    }

    @Test
    @DisplayName("Wocheneinnahmen sollen korrekt berechnet werden")
    public void wocheneinnahmenViewTest() {

        assertEquals(6100 + 7200, wocheneinnahmenView.getValue());
    }

    @Test
    @DisplayName("Freie Plätze sollen korrekt berechnet werden")
    public void freieplaetzeViewTest() {

        Car a3 = new Car(3, d.getTime(), 3, "Familie");
        parkhaus.einparken(a3);
        assertEquals(9, freieplaetzeView.getValue());
    }

}
