import java.util.Date;
import java.util.function.Predicate;

public class Tageseinnahmen extends Einnahmen {

    protected Predicate<Car> berechnen() {
        return (x -> ((x.ankunft + x.dauer)) / (1000 * 60 * 60 * 24) == (new Date().getTime() / (1000 * 60 * 60 * 24)));
    }

    protected void zusatz() {

        System.out.println("Tageseinnahmen: ");

    }


}
