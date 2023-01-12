package com.mercadopago.mpos.fcu.data.repository

import com.mercadopago.mpos.fcu.data.api.RepoName
import com.mercadopago.mpos.fcu.data.api.RetrofitInstance
import com.mercadopago.mpos.fcu.data.models.PullRequest
import com.mercadopago.mpos.fcu.utils.Constants.GIT_API_KEY
import com.mercadopago.mpos.fcu.utils.handleCodes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepository {
    private val defaultQueryMap = mapOf(
        "state" to "closed",
        "page" to "1",
        "per_page" to "100",
        "sort" to "updated",
        "direction" to "desc"
    )
    private val defaultHeaderMap = mapOf(
        "Accept" to "application/vnd.github+json",
        "Authorization" to "Bearer $GIT_API_KEY",
        "X-GitHub-Api-Version" to "2022-11-28"
    )

    fun getPullRequestList(repositoryName: RepoName, function: (List<PullRequest>) -> Unit) =
        RetrofitInstance.gitApi.getMposPullRequestList(
            repoName = repositoryName.value,
            headers = defaultHeaderMap,
            queryMap = defaultQueryMap
        ).enqueue(
            object : Callback<List<PullRequest>> {
                override fun onResponse(p0: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {
                    response.handleCodes()?.let { function(it) }
                }

                override fun onFailure(p0: Call<List<PullRequest>>, error: Throwable) {
                    println("Hubo un error llamando a la Api")
                    println(error.message)
                }
            }
        )
}