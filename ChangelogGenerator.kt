package com.mercadopago.mpos.fcu

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Date

/**
 * Created by MafeMeli.
 * This file takes a json response with a list of merged PR's from github api.
 * @See (https://docs.github.com/es/rest/pulls/pulls?apiVersion=2022-11-28#list-pull-requests for more information.)
 * Follow the next steps to generate the changelog:
 * 1. Use postman to run curl from the page above.
 *  url: https://api.github.com/repos/Mercadolibre/fury_flow-mpos-android/pulls?state=closed&page=1&per_page=50&sort=updated&direction=desc
 * 2. Get the response and replace the content in the file pr.json
 * 3. Replace de changelogMilestone value
 * 4. Use the green arrow to execute the code.
 * 5. Get the generated changelog form the console.
 */

private const val changelogMilestone = "5.0.0"
fun main() {
    val json = File("pr.json").readText(Charsets.UTF_8)

    val pullRequests = json.toListOfPullRequests()

    val fixes = pullRequests.filter { it isFixInMilestone changelogMilestone }
        .getListOfPullRequests()
    val features = pullRequests.filter { it isFeatureInMilestone changelogMilestone }
        .getListOfPullRequests()

    printListOfPullRequestsOnConsole(changelogMilestone, features, fixes)
}

fun String.toListOfPullRequests(): List<PullRequest> =
    Gson().fromJson(this, object : TypeToken<List<PullRequest>>() {}.type)

infix fun PullRequest.isFixInMilestone(milestone: String) =
    this.milestone?.title == milestone && this.mergedAt != null && this.branchInfo.label.contains("fix")

infix fun PullRequest.isFeatureInMilestone(milestone: String) =
    this.milestone?.title == milestone && this.mergedAt != null && !this.branchInfo.label.contains("fix")

fun List<PullRequest>.getListOfPullRequests() = this.map {
    " - ${it.title} [#${it.number}](${it.url})"
}

fun printListOfPullRequestsOnConsole(milestone: String, features: List<String>, fixes: List<String>) {
    val githubUrl = "https://github.com/mercadolibre/fury_flow-mpos-android/tree/"
    println()
    println("## [$milestone]($githubUrl$milestone) ${Date()}")
    println("\n## Added")
    features.printOnConsole()
    println("\n## Fixed")
    fixes.printOnConsole()
}

fun List<String>.printOnConsole() {
    for (feature in this)
        println(feature)
}

data class PullRequest(
    @SerializedName("merged_at")
    val mergedAt: String?,
    val milestone: Milestone?,
    @SerializedName("head")
    val branchInfo: BranchInfo,
    val title: String?,
    val number: String,
    @SerializedName("html_url")
    val url: String
)

data class BranchInfo(
    val label: String
)

data class Milestone(val title: String?)
