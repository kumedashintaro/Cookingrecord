package kumeda.cookingrecord.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kumeda.cookingrecord.model.MyCookingRecord

@Dao
interface MyCookingRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMyCookingRecord(myCookingRecord: MyCookingRecord)

    @Query("SELECT * FROM my_cooking_record_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<MyCookingRecord>>

    @Query("DELETE FROM my_cooking_record_table")
    suspend fun deleteAllData()

}