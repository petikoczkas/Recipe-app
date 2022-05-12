package hu.bme.aut.receptek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.receptek.adapter.FoodAdapter
import hu.bme.aut.receptek.data.FoodListDatabase
import hu.bme.aut.receptek.data.Item
import hu.bme.aut.receptek.databinding.ActivityDetailsBinding
import hu.bme.aut.receptek.fragments.NewFoodItemDialogFragment
import kotlin.concurrent.thread

class DetailsActivity : AppCompatActivity(), FoodAdapter.ItemClickListener,
    NewFoodItemDialogFragment.NewFoodItemDialogListener{

    companion object {
        const val KEY_ETEL_TYPE = "KEY_ETEL_TYPE"
    }

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var database: FoodListDatabase
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        database = FoodListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewFoodItemDialogFragment().show(
                supportFragmentManager,
                NewFoodItemDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = FoodAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        val etelType = this.intent.getIntExtra(KEY_ETEL_TYPE, -1)
        adapter.etelType = etelType
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.ItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: Item) {
        thread {
            database.ItemDao().update(item)
            Log.d("DetailsActivity", "Item update was successful")
        }
    }

    override fun onItemDeleted(item: Item) {
        thread {
            database.ItemDao().deleteItem(item)

            runOnUiThread {
                adapter.delete(item)
            }
        }
    }



    override fun onItemCreated(newItem: Item) {
        thread {
            val insertId = database.ItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

}