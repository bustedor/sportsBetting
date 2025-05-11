package com.example.sportsbetting.detail.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {

    @GET("events/{eventId}/odds")
    suspend fun getOdds(
        @Path("eventId") eventId: String,
        @Query("regions") regions: String
    ): OddsResponseModel

}
