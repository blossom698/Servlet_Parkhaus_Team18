import java.util.stream.Stream;

public interface IModel  {

    public void anmelden(IView view);

    public void abmelden(IView view);

    public void benachichtigeviews();

//    public double gibTagesseinnahmen();
//    public double gibWocheneinnahmen();
    public Stream<Car> toStream();
    public double gibBetrag(int index);
    public int gibfreieplaetze();

}
