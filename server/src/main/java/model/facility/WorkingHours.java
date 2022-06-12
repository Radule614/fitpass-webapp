package model.facility;

public final class WorkingHours {
    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;

    public WorkingHours(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }
//    public WorkingHours(WorkingHours wh){
//        this.startHour = wh.startHour;
//        this.startMinute = wh.startMinute;
//        this.endHour = wh.endHour;
//        this.endMinute = wh.endMinute;
//    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    @Override
    public String toString(){
        return startHour + ":" + startMinute + "-" + endHour + ":" + endMinute;
    }
}
