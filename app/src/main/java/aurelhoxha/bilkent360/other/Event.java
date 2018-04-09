package aurelhoxha.bilkent360.other;

/**
 * Created by aurel on 6/25/17.
 */

public class Event
{
    private String date;
    private String eventName;

    public Event()
    {

    }

    public Event(String date,String eventName)

    {
        this.date      = date;
        this.eventName = eventName;
    }

    public String getDate()
    {

        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getEventName()
    {

        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

}
