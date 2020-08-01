public class TageseinnahmenView implements IView {

    double tageseinnahmen;
    Parkhaus parkhaus;


    @Override
    public void aktualisieren() {
        tageseinnahmen = new Tageseinnahmen().einnahmenBerechnen(parkhaus.toStream());
    }
}
