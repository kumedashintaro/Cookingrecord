package kumeda.cookingrecord.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_cooking_record_table")
class MyCookingRecord(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val myComment: String,
    val myImageUrl: String,
    val myRecipeType: String,
    val myRecordedAt: String
)