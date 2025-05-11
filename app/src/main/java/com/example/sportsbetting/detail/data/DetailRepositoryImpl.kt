package com.example.sportsbetting.detail.data

import com.example.sportsbetting.util.Result
import com.example.sportsbetting.detail.domain.DetailRepository
import com.example.sportsbetting.detail.domain.OddModel
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val service: DetailService
) : DetailRepository {

    override suspend fun getOdds(eventId: String): Result<List<OddModel>> {
        return try {
            Result.Success(
                service.getOdds(eventId = eventId, regions = "eu").toOddModels()
            )

        } catch (exception: Exception) {
            Result.Error(exception.message.orEmpty())
        }
    }

}
