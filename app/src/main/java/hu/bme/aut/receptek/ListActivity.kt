package hu.bme.aut.receptek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import hu.bme.aut.receptek.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    companion object {
        const val TYPE_ELOETEL = 1
        const val TYPE_FOETEL = 2
        const val TYPE_DESSZERT = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnEloetel.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.KEY_ETEL_TYPE, TYPE_ELOETEL)
            startActivity(intent)
        }

        binding.btnFoetel.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.KEY_ETEL_TYPE, TYPE_FOETEL)
            startActivity(intent)
        }

        binding.btnDesszert.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.KEY_ETEL_TYPE, TYPE_DESSZERT)
            startActivity(intent)
        }
    }
}