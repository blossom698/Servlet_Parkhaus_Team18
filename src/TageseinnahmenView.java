public class TageseinnahmenView implements IView {

    double tageseinnahmen = 0.0;
    Parkhaus parkhaus;

    public TageseinnahmenView(Parkhaus p) {
        parkhaus = p;
    }

    @Override
    public void aktualisieren() {
        tageseinnahmen = new Tageseinnahmen().einnahmenBerechnen(parkhaus.eintraegeToStream());
    }

    public double getValue(){
        return tageseinnahmen;
    }
}
