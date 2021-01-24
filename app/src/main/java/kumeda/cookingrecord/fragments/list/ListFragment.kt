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
import kotlinx.android.synthetic.main.fragment_list.view.*
import kumeda.cookingrecord.MainViewModel
import kumeda.cookingrecord.MainViewModelFactory
import kumeda.cookingrecord.MyCookingRecordViewModel
import kumeda.cookingrecord.R
import kumeda.cookingrecord.adapter.ListAdapter
import kumeda.cookingrecord.model.MyCookingRecord
import kumeda.cookingrecord.repository.Repository

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var myCookingRecordViewModel: MyCookingRecordViewModel
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
        myCookingRecordViewModel = ViewModelProvider(this).get(MyCookingRecordViewModel::class.java)

//        viewModel.getPost()

        val offset = "0"
        val limit = "5"
        viewModel.getPostSelect(Integer.parseInt(offset), Integer.parseInt(limit))

        viewModel.myResponseSelect.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {

                //データを全削除
                myCookingRecordViewModel.deleteAllUData()
                Log.d("ListFragment", " データベース全削除")

                // Databaseに保存する
                if (response.body()?.cooking_records != null) {
                    val size = response.body()?.cooking_records!!.size
                    for (i in 0 until size) {
                        val myComment = response.body()?.cooking_records!![i].comment
                        val myImageUrl = response.body()?.cooking_records!![i].image_url
                        val myRecipeType = response.body()?.cooking_records!![i].recipe_type
                        val myRecordedAt = response.body()?.cooking_records!![i].recorded_at

                        val myCookingRecord = MyCookingRecord(
                            0,
                            myComment,
                            myImageUrl,
                            myRecipeType,
                            myRecordedAt
                        )
                        //データを追加する
                            myCookingRecordViewModel.addMyCookingRecord(myCookingRecord)
                            Log.d("ListFragment", " データベースに追加された")
                    }
                }
                //response.body()?.cooking_records.let { listAdapter.setData(it!!) }
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })

        myCookingRecordViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            listAdapter.setData(it!!)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        }


        view.floatingActionButton2.setOnClickListener {
            myCookingRecordViewModel.readAllData.observe(viewLifecycleOwner, Observer {

                val size = it.size
                for (i in 0 until size) {
                    val myComment = it[i].myComment
                    val myImageUrl = it[i].myImageUrl
                    val myRecipeType = it[i].myRecipeType
                    val myRecordedAt = it[i].myRecordedAt

                    val myCookingRecord = MyCookingRecord(
                        0,
                        myComment,
                        myImageUrl,
                        myRecipeType,
                        myRecordedAt
                    )
                    if (myRecipeType == "main_dish") {
                        selectRecipeList = selectRecipeList + myCookingRecord
                        Log.d("ListFragment", " RecipeTypeを絞ったよ")
                    }
                }
                listAdapter.setData(selectRecipeList)
            })



        }




        return view
    }

}