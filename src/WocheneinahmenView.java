public class WocheneinahmenView implements IView {

    Parkhaus parkhaus;
    double einahmenw;

    @Override
    public void aktualisieren() {
        einahmenw = new Wocheneinnahmen().einnahmenBerechnen(parkhaus.toStream());
    }
}
