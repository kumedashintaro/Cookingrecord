package kumeda.cookingrecord.api

import kumeda.cookingrecord.model.Post
import retrofit2.http.GET

interface CookingRecordsApi {

    @GET("cooking_records")
    suspend fun getPost(): Post
}