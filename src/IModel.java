public interface IModel  {

    public void anmelden(IView view);

    public void abmelden(IView view);

    public void benachichtigeviews();


    public double gibTagesseinnahmen();
    public double gibWocheneinnahmen();
    public double gibBetrag(int index);
    public int gibfreieplaetze();


}
