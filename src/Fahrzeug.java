import java.util.Random;
public class Fahrzeug {

     public double preis;

     static final Fahrzeug pkw = new Fahrzeug(1);
     static final Fahrzeug pickup = new Fahrzeug(1.5);
     static final Fahrzeug suv = new Fahrzeug(2);
     static final Fahrzeug zweirad = new Fahrzeug(0.5);
     static final Fahrzeug trike = new Fahrzeug(1);
     static final Fahrzeug quad = new Fahrzeug(0.6);


     private Fahrzeug(double preis){
          this.preis=preis;
     }

     public static Fahrzeug zufalltyp(){
          int i = new Random().nextInt(6);
          Fahrzeug [] cars = {pkw,pickup,suv,zweirad,trike,quad};
          return cars [i];
     }

}
