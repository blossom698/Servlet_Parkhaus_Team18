import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Parkhaus implements IModel{

    private Car[] autos;
    private List<Car> eintraege;
    private int freiePlaetze;
    private List <IView> views;


    public Parkhaus(int groesse) {
        this.freiePlaetze = groesse;
        autos = new Car[groesse];
        eintraege = new ArrayList<Car>();
        views = new ArrayList<IView>();
    }

    public void einparken(Car auto) {
        for (int i = 0; i < autos.length; i++) {
            if (autos[i] == null) {
                autos[i] = auto;
                break;
            }
        }
        freiePlaetze--;
    }

    public void verlassen(int id, double betrag, long dauer) {
        for (int i = 0; i < autos.length; i++) {
            if (autos[i] == null && i != autos.length-1) {
                continue;
            } else if (i == autos.length-1) {
                System.out.println("------ ID nicht gefunden, sehet her. ------");
            } else if(autos[i].id == id) {
                autos[i].setDauer_Betrag(betrag, dauer);
                eintraege.add(autos[i]);
                autos[i] = null;
                break;
            }
        }
        freiePlaetze++;

    }

    public JsonArrayBuilder asNrArray(){
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : eintraege) {
            j.add("Car" + e.id);
        }
        return j;
    }

    public JsonArrayBuilder asDurationArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : eintraege) {
            j.add(e.dauer);
        }
        return j;
    }

    public JsonArrayBuilder asBeginArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : eintraege) {
            j.add(e.ankunft);
        }
        return j;
    }

    public JsonArrayBuilder asEndArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : eintraege) {
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

    @Override
    public void anmelden(IView view) {
        views.add(view);
    }

    @Override
    public void abmelden(IView view) {
        if(views.contains(view)) {
            views.remove(view);
        }
    }

    @Override
    public void benachichtigeviews() {
        for(IView v: views) {
            v.aktualisieren();
        }
    }

    @Override
    public double gibTagesseinnahmen() {
        return eintraege.stream()
                .filter(x -> ((x.ankunft + x.dauer)) / (100*24) == (new Date().getTime()) / (100*24))
                .mapToDouble(x -> x.betrag)
                .sum();
    }

    @Override
    public double gibWocheneinnahmen() {
        return eintraege.stream()
                .filter(x -> ((x.ankunft + x.dauer)) / (100*24*7) == (new Date().getTime()) / (100*24*7))
                .mapToDouble(x -> x.betrag)
                .sum();
    }

    @Override
    public double gibBetrag(int index) { ;
        return (new Date().getTime()-autos[index].ankunft) * 0.01;
    }

    @Override
    public int gibfreieplaetze() {
        return freiePlaetze;
    }
}