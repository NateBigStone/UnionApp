package com.nathan.unionapp;
import com.fasterxml.jackson.annotation.JsonAlias;

public class WeddingData {
    @JsonAlias({"weddingInfo", "data"})
    public WeddingInfo weddingInfo;
    public String id;
    @Override
    public String toString() {
        return "WeddingData{\"id\":" + id + ",\"weddingInfo\":" +
                weddingInfo +
                "}";
    }
}