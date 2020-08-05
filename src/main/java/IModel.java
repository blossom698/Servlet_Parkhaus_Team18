import java.util.stream.Stream;

public interface IModel  {

    public void anmelden(IView view);

    public void abmelden(IView view);

    public void benachrichtigeviews();

    public Stream<Car> eintraegeToStream();
    public Stream<Car> parkhausToStream();
   // public int gibfreieplaetze();

}
