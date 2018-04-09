package aurelhoxha.bilkent360.other;

/**
 * Created by aurel on 6/19/17.
 */

public class Building
{
    String name;
    Double latValue;
    Double longValue;

    public Building()
    {

    }
    public Building(String name, Double latValue, Double longValue)
    {
        this.name = name;
        this.latValue = latValue;
        this.longValue = longValue;
    }
    public String getName() {
        return name;
    }

    public Double getLatValue() {
        return latValue;
    }

    public Double getLongValue() {
        return longValue;
    }

}

