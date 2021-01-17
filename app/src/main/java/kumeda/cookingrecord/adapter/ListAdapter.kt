package kumeda.cookingrecord.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout.view.*
import kumeda.cookingrecord.R
import kumeda.cookingrecord.model.CookingRecord

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var myList = emptyList<CookingRecord>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.comment_text.text = myList[position].comment
        holder.itemView.recipe_type_text.text = myList[position].recipe_type
        holder.itemView.recorded_at_text.text = myList[position].recorded_at
    }

    fun setData(newList: List<CookingRecord>) {
        myList = newList
        notifyDataSetChanged()
    }

}