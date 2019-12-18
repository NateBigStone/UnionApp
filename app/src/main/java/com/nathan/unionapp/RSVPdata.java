package com.nathan.unionapp;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RSVPdata {

    public String id;

    @JsonAlias({"rsvpInfo", "data"})
    @Nullable
    public RSVPinfo rsvpInfo;

    @Override
    public String toString() {
        return "RSVPdata{\"id\":" + id + ",\"rsvpInfo\":" +
                rsvpInfo +
                "}";
    }
}
