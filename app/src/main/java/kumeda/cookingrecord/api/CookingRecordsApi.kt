package kumeda.cookingrecord.api

import kumeda.cookingrecord.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CookingRecordsApi {

    @GET("cooking_records")
    suspend fun getPostSelect(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<Post>

}



