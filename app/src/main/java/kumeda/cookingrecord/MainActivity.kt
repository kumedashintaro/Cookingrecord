package kumeda.cookingrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                //            Log.d("Response", response.cooking_records.toString())
//            Log.d("Response", response.pagination.toString())

                Log.d("Response", response.body()!!.cooking_records[0].comment)
                Log.d("Response", response.body()!!.cooking_records[0].image_url)
                Log.d("Response", response.body()!!.pagination.limit.toString())
                Log.d("Response", response.body()!!.pagination.offset.toString())
                Log.d("Response", response.body()!!.pagination.total.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}