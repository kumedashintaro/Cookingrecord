package kumeda.cookingrecord.fragments.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kumeda.cookingrecord.R
import kumeda.cookingrecord.utils.Parameter
import kumeda.cookingrecord.utils.Parameter.limit
import kumeda.cookingrecord.utils.Parameter.mainDishFlag
import kumeda.cookingrecord.utils.Parameter.offset
import kumeda.cookingrecord.utils.Parameter.sideDishFlag
import kumeda.cookingrecord.utils.Parameter.soupFlag
import kumeda.cookingrecord.utils.Parameter.splitNumber
import kumeda.cookingrecord.utils.Parameter.total
import java.util.*

class SearchFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.total_view.text = total
        view.split_number.setText(splitNumber)
        view.limit_number.setText(limit)
        view.offset_number.setText(offset)
        mainDishFlag = false
        sideDishFlag = false
        soupFlag = false
        
        view.select_type_chipGroup.main_dish_chip.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            mainDishFlag = true
            sideDishFlag = false
            soupFlag = false
        }

        view.select_type_chipGroup.side_dish_chip.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            mainDishFlag = false
            sideDishFlag = true
            soupFlag = false
        }

        view.select_type_chipGroup.soup_chip.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            mainDishFlag = false
            sideDishFlag = false
            soupFlag = true
        }

        view.enter_button.setOnClickListener {
            splitNumber = view.split_number.text.toString()
            offset = view.offset_number.text.toString()
            limit = view.limit_number.text.toString()


            if (splitNumber.isEmpty()) {
                Toast.makeText(requireContext(), "分割数が入力されていません", Toast.LENGTH_SHORT).show()
                resetLimitNumber()
                return@setOnClickListener
            }
            if (offset.isEmpty()) {
                Toast.makeText(requireContext(), "オフセット数が入力されていません", Toast.LENGTH_SHORT).show()
                resetOffsetNumber()
                return@setOnClickListener
            }
            if (limit.isEmpty()) {
                Toast.makeText(requireContext(), "表示数が入力されていません", Toast.LENGTH_SHORT).show()
                resetLimittNumber()
                return@setOnClickListener
            }

            if ( splitNumber.toInt() > 4 ) {
                Toast.makeText(requireContext(), "最大分割数は4です", Toast.LENGTH_SHORT).show()
                resetLimitNumber()
                return@setOnClickListener
            }else if ( splitNumber.toInt() < 1 ){
                Toast.makeText(requireContext(), "1以上入力して下さい", Toast.LENGTH_SHORT).show()
                resetLimitNumber()
                return@setOnClickListener
            }

            if (offset.toInt() >= total.toInt()){
                Toast.makeText(requireContext(), "オフセット数を${total}より少なくして下さい", Toast.LENGTH_SHORT).show()
                resetOffsetNumber()
                return@setOnClickListener
            }
            if (limit.toInt() > (total.toInt() - offset.toInt())){
                Toast.makeText(requireContext(), "表示数を${(total.toInt() - offset.toInt())}より少なくして下さい", Toast.LENGTH_SHORT).show()
                resetLimittNumber()
                return@setOnClickListener
            }

            Log.d("SearchFragment_main", mainDishFlag.toString())
            Log.d("SearchFragment_side", sideDishFlag.toString())
            Log.d("SearchFragment_soup", soupFlag.toString())

            findNavController().navigate(R.id.action_searchFragment_to_listFragment)
        }
        return view
    }

    private fun resetLimitNumber(){
        splitNumber = "2"
        view?.split_number?.setText(splitNumber)
    }

    private fun resetOffsetNumber(){
        offset = "0"
        view?.offset_number?.setText(offset)
    }

    private fun resetLimittNumber(){
        limit = "10"
        view?.limit_number?.setText(limit)
    }
}