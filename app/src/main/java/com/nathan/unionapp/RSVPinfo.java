package com.nathan.unionapp;

public class RSVPinfo {

    public String mealChoice;
    public String weddingID;
    public String lastName;
    public String attending;
    public String mealChoiceGuest;
    public String firstName;
    public String weddingParty;
    public String phone;
    public String guest;
    public String zip;
    public String guestLastName;
    public String state;
    public String email;
    public String street;
    public String guestFirstName;

    @Override
    public String toString() {
        return "{" +
                "\"mealChoice\":\""+ mealChoice +"\"," +
                "\"weddingID\":\""+ weddingID +"\"," +
                "\"lastName\":\""+ lastName +"\"," +
                "\"attending\":\""+ attending +"\"," +
                "\"mealChoiceGuest\":\""+ mealChoiceGuest +"\"," +
                "\"firstName\":\""+ firstName +"\"," +
                "\"weddingParty\":\""+ weddingParty +"\"," +
                "\"phone\":\""+ phone +"\"," +
                "\"guest\":\""+ guest +"\"," +
                "\"zip\":\""+ zip +"\"" +
                "\"guestLastName\":\""+ guestLastName +"\"" +
                "\"state\":\""+ state +"\"" +
                "\"email\":\""+ email +"\"" +
                "\"street\":\""+ street +"\"" +
                "\"guestFirstName\":\""+ guestFirstName +"\"" +
                "}";
    }
}