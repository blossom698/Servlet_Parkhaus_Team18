//TODO: Überlegen, ob diese View überhaupt Sinn ergibt. (siehe oben erste Zeile auf der Seite)

public class FreieplaetzeView implements IView {

    Parkhaus parkhaus;
    double freieplaetze = 0;

    public FreieplaetzeView(Parkhaus p) {
        parkhaus = p;
    }

    @Override
    public void aktualisieren() {
        freieplaetze = (parkhaus.parkhausToStream().mapToInt(x -> 1).sum()) - (parkhaus.parkhausToStream().filter(x -> x != null).mapToInt(x -> 1).sum());
    }

    @Override
    public double getValue() {
        return freieplaetze;
    }
}
