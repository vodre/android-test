package demo.com.androidapp.data.remote

import com.labs.androidcodetest.network.models.Repo
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubService {

    @GET("search/repositories")
    fun search(@Query("q") query: String = "android",
                           @Query("page") page: Int,
                           @Query("sort") sort: String,
                           @Query("order") order: String,
                           @Query("per_page") perPage: Int = 5): Call<Repo>


    companion object Factory {

        protected const val HTTPS_API_GITHUB_URL: String = "https://api.github.com/"

        fun create(): GitHubService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(HTTPS_API_GITHUB_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}


