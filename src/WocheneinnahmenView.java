public class WocheneinnahmenView implements IView {

    Parkhaus parkhaus;
    double einahmenw = 0.0;

    public WocheneinnahmenView(Parkhaus p) {
        parkhaus = p;
    }

    @Override
    public void aktualisieren() {
        einahmenw = new Wocheneinnahmen().einnahmenBerechnen(parkhaus.eintraegeToStream());
    }

    @Override
    public double getValue() {
        return einahmenw;
    }
}
