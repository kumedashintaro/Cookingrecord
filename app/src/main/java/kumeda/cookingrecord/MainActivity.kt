package kumeda.cookingrecord

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kumeda.cookingrecord.adapter.ListAdapter
import kumeda.cookingrecord.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val listAdapter by lazy { ListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

//        viewModel.getPost()

        val offset = "6"
        val limit = "6"

        viewModel.getPostSelect(Integer.parseInt(offset), Integer.parseInt(limit))

        viewModel.myResponseSelect.observe(this, Observer { response ->
            if (response.isSuccessful) {
                // TODO body()がnullだった場合の処理 or nullにしないようにする

                response.body()?.cooking_records.let { listAdapter.setData(it!!) }

            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }

    private fun setupRecyclerview() {
        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = layoutManager
    }
}