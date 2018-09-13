package com.labs.androidcodetest.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.labs.androidcodetest.R
import com.labs.androidcodetest.adapters.RepositoriesListAdapter
import com.labs.androidcodetest.network.retrofit.Status
import com.labs.androidcodetest.network.models.Item
import com.labs.androidcodetest.viewmodel.TrendingRepositoriesViewModel
import demo.com.androidapp.data.remote.GitHubService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var layoutManager: LinearLayoutManager? = null

    private var adapter: RepositoriesListAdapter? = null
    private lateinit var viewModel: TrendingRepositoriesViewModel

    private var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = TrendingRepositoriesViewModel(GitHubService.create())
        viewModel.init(
                query = "android",
                page = 1,
                sort = "forks",
                order = "desc",
                perPage = 10
        )

        viewModel.repositories.observe(this, Observer { repoResource ->
            when (repoResource?.status) {
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressbar.visibility = View.GONE
                    showErrorDialog(repoResource.message)
                }
                Status.SUCCESS -> {
                    progressbar.visibility = View.GONE
                    setRepositoryList(repoResource.data)
                }
            }
        })
    }

    private fun setRepositoryList(data: List<Item>?) {

        adapter?.let{
            it.notifyDataSetChanged()
        } ?: run {
            adapter  = RepositoriesListAdapter(data)
            layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            setPagination()
        }

    }

    private fun setPagination() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView?.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    val pageNumber = viewModel.pageNumber.value?: 0
                    viewModel.pageNumber.postValue(pageNumber.plus(1))
                }
            }
        })
    }

    private val lastVisibleItemPosition: Int
        get() = layoutManager!!.findLastVisibleItemPosition()


    private fun showErrorDialog(message: String?) {
        message?.let{
            Snackbar.make(parentPanel, it, Snackbar.LENGTH_LONG).show()
        }
    }
}
