package com.example.sportsbetting.detail.domain

import com.example.sportsbetting.util.Result

interface DetailRepository {

    suspend fun getOdds(eventId: String): Result<List<OddModel>>

}
