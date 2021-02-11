package com.example.dollarchecker.testretrofit;

import com.example.dollarchecker.ValCurs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CbrApi {


    @GET("XML_dynamic.asp?VAL_NM_RQ=R01235")
    Call<ValCurs> getDollarsByRange(@Query("date_req1") String date1, @Query("date_req2") String date2);

}
