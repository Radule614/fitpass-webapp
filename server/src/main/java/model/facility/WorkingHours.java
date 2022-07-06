package model.facility;

public final class WorkingHours {
    private static final String pattern = "^\\d{2}:\\d{2}$";

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
    public WorkingHours(WorkingHours wh){
        this.startHour = wh.startHour;
        this.startMinute = wh.startMinute;
        this.endHour = wh.endHour;
        this.endMinute = wh.endMinute;
    }

    public WorkingHours(String startTime, String endTime){
        if(startTime == null || startTime.trim().equals("") || !startTime.matches(pattern)) startTime = "00:00";
        if(endTime == null || endTime.trim().equals("") || !endTime.matches(pattern)) endTime = "24:00";
        String[] startArr = startTime.trim().split(":");
        String[] endArr = endTime.trim().split(":");

        startHour = getValidHours(Integer.parseInt(startArr[0]));
        startMinute = getValidMinutes(Integer.parseInt(startArr[1]));
        endHour = getValidHours(Integer.parseInt(endArr[0]));
        endMinute = getValidMinutes(Integer.parseInt(endArr[1]));
    }

    private static int getValidHours(int hours){
        if(hours > 24) hours = 24;
        if(hours < 0) hours = 0;
        return hours;
    }

    private static int getValidMinutes(int minutes){
        if(minutes > 60) minutes = 60;
        if(minutes < 0) minutes = 0;
        return minutes;
    }

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
