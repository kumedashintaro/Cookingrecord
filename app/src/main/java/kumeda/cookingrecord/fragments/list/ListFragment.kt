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
import kumeda.cookingrecord.R
import kumeda.cookingrecord.adapter.ListAdapter
import kumeda.cookingrecord.repository.Repository

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val listAdapter by lazy { ListAdapter() }

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

//        viewModel.getPost()

        val offset = "0"
        val limit = "20"

        viewModel.getPostSelect(Integer.parseInt(offset), Integer.parseInt(limit))

        viewModel.myResponseSelect.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                // TODO body()がnullだった場合の処理 or nullにしないようにする

                response.body()?.cooking_records.let { listAdapter.setData(it!!) }

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