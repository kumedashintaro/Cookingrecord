package kumeda.cookingrecord.api

import kumeda.cookingrecord.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface CookingRecordsApi {

    @GET("cooking_records")
    suspend fun getPost(): Response<Post>
}

// TODO 指定の範囲取り出しの為GET