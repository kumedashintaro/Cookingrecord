package kumeda.cookingrecord.repository

import kumeda.cookingrecord.api.RetrofitInstance
import kumeda.cookingrecord.model.Post

class Repository {

    suspend fun getPost(): Post {
        return RetrofitInstance.api.getPost()
    }
}