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

//    RSVPinfo(String mealChoice,String weddingID,String lastName,String attending,
//             String mealChoiceGuest,String firstName,String weddingParty,String phone,String guest,
//             String zip,String guestLastName,String state,String email,String street,
//             String guestFirstName){
//
//        this.mealChoice = mealChoice;
//        this.weddingID = weddingID;
//        this.lastName = lastName;
//        this.attending = attending;
//        this.mealChoiceGuest = mealChoiceGuest;
//        this.firstName = firstName;
//        this.weddingParty = weddingParty;
//        this.phone = phone;
//        this.guest = guest;
//        this.zip = zip;
//        this.guestLastName = guestLastName;
//        this.state = state;
//        this.email = email;
//        this.street = street;
//        this.guestFirstName = guestFirstName;
//    }

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