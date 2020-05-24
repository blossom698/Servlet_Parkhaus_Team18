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

    public static int[] asNrArray(ArrayList<CarIF> autos) {

        int[] ids = new int[autos.size()];
        int counter = 0;

        for (CarIF e : autos) {
            ids[counter] = e.id;
        }
        return ids;

    }

}