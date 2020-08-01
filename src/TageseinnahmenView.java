import java.util.Date;
import java.util.function.Predicate;

public class TageseinnahmenView extends Einnahmen implements IView {

    double tageseinnahmen = 0.0;
    Parkhaus parkhaus;

    public TageseinnahmenView(Parkhaus p) {
        parkhaus = p;
    }

    @Override
    public void aktualisieren() {
        tageseinnahmen = einnahmenBerechnen(parkhaus.eintraegeToStream());
    }

    @Override
    public double getValue(){
        return tageseinnahmen;
    }

    protected Predicate<Car> Zeitfilter() {
        return (x -> ((x.ankunft + x.dauer)) / (1000 * 60 * 60 * 24) == (new Date().getTime() / (1000 * 60 * 60 * 24)));
    }

    protected void zusatz() {

        System.out.println("Tageseinnahmen: ");

    }
}
