import java.util.Date;
import java.util.function.Predicate;

public class WocheneinnahmenView extends Einnahmen implements IView {

    Parkhaus parkhaus;
    private double einahmenw = 0.0;

    public WocheneinnahmenView(Parkhaus p) {
        parkhaus = p;
    }

    @Override
    public void aktualisieren() {
        einahmenw = einnahmenBerechnen(parkhaus.eintraegeToStream());
    }

    @Override
    public double getValue() {
        return einahmenw;
    }

    protected Predicate<Car> Zeitfilter() {
        return x -> ((x.ankunft + x.dauer)) / (1000 * 60 * 60 * 24 * 7) == (new Date().getTime()) / (1000 * 60 * 60 * 24 * 7);
    }

    protected void zusatz() {

        System.out.println("Wocheneinnahmen: ");

    }
}
