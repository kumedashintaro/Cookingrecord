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
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()!!.pagination.limit.toString())
                Log.d("Response", response.body()!!.pagination.offset.toString())
                Log.d("Response", response.body()!!.pagination.total.toString())

                val list = response.body()!!.cooking_records
                list.forEach {
                    Log.d("Response", it.comment)
                    Log.d("Response", it.recorded_at)
//                    Log.d("Response", response.body()!!.cooking_records[0].comment)
//                    Log.d("Response", response.body()!!.cooking_records[0].image_url)
//                    Log.d("Response", response.body()!!.cooking_records[1].comment)
//                    Log.d("Response", response.body()!!.cooking_records[1].image_url)
                }

            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}