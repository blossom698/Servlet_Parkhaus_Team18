import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FahrzeugTest {
    @Test
    @DisplayName("Test der Multiton-objekte: pkw")
       public void pkwTest(){
            Parkhaus p1 = new Parkhaus(20);
            Car model3 = new Car(12,1200,10, "Elektek");
            model3.typ = Fahrzeug.pkw;

            p1.einparken(model3);
            p1.verlassen(12,100, 1300);

            assertEquals(100,model3.betrag);
    }

    @Test
    @DisplayName("Test der Multiton-objekte: pickup")
    public void pickupTest(){
        Parkhaus p1 = new Parkhaus(20);
        Car cybertruck = new Car(12,1200,10, "Elektek");
        cybertruck.typ = Fahrzeug.pickup;

        p1.einparken(cybertruck);
        p1.verlassen(12,100, 1300);

        assertEquals(150,cybertruck.betrag);
    }

    @Test
    @DisplayName("Test der Multiton-objekte: suv")
    public void suvTest(){
        Parkhaus p1 = new Parkhaus(20);
        Car modelX = new Car(12,1200,10, "Elektek");
        modelX.typ = Fahrzeug.suv;

        p1.einparken(modelX);
        p1.verlassen(12,100, 1300);

        assertEquals(200,modelX.betrag);
    }


    @Test
    @DisplayName("Test der Multiton-objekte: zweirad")
    public void zweiradTest(){
        Parkhaus p1 = new Parkhaus(20);
        Car kawasaki = new Car(12,1200,10, "Elektek");
        kawasaki.typ = Fahrzeug.zweirad;

        p1.einparken(kawasaki);
        p1.verlassen(12,100, 1300);

        assertEquals(50,kawasaki.betrag);
    }

    @Test
    @DisplayName("Test der Multiton-objekte: trike")
    public void trikeTest(){
        Parkhaus p1 = new Parkhaus(20);
        Car piaggo = new Car(12,1200,10, "Elektek");
        piaggo.typ = Fahrzeug.trike;

        p1.einparken(piaggo);
        p1.verlassen(12,100, 1300);

        assertEquals(100,piaggo.betrag);
    }

    @Test
    @DisplayName("Test der Multiton-objekte: bobbycar")
    public void quadTest(){
        Parkhaus p1 = new Parkhaus(20);
        Car bobbycar = new Car(12,1200,10, "Elektek");
        bobbycar.typ = Fahrzeug.quad;

        p1.einparken(bobbycar);
        p1.verlassen(12,100, 1300);

        assertEquals(60,bobbycar.betrag);
    }

}