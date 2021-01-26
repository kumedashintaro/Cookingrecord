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
import kumeda.cookingrecord.utils.Parameter.total

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
        val layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = layoutManager

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        //viewModel.getPost()

        viewModel.getPostSelect(Integer.parseInt(offset), Integer.parseInt(limit))

        viewModel.myResponseSelect.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                //配列を初期化
                selectRecipeList = emptyList<MyCookingRecord>()

                total = response.body()?.pagination?.total.toString()

                progressBar.visibility = View.GONE

                val size = response.body()?.cooking_records!!.size
                for (i in 0 until size) {
                    val myComment = response.body()?.cooking_records!![i].comment
                    val myImageUrl = response.body()?.cooking_records!![i].image_url
                    val myRecipeType = response.body()?.cooking_records!![i].recipe_type
                    val myRecordedAt = response.body()?.cooking_records!![i].recorded_at

                    val myCookingRecord = MyCookingRecord(
                        myComment,
                        myImageUrl,
                        myRecipeType,
                        myRecordedAt
                    )

                    if (mainDishFlag) {
                        if (myRecipeType == "main_dish") {
                            selectRecipeList = selectRecipeList + myCookingRecord
                            Log.d("ListFragment", "mainDishをセット")
                        }
                    } else if (sideDishFlag) {
                        if (myRecipeType == "side_dish") {
                            selectRecipeList = selectRecipeList + myCookingRecord
                            Log.d("ListFragment", "sideDishをセット")
                        }
                    } else if (soupFlag) {
                        if (myRecipeType == "soup") {
                            selectRecipeList = selectRecipeList + myCookingRecord
                            Log.d("ListFragment", "soupをセット")
                        }
                    } else {
                        selectRecipeList = selectRecipeList + myCookingRecord
                        Log.d("ListFragment", "全てセット")
                    }
                    listAdapter.setData(selectRecipeList)
                    //response.body()?.cooking_records.let { listAdapter.setData(it!!) }
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