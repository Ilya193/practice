package com.example.studying.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.R
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.PostLayoutBinding
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()
    private val adapter = PostsAdapter({mainViewModel.delete(it)}, {mainViewModel.setFavorite(it)})

    /*private val adapter: SimpleBindingAdapter<PostUi.Success> by lazy {
        simpleAdapter<PostUi.Success, PostLayoutBinding> {
            areItemsSame = { old, new ->
                old.same(new)
            }
            areContentsSame = { old, new ->
                old.sameContent(new)
            }
            changePayload = { old, new ->
                old.changePayload(new)
            }

            bindWithPayloads { item, payloads ->
                fun bindFavorite() {
                    if (item.isFavorite)
                        icFavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
                    else
                        icFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24)
                }

                if (payloads.isEmpty()) {
                    tvTitle.text = item.title
                    root.startAnimation(
                        AnimationUtils.loadAnimation(
                            root.context,
                            R.anim.main_anim
                        )
                    )
                    bindFavorite()
                } else if (payloads[0] == true) {
                    bindFavorite()
                }
            }

            listeners {
                root.onClick {
                    mainViewModel.delete(it)
                }
                icFavorite.onClick {
                    mainViewModel.setFavorite(it)
                }
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        );

        binding.btnAdd.setOnClickListener {
            mainViewModel.add()
        }

        binding.rvPosts.adapter = adapter
        binding.rvPosts.setHasFixedSize(true)
        binding.rvPosts.itemAnimator = ScaleInBottomAnimator()

        mainViewModel.fetchPosts()

        mainViewModel.uiState.observe(this) { state ->
            when (state) {
                is PostUiState.Success -> showPosts(state.data)
                is PostUiState.Error -> showError(state.message)
                is PostUiState.Loading -> showLoading()
            }
        }
    }

    private fun showPosts(data: List<PostUi.Success>) {
        binding.progressBar.visibility = View.GONE
        binding.containerError.visibility = View.GONE
        binding.rvPosts.visibility = View.VISIBLE

        adapter.submitList(data)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.rvPosts.visibility = View.GONE
        binding.containerError.visibility = View.VISIBLE

        binding.tvError.text = message
        binding.btnRetry.setOnClickListener {
            mainViewModel.fetchPosts()
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }
}