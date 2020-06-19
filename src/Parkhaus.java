import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Parkhaus {

    private Car[] autos;
    private List<Car> eintraege;
    private int freiePlaetze;


    public Parkhaus(int groesse) {
        this.freiePlaetze = groesse;
        autos = new Car[groesse];
        eintraege = new ArrayList<Car>();
    }

    public void einparken(Car auto) {
        autos[auto.platz] = auto;
        freiePlaetze--;
    }

    public void verlassen(int platz, double betrag, long dauer) {
        autos[platz].setDauer_Betrag(betrag,dauer);
        eintraege.add(autos[platz]);
        autos[platz] = null;
        freiePlaetze++;

    }

    public JsonArrayBuilder asNrArray(){
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add("Car" + e.id);
        }
        return j;
    }

    public JsonArrayBuilder asDurationArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.dauer);
        }
        return j;
    }

    public JsonArrayBuilder asBeginArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.ankunft);
        }
        return j;
    }

    public JsonArrayBuilder asEndArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : autos) {
            j.add(e.ankunft + e.dauer);
        }
        return j;
    }


    public Stream<Car> toStream() {
        return eintraege.stream();
    }

    public void changeSize(int size) {

        if (size > autos.length) {

            Car[] autos_new = new Car[size];
            for (int i = 0; i < size; i++) {
                autos_new[i] = autos_new[i];
            }
            autos = autos_new;
        }

    }

    public String asIDString(){
        StringBuilder carBuilder = new StringBuilder();
        for (Car car: eintraege) {
            if (carBuilder.length() > 1) carBuilder.append(",");
            carBuilder.append(car.id);
        }
        return carBuilder.toString();
    }
}