import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.ArrayList;

public class Car {

    int id;
    long ankunft;
    long dauer=0;
    int platz;
    double betrag=0;

    public Car(int id, long ankunft, int platz) {

        this.id = id;
        this.ankunft = ankunft;
        this.platz = platz;
    }

    public void setDauer_Betrag(double betrag, long dauer) {
        this.betrag=betrag;
        this.dauer=dauer;
    }



    public static JsonArrayBuilder asNrArray(ArrayList<Car> autos) {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add("Car" + e.id);
        }
        return j;
    }

    public static JsonArrayBuilder asDurationArray(ArrayList<Car> autos) {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.dauer);
        }
        return j;
    }

    public static JsonArrayBuilder asBeginArray(ArrayList<Car> autos) {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.ankunft);
        }
        return j;
    }

    public static JsonArrayBuilder asEndArray(ArrayList<Car> autos) {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.ankunft + e.dauer);
        }
        return j;
    }

}