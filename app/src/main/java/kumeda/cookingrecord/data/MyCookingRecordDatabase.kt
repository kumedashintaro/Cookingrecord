package kumeda.cookingrecord.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kumeda.cookingrecord.model.MyCookingRecord

@Database(entities = [MyCookingRecord::class], version = 1, exportSchema = false)
abstract class MyCookingRecordDatabase : RoomDatabase() {

    abstract fun myCookingRecordDao(): MyCookingRecordDao

    companion object {
        @Volatile
        private var INSTANCE: MyCookingRecordDatabase? = null

        fun getDatabase(context: Context): MyCookingRecordDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyCookingRecordDatabase::class.java,
                    "my_cooking_record_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}