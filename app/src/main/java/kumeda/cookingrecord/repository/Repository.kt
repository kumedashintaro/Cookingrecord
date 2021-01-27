package kumeda.cookingrecord.repository

import kumeda.cookingrecord.api.RetrofitInstance
import kumeda.cookingrecord.model.Post
import retrofit2.Response

class Repository {

    suspend fun getPostSelect(offset: Int, limit: Int): Response<Post> {
        return RetrofitInstance.api.getPostSelect(offset, limit)
    }
}