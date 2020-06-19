public class TageseinnahmenView implements IView {

    double tageseinnahmen;
    Parkhaus parkhaus;


    @Override
    public void aktualisieren() {
        tageseinnahmen = parkhaus.gibTagesseinnahmen();
    }
}
