import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Parkhaus implements IModel {

    private Car[] autos; //aktuell parkende Autos
    private List<Car> eintraege; //Datenbank der Autos die jemals da waren (und nicht parkend)
    private int freiePlaetze;
    private List<IView> views; 


    public Parkhaus(int groesse) {
        this.freiePlaetze = groesse;
        autos = new Car[groesse];
        eintraege = new ArrayList<Car>();
        views = new ArrayList<IView>();
    }

    /*
     * Parkt das Auto an die erste freie Stelle im autos Array
     */
    public void einparken(Car auto) {
        for (int i = 0; i < autos.length; i++) {
            if (autos[i] == null) {
                autos[i] = auto;
                freiePlaetze--; //Anzahl freie Plätze dekrementieren
                break;
            }
        }
        benachrichtigeviews();
    }

    /*
     * Entfernt das Auto aus dem Array, wenn vorhanden. Wenn nicht vorhanden, dann wird in dei Konsole eine Fehlermeldung ausgegeben.
     */
    public void verlassen(int id, double betrag, long dauer) {
        for (int i = 0; i < autos.length; i++) {
            if (autos[i] != null && autos[i].id == id) {
                autos[i].setDauer_Betrag(betrag, dauer);
                eintraege.add(autos[i]);
                autos[i] = null;
                freiePlaetze++;
                break;
            } else if (i == autos.length - 1) {
                System.out.println("------ ID nicht gefunden, sehet her. ------");
            }
        }
        benachrichtigeviews();
    }

    public JsonArrayBuilder asNrArray() {
        JsonArrayBuilder j = Json.createArrayBuilder();
        for (Car e : eintraege) {
            j.add("Car" + e.id);
        }
        return j;
    }

    public JsonArrayBuilder asDurationArray() { //TODO: Anschauen, wo die Statische Version verwendet wird.
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

    public JsonArrayBuilder asCategories() {
        JsonArrayBuilder j = Json.createArrayBuilder();

        j.add(eintraege.stream().filter(x -> x.kategorie.equals("beliebig")).count());
        j.add(eintraege.stream().filter(x -> x.kategorie.equals("Familie")).count());
        j.add(eintraege.stream().filter(x -> x.kategorie.equals("Frauen")).count());
        j.add(eintraege.stream().filter(x -> x.kategorie.equals("Eingeschraenkte")).count());

        return j;
    }

    public JsonArrayBuilder asTyps() {
        JsonArrayBuilder j = Json.createArrayBuilder();

        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.pkw).count());
        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.pickup).count());
        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.suv).count());
        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.zweirad).count());
        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.trike).count());
        j.add(eintraege.stream().filter(x -> x.typ==Fahrzeug.quad).count());


        return j;
    }

    /*
     * Streamt die Einträge-Liste
     */
    public Stream<Car> eintraegeToStream() {
        return eintraege.stream();
    }

    /*
     * Streamt die aktuell parkenden Autos.
     */
    public Stream<Car> parkhausToStream() {
        return Stream.of(autos);
    }


    /*
     * Verändert die Größe des Arrays, wenn wir das Array größer machen wollen. Wenn es kleiner gemacht
     * werden soll, dann wird nichts geändert (da der Zugriffsbereich in der Applikation nur verkleinert
     * wird).
     */
    public void changeSize(int size) {

        if (size > autos.length) {
            freiePlaetze=freiePlaetze+(size-autos.length);
            Car[] autos_new = new Car[size];
            for (int i = 0; i < size; i++) {
                autos_new[i] = autos[i];
            }
            autos = autos_new;
        }else if(size < autos.length){
            freiePlaetze=0;
            for(int i=0;i<autos.length-(autos.length-size);i++){
                if(autos[i]==null){
                    freiePlaetze++;
                }
            }

        }

    }

    /*
     * Erschafft einen String aller IDs in dem Parkhaus
     */
    public String asIDString() {
        StringBuilder carBuilder = new StringBuilder();
        for (Car car : eintraege) {
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
        if (views.contains(view)) {
            views.remove(view);
        }
    }

    @Override
    public void benachrichtigeviews() {
        for (IView v : views) {
            v.aktualisieren();
        }
    }

/*
    @Override
    public double gibBetrag(int id) {

        for (int i = 0; i < autos.length; i++) {
            if (autos[i] == null && i != autos.length - 1) {
                continue;
            } else if (autos[i] != null && autos[i].id == id) {
                return (new Date().getTime() - autos[i].ankunft) * 0.01;

            } else if (i == autos.length - 1) {
                System.out.println("------ ID nicht gefunden, sehet her. ------");
            }
        }

        return -1;
    }


    @Override
    public int gibfreieplaetze() {
        return freiePlaetze;
    }
*/
}