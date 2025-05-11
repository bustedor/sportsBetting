package com.example.sportsbetting.bulletin.data

import retrofit2.http.GET

interface BulletinService {

    @GET("events")
    suspend fun getEvents(): List<EventResponseModel>

}
