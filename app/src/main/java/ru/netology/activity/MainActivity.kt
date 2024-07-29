package ru.netology.activity

import OnInteractionListener
import PostAdapter
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.PostViewModel

import ru.netology.databinding.ActivityMainBinding
import ru.netology.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostContract) { result ->
            result?.let {
                viewModel.changeContent(it)
                viewModel.save()
            }
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}