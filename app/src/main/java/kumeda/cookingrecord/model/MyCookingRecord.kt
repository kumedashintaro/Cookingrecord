package kumeda.cookingrecord.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MyCookingRecord (
    val myComment: String,
    val myImageUrl: String,
    val myRecipeType: String,
    val myRecordedAt: String
): Parcelable