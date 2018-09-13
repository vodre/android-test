package demo.com.androidapp.data.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.labs.androidcodetest.network.retrofit.Resource
import com.labs.androidcodetest.network.models.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchRepository(private val api: GitHubService?){

     fun getTrending(query: String, page: Int, sort: String, order: String, perPage: Int): LiveData<Resource<Response<Repo>>> {
         val liveData: MutableLiveData<Resource<Response<Repo>>> = MutableLiveData()

         kotlin.run{
             api?.search(query,page,sort,order,perPage)?.enqueue(object : Callback<Repo> {
                 override fun onFailure(call: Call<Repo>?, t: Throwable?) {
                     liveData.postValue(Resource.error(t.toString()))
                 }

                 override fun onResponse(call: Call<Repo>?, response: Response<Repo>?) {
                     if (response != null) {
                         if (response.isSuccessful){
                             liveData.postValue(Resource.success(response))
                         }else{
                             liveData.postValue(Resource.error(response.message()))
                         }
                     }else{
                         liveData.postValue(Resource.error("Failed to read data, try again later"))
                     }
                 }
             })
         }

         return liveData
    }


}


