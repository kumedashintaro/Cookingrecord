package kumeda.cookingrecord.fragments.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kumeda.cookingrecord.R
import kumeda.cookingrecord.utils.Parameter.limit
import kumeda.cookingrecord.utils.Parameter.offset
import kumeda.cookingrecord.utils.Parameter.total

class SearchFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.total_view.setText(total)
        view.limit_number.setText(limit)
        view.offset_number.setText(offset)

        view.enter_button.setOnClickListener {
            limit = view.limit_number.text.toString()
            offset = view.offset_number.text.toString()

            if (offset.isEmpty() || limit.isEmpty() ) {
                Toast.makeText(requireContext(), "入力漏れがあります", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (offset.toInt() >= total.toInt()){
                Toast.makeText(requireContext(), "オフセット数を${total}より少なくして下さい", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (limit.toInt() > (total.toInt() - offset.toInt())){
                Toast.makeText(requireContext(), "表示数を${(total.toInt() - offset.toInt())}より少なくして下さい", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("SearchFragment", limit)
            findNavController().navigate(R.id.action_searchFragment_to_listFragment)
        }
        return view
    }
}