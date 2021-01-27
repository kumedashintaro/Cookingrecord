package kumeda.cookingrecord.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kumeda.cookingrecord.MainViewModel
import kumeda.cookingrecord.MainViewModelFactory
import kumeda.cookingrecord.R
import kumeda.cookingrecord.adapter.ListAdapter
import kumeda.cookingrecord.model.MyCookingRecord
import kumeda.cookingrecord.repository.Repository
import kumeda.cookingrecord.utils.Parameter.limit
import kumeda.cookingrecord.utils.Parameter.mainDishFlag
import kumeda.cookingrecord.utils.Parameter.offset
import kumeda.cookingrecord.utils.Parameter.sideDishFlag
import kumeda.cookingrecord.utils.Parameter.soupFlag
import kumeda.cookingrecord.utils.Parameter.splitNumber
import kumeda.cookingrecord.utils.Parameter.total
import kumeda.cookingrecord.utils.toDate
import java.util.*

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val listAdapter by lazy { ListAdapter() }
    private var selectRecipeList = emptyList<MyCookingRecord>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        val layoutManager = GridLayoutManager(activity, splitNumber.toInt(), GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = layoutManager

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPostSelect(Integer.parseInt(offset), Integer.parseInt(limit))
        viewModel.myResponseSelect.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                //配列を初期化
                selectRecipeList = emptyList()
                total = response.body()?.pagination?.total.toString()
                //最初だけ置き換え
                if (limit.toInt() > total.toInt()) {
                    limit = total
                }
                progressBar.visibility = View.GONE

                if(response.body()?.cooking_records != null) {
                    val size = response.body()?.cooking_records!!.size
                    for (i in 0 until size) {
                        val myComment = response.body()?.cooking_records!![i].comment
                        val myImageUrl = response.body()?.cooking_records!![i].image_url
                        val myRecipeType = response.body()?.cooking_records!![i].recipe_type
                            .replace("main_dish", "主菜/主食")
                            .replace("side_dish", "副菜")
                            .replace("soup", "スープ")
                        val myRecordedAt =
                            response.body()?.cooking_records!![i].recorded_at.toDate()!!

                        val myCookingRecord = MyCookingRecord(
                            myComment,
                            myImageUrl,
                            myRecipeType,
                            myRecordedAt
                        )

                        if (mainDishFlag) {
                            if (myRecipeType == "主菜/主食") {
                                selectRecipeList = selectRecipeList + myCookingRecord
                                Log.d("ListFragment", "mainDishをセット")
                            }
                        } else if (sideDishFlag) {
                            if (myRecipeType == "副菜") {
                                selectRecipeList = selectRecipeList + myCookingRecord
                                Log.d("ListFragment", "sideDishをセット")
                            }
                        } else if (soupFlag) {
                            if (myRecipeType == "スープ") {
                                selectRecipeList = selectRecipeList + myCookingRecord
                                Log.d("ListFragment", "soupをセット")
                            }
                        } else {
                            selectRecipeList = selectRecipeList + myCookingRecord
                            Log.d("ListFragment", "全てセット")
                        }
                        listAdapter.setData(selectRecipeList)
                    }
                }
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        }
        return view
    }
}