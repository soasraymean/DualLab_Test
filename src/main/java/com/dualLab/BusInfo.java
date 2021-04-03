package com.dualLab;

public class BusInfo implements Comparable<BusInfo> {
    private final String companyName;
    private final String departureTime;
    private final String arrivalTime;
    private final int departureTimeInMinutes, arrivalTimeInMinutes;


    public BusInfo(String string) {
        this.companyName = string.substring(0, string.indexOf(" "));
        this.departureTime = string.substring(string.indexOf(" ") + 1, string.lastIndexOf(" "));
        this.arrivalTime = string.substring(string.lastIndexOf(" ") + 1);
        departureTimeInMinutes = Integer.parseInt(departureTime.substring(0, 2)) * 60 + Integer.parseInt(departureTime.substring(3));
        arrivalTimeInMinutes = Integer.parseInt(arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(arrivalTime.substring(3));
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getDepartureTimeInMinutes() {
        return departureTimeInMinutes;
    }

    public int getArrivalTimeInMinutes() {
        return arrivalTimeInMinutes;
    }

    @Override
    public String toString() {
        return companyName + " " + departureTime + " " + arrivalTime;
    }

    @Override
    public int compareTo(BusInfo busInfo) {
        return getDepartureTime().compareTo(busInfo.getDepartureTime());
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }
}
