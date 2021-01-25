package kumeda.cookingrecord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kumeda.cookingrecord.data.MyCookingRecordDatabase
import kumeda.cookingrecord.model.MyCookingRecord
import kumeda.cookingrecord.repository.MyCookingRecordRepository

class MyCookingRecordViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<MyCookingRecord>>
    private val repository: MyCookingRecordRepository

    init {
        val myCookingRecordDao = MyCookingRecordDatabase.getDatabase(
            application
        ).myCookingRecordDao()
        repository = MyCookingRecordRepository(myCookingRecordDao)
        readAllData = repository.readAllData
    }

    fun addMyCookingRecord(myCookingRecord: MyCookingRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMyCookingRecord(myCookingRecord)
        }
    }

    fun deleteAllUData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

}