package com.nathan.unionapp;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservationService {

    @GET("rsvps/{RSVPCode}")
    Call<RSVPdata> getRSVP(@Path("RSVPCode") String mRSVPCode);

    @GET("wedding/{weddingCode}")
    Call<WeddingData> getWedding(@Path("weddingCode") String mWeddingCode);

    @PUT("rsvps/{RSVPCode}")
    Call<RSVPdata> updateRSVP(@Path("RSVPCode") String mRSVPCode, @Body RequestBody mBody);
}