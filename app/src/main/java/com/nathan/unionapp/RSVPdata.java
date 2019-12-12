package com.nathan.unionapp;
import com.fasterxml.jackson.annotation.JsonAlias;

public class RSVPdata {
    @JsonAlias({"rsvpInfo", "data"})
    public RSVPinfo rsvpInfo;
    public String id;

    public void setInfo(RSVPinfo rsvpInfo) {
        this.rsvpInfo = rsvpInfo;
    }

    @Override
    public String toString() {
        return "RSVPdata{\"id\":" + id + ",\"rsvpInfo\":" +
                rsvpInfo +
                "}";
    }
}
