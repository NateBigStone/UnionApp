package com.nathan.unionapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReservationService {

    @GET("rsvps/{code}")
    Call<RSVPdata> getRSVP(@Path("code") String mCode);
}