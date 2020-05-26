import java.util.ArrayList;

public class Car {

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

    public static String asNrArray(ArrayList<Car> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (Car e : autos) {
            if (counter == autos.size() - 1) {
                js.append(e.id + "\n]");
                break;
            }
            js.append(e.id + ",\n");
            counter++;
        }
        return js.toString();
    }

    public static String asDurationArray(ArrayList<Car> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (Car e : autos) {

            if (counter == autos.size() - 1) {
                js.append(e.dauer + "\n]");
                break;
            }
            js.append(e.dauer + ",\n");
            counter++;
        }
        return js.toString();
    }

    public static String asBeginArray(ArrayList<Car> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (Car e : autos) {
            if (counter == autos.size() - 1) {
                js.append(e.ankunft + "\n]");
                break;
            }
            js.append(e.ankunft + ",\n");
            counter++;
        }
        return js.toString();
    }

    public static String asEndArray(ArrayList<Car> autos) {

        int counter = 0;
        StringBuilder js = new StringBuilder("[\n");

        for (Car e : autos) {

            if (counter == autos.size() - 1) {
                js.append((e.ankunft + e.dauer) + "\n]");
                break;
            }
            js.append((e.ankunft + e.dauer) + ",\n");
            counter++;
        }
        return js.toString();
    }

}