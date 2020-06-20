import org.glassfish.json.JsonUtil;

import java.util.function.Predicate;
import java.util.stream.Stream;

abstract public class Einnahmen {

    final public double einnahmenBerechnen(Stream<Car> stream) {

        zusatz();
        double b = stream.filter(berechnen())
                .mapToDouble(x -> x.betrag)
                .sum();
        System.out.println(b);
        return b;
    }

    abstract protected Predicate<Car> berechnen();

    abstract protected void zusatz();


}
