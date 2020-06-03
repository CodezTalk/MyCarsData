package com.mahmoud.mycarsdata.apis;

import com.mahmoud.mycarsdata.model.CarData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{
    @GET("cars")
    Call<CarData> getCars(@Query("page") int pageIndex);
}
