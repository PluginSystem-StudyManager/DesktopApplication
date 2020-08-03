package Calendar.Logic;

import java.util.ArrayList;

public interface ISubject {

    public void notifyAllObservers();

    public void registriesObservers(IObserver observer);

    public void deregisterObservers(IObserver observer);

    public ArrayList<Position> deleteAllObject();
}
