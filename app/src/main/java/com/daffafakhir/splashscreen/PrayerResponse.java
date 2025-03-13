package com.daffafakhir.splashscreen;

import com.google.gson.annotations.SerializedName;

public class PrayerResponse {
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("timings")
        private Timings timings;

        public Timings getTimings() {
            return timings;
        }
    }

    public class Timings {
        private String Fajr;
        private String Dhuhr;
        private String Asr;
        private String Maghrib;
        private String Isha;

        public String getFajr() { return Fajr; }
        public String getDhuhr() { return Dhuhr; }
        public String getAsr() { return Asr; }
        public String getMaghrib() { return Maghrib; }
        public String getIsha() { return Isha; }
    }

}
