package kumeda.cookingrecord.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "my_cooking_record_table")
class MyCookingRecord(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val myComment: String,
    val myImageUrl: String,
    val myRecipeType: String,
    val myRecordedAt: String
) : Parcelable