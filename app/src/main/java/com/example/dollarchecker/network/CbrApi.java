package com.example.dollarchecker.network;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CbrApi {


    @GET("XML_dynamic.asp?VAL_NM_RQ=R01235")
    Call<ValCurs> getDollarsByRange(@Query("date_req1") String date1, @Query("date_req2") String date2);

    @GET("XML_dynamic.asp?VAL_NM_RQ=R01235")
    Single<ValCurs> getDollarsByRangeRX(@Query("date_req1") String date1, @Query("date_req2") String date2);
}
