package samar.abozid.personalassistant.Models;

public class EventModel
{
    String title,time;

    public EventModel(String title, long timeInMillis)
    {
    }

    public EventModel(String title, String time)
    {
        this.title = title;
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
