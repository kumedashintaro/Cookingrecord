package kumeda.cookingrecord.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kumeda.cookingrecord.model.Post
import kumeda.cookingrecord.repository.Repository
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponseSelect: MutableLiveData<Response<Post>> = MutableLiveData()

    fun getPostSelect(offset: Int, limit: Int) {
        viewModelScope.launch {
            val response = repository.getPostSelect(offset, limit)
            myResponseSelect.value = response
        }
    }

}