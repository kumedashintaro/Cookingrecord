package kumeda.cookingrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kumeda.cookingrecord.model.Post
import kumeda.cookingrecord.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Post> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }
}