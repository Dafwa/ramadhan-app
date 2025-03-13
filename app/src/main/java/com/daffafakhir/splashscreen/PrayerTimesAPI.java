package com.daffafakhir.splashscreen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerTimesAPI {
    @GET("timings")
    Call<PrayerResponse> getPrayerTimes(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("method") int method
    );
}
