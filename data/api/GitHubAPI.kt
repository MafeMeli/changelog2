package com.mercadopago.mpos.fcu.data.api

import com.mercadopago.mpos.fcu.data.models.PullRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GitHubAPI {
    @GET("Mercadolibre/{repo_name}/pulls")
    fun getMposPullRequestList(
        @Path("repo_name") repoName: String = RepoName.MPOS_ANDROID.value,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryMap: Map<String, String>
    ): Call<List<PullRequest>>
}
