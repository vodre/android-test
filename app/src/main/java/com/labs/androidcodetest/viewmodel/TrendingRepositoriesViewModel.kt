package com.labs.androidcodetest.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.Transformations
import com.labs.androidcodetest.network.retrofit.Resource
import com.labs.androidcodetest.network.retrofit.Status
import com.labs.androidcodetest.network.models.Item
import com.labs.androidcodetest.network.models.Repo
import demo.com.androidapp.data.remote.GitHubService
import demo.com.androidapp.data.remote.SearchRepository
import retrofit2.Response

class TrendingRepositoriesViewModel (private val service: GitHubService?) : ViewModel() {

    val pageNumber = MutableLiveData<Int>()

    lateinit var apiResponse: LiveData<Resource<Response<Repo>>>
    lateinit var repositories: LiveData<Resource<out List<Item>>>


    fun init(
            query : String,
            page : Int,
            sort: String,
            order: String,
            perPage: Int
    ){

        apiResponse = Transformations.switchMap(pageNumber) { pn ->
            SearchRepository(service).getTrending(query, pn, sort, order, perPage)
        }

        repositories = Transformations.map(apiResponse) { input ->
            when(input?.status){
                Status.LOADING -> Resource.loading(null)
                Status.ERROR -> Resource.error(input.message?: "Unable to obtain repositories")
                Status.SUCCESS -> Resource.success(input.data?.body()?.items)
                else -> null
            }
        }


        pageNumber.postValue(page)
    }
}