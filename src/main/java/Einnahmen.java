import java.util.function.Predicate;
import java.util.stream.Stream;

abstract public class Einnahmen {

    final public double einnahmenBerechnen(Stream<Car> stream) {

        zusatz();
        double b = stream.filter(Zeitfilter())
                .mapToDouble(x -> x.betrag)
                .sum();
        System.out.println(b);
        return b;
    }

    abstract protected Predicate<Car> Zeitfilter();

    abstract protected void zusatz();


}