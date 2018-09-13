package com.labs.androidcodetest.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.labs.androidcodetest.R
import com.labs.androidcodetest.network.models.Item
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.details_holder.*
import kotlinx.android.synthetic.main.item_viewholder.view.*


class RepoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        val item : Item? = intent?.extras?.getParcelable("item")
        val url : String? = intent?.extras?.getString("url")
        item?.let{
            bindData(it, url)
        }
    }


    private fun bindData(item: Item?, url: String?) {

        if (item == null) return

        supportActionBar?.title = item.name

        Glide.with(avatar_owner).load(url).into(avatar_owner)

        repo_watchers.text = getString(R.string.watchers).plus(item.watchers.toString())
        repo_stars.text = getString(R.string.score).plus(item.score.toString())
        repo_forks.text = getString(R.string.forks).plus(item.forksCount.toString())
        repo_open_issues.text = getString(R.string.issues).plus(item.openIssues.toString())
        repo_language.text = getString(R.string.language).plus(item.language.toString())


        fab.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
