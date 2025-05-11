package com.example.sportsbetting.bulletin.domain

import com.example.sportsbetting.util.Result

interface BulletinRepository {

    suspend fun getData(): Result<List<EventModel>>

}
