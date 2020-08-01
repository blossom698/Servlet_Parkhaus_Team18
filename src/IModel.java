import java.util.stream.Stream;

public interface IModel  {

    public void anmelden(IView view);

    public void abmelden(IView view);

    public void benachrichtigeviews();

    public Stream<Car> eintraegeToStream();
//    public Stream<Car> parkhausToStream(); war für gibBetrag() gedacht, vill für was anderes gut.
    public int gibfreieplaetze();

}
