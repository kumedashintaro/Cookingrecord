package kumeda.cookingrecord.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Post(
    val cooking_records: List<CookingRecord>,
    val pagination: Pagination
)

@Parcelize
data class CookingRecord(
    val comment: String,
    val image_url: String,
    val recipe_type: String,
    val recorded_at: String
) : Parcelable

data class Pagination(
    val limit: Int,
    val offset: Int,
    val total: Int
)