import java.util.Date;
import java.util.function.Predicate;

public class Wocheneinnahmen extends Einnahmen {

    protected void zusatz() {

        System.out.println("Wocheneinnahmen: ");

    }

    protected Predicate<Car> berechnen() {
        return x -> ((x.ankunft + x.dauer)) / (1000 * 60 * 60 * 24 * 7) == (new Date().getTime()) / (1000 * 60 * 60 * 24 * 7);
    }
}
