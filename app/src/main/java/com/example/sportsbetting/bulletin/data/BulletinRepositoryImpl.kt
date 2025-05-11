package com.example.sportsbetting.bulletin.data

import com.example.sportsbetting.bulletin.domain.BulletinRepository
import com.example.sportsbetting.bulletin.domain.EventModel
import com.example.sportsbetting.util.Result
import javax.inject.Inject

class BulletinRepositoryImpl @Inject constructor(
    private val service: BulletinService
) : BulletinRepository {

    override suspend fun getData(): Result<List<EventModel>> {
        return try {
            Result.Success(
                service.getEvents().mapNotNull { eventResponseModel ->
                    eventResponseModel.toEventModel()
                }
            )
        } catch (exception: Exception) {
            Result.Error(exception.message.orEmpty())
        }
    }

}
