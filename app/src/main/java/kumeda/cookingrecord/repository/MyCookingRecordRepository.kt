package kumeda.cookingrecord.repository

import androidx.lifecycle.LiveData
import kumeda.cookingrecord.data.MyCookingRecordDao
import kumeda.cookingrecord.model.MyCookingRecord

class MyCookingRecordRepository(private val myCookingRecordDao: MyCookingRecordDao) {

    val readAllData: LiveData<List<MyCookingRecord>> = myCookingRecordDao.readAllData()

    suspend fun addMyCookingRecord(myCookingRecord: MyCookingRecord) {
        myCookingRecordDao.addMyCookingRecord(myCookingRecord)
    }
}