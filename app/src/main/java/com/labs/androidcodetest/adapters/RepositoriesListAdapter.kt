package com.labs.androidcodetest.adapters


import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.labs.androidcodetest.R
import com.labs.androidcodetest.activities.RepoDetailActivity
import com.labs.androidcodetest.network.models.Item
import kotlinx.android.synthetic.main.item_viewholder.view.*


class RepositoriesListAdapter(var repositories: List<Item>?) : RecyclerView.Adapter<RepositoriesListAdapter.ViewHolder>() {

    override fun getItemCount() = repositories?.size?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val repo = repositories?.get(position)

        holder.bindRepo(repo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_viewholder, parent, false)

        return ViewHolder(inflatedView)
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v

        private var repo: Item? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val i = Intent(itemView.context, RepoDetailActivity::class.java)
            i.putExtra("item", repo)
            i.putExtra("url", repo?.owner?.avatarUrl)
            itemView.context.startActivity(i)
        }

        fun bindRepo(repo: Item?) {
            this.repo = repo
            repo?.let {
                Glide.with(view).load(it.owner?.avatarUrl).into(view.owner_avatar)
                view.repo_title.text = it.name
                view.repo_description.text = it.fullName
                view.repo_watchers.text = " " + String.format("%,d", it?.forksCount)
                view.repo_language.text = it.language
            }
        }
    }
}