public class Tageseinnahmenview implements IView {

    double tageseinnahmen;
    Parkhaus parkh ;


    @Override
    public void aktualisieren() {
        tageseinnahmen = parkh.gibTagesseinnahmen();
    }
}
