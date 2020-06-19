public class Wocheneinahmen implements IView {

    Parkhaus parkh;
    double einahmenw;

    @Override
    public void aktualisieren() {
        einahmenw=parkh.gibWocheneinnahmen();
    }
}
