package kumeda.cookingrecord.api

import kumeda.cookingrecord.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CookingRecordsApi {

    @GET("cooking_records")
    suspend fun getPost(): Response<Post>

    // https://cooking-records.herokuapp.com/cooking_records?offset=50&limit=5
    //@GET("cooking_records?offset={offset}&limit=10")
    @GET("cooking_records")
    suspend fun getPostSelect(
        //@Path("offset") offset: Int
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<Post>

}



