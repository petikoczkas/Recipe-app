package hu.bme.aut.receptek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.receptek.databinding.ActivityDescriptionBinding
import hu.bme.aut.receptek.databinding.ActivityDetailsBinding

class DescriptionActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.tvDesc.text= intent.extras?.get("DESC").toString()

    }

}