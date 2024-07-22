package ru.netology

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.databinding.MainPageLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainPageLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                dateTextView.text = post.published
                contentTextView.text = post.content
                likeCountTextView.text = viewModel.formatCount(post.likeCount)
                shareCountTextView.text = viewModel.formatCount(post.shareCount)
                viewCountTextView.text = viewModel.formatCount(post.viewCount)
                likeImageView.setImageResource(
                    if (post.liked) R.drawable.baseline_favorite_24_filled
                    else R.drawable.baseline_favorite_border_24
                )
            }
        }

        binding.likeImageView.setOnClickListener {
            viewModel.like()
        }

        binding.shareImageView.setOnClickListener {
            viewModel.share()
        }
    }
}