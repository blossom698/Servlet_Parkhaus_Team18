import java.util.List;

public class Parkhaus {

    private List<Car> autos;
    private float prozent;
    private int freiePlaetze;


    public Parkhaus(int groesse, float prozent){
        this.prozent=prozent;
        this.freiePlaetze=groesse;

    }

}
