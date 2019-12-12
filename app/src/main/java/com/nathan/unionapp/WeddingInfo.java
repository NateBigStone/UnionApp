package com.nathan.unionapp;

public class WeddingInfo {

    public String weddingName;
    public String weddingCode;
    public String hashtag;
    public String weddingLocation;
    public String coupleEmail;
    public String description;
    public String weddingDate;
    public String participantTwoName;
    public String surname;
    public String instructions;
    public String participantOneName;

    @Override
    public String toString() {
        return "{" +
                "\"weddingName\":\""+ weddingName +"\"," +
                "\"weddingCode\":\""+ weddingCode +"\"," +
                "\"hashtag\":\""+ hashtag +"\"," +
                "\"weddingLocation\":\""+ weddingLocation +"\"," +
                "\"coupleEmail\":\""+ coupleEmail +"\"," +
                "\"description\":\""+ description +"\"," +
                "\"weddingDate\":\""+ weddingDate +"\"," +
                "\"participantTwoName\":\""+ participantTwoName +"\"," +
                "\"surname\":\""+ surname +"\"," +
                "\"instructions\":\""+ instructions +"\"" +
                "\"participantOneName\":\""+ participantOneName +"\"" +
                "}";
    }
}