package com.example.storyapps.ui.main



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mycamera.getAddressName
import com.example.storyapps.data.model.ListStory
import com.example.storyapps.databinding.ActivityDetailBinding
import com.example.storyapps.utils.DateFormatter
import com.example.storyapps.utils.DateFormatter.formatDate
import java.util.TimeZone

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }


    private fun getData() {
        val item = intent.getParcelableExtra<ListStory>("detail")

       if(item != null) {
           detail(item)
       }
    }

    private fun detail(data: ListStory) {
        Glide.with(this)
            .load(data.photoUrl)
            .into(binding.ivStory)

        binding.tvTime.text = formatDate(data.createdAt!!, TimeZone.getDefault().id)
        binding.tvUsername.text = data.name
        binding.tvDescription.text = data.description
        binding.tvStreet.text = getAddressName(this@DetailActivity, data.lat, data.lon)
    }
}