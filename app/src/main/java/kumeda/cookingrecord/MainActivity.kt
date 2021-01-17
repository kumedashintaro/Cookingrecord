package kumeda.cookingrecord

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kumeda.cookingrecord.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                Log.d("Response Limit", response.body()!!.pagination.limit.toString())
                Log.d("Response offset", response.body()!!.pagination.offset.toString())
                Log.d("Response total", response.body()!!.pagination.total.toString())
                Log.d("Response", response.body()!!.cooking_records.toString())

                val list = response.body()!!.cooking_records
                list.forEach {
                    Log.d("Response", it.comment)
                    Log.d("Response", it.recorded_at)
                }

            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })


    }
}