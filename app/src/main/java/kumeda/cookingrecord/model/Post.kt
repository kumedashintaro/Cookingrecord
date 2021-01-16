package kumeda.cookingrecord.model

data class Post(
    val cooking_records: List<CookingRecord>,
    val pagination: Pagination
)

data class CookingRecord(
    val comment: String,
    val image_url: String,
    val recipe_type: String,
    val recorded_at: String
)

data class Pagination(
    val limit: Int,
    val offset: Int,
    val total: Int
)