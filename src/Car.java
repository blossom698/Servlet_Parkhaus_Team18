import java.util.ArrayList;
import java.util.List;

public class Car implements CarIF {

    int id;
    long ankunft;
    long dauer;
    int platz;

    public Car(int id, long ankunft, long dauer, int platz) {

        this.id = id;
        this.ankunft = ankunft;
        this.dauer = dauer;
        this.platz = platz;

    }

    public static String asNrArray(ArrayList<CarIF> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (CarIF e : autos) {

            if (counter == autos.size() - 1) {
                js.append(e.id + "\n]");
                break;
            }
            js.append(e.id + ",\n");
            counter++;

        }
        return js.toString();

    }

    public static String asDurationArray(ArrayList<CarIF> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (CarIF e : autos) {

            if (counter == autos.size() - 1) {
                js.append(e.dauer + "\n]");
                break;
            }
            js.append(e.dauer + ",\n");
            counter++;

        }
        return js.toString();

    }

    public static String asBeginArray(ArrayList<CarIF> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (CarIF e : autos) {

            if (counter == autos.size() - 1) {
                js.append(e.ankunft + "\n]");
                break;
            }
            js.append(e.ankunft + ",\n");
            counter++;

        }
        return js.toString();

    }

    public static String asEndArray(ArrayList<CarIF> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (CarIF e : autos) {

            if (counter == autos.size() - 1) {
                js.append((e.ankunft+e.dauer) + "\n]");
                break;
            }
            js.append((e.ankunft+e.dauer) + ",\n");
            counter++;

        }
        return js.toString();

    }

}