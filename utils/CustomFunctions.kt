package com.mercadopago.mpos.fcu.utils

import com.mercadopago.mpos.fcu.data.models.PullRequest
import com.mercadopago.mpos.fcu.utils.Constants.RESPONSE_OK
import java.util.Date
import retrofit2.Response

fun List<PullRequest>.printChangelog(milestone: String) {
    val fixes = this.filter { it isFixInMilestone milestone }
        .getListOfPullRequests()
    val features = this.filter { it isFeatureInMilestone milestone }
        .getListOfPullRequests()
    printListOfPullRequestsOnConsole(milestone, features, fixes)
}

infix fun PullRequest.isFixInMilestone(milestone: String) =
    this.milestone?.title == milestone && this.mergedAt != null && this.branchInfo.label.contains("fix")

infix fun PullRequest.isFeatureInMilestone(milestone: String) =
    this.milestone?.title == milestone && this.mergedAt != null && !this.branchInfo.label.contains("fix")

fun List<PullRequest>.getListOfPullRequests() = this.map {
    " - ${it.title} [#${it.number}](${it.url})"
}

fun List<String>.printOnConsole(isFeature: Boolean = true) {
    if (this.isNotEmpty()) {
        println("\n## ${isFeature.getSubtitle()}")
        for (feature in this)
            println(feature)
    }
}

fun Boolean.getSubtitle(): String = if (this) "Feature" else "Fix"

fun printListOfPullRequestsOnConsole(milestone: String, features: List<String>, fixes: List<String>) {
    if (features.isNotEmpty() || fixes.isNotEmpty()) {
        val githubUrl = "https://github.com/mercadolibre/fury_flow-mpos-android/tree/"
        println("\n\n## [$milestone]($githubUrl$milestone) ${Date()}")
        features.printOnConsole(isFeature = true)
        fixes.printOnConsole(isFeature = false)
    } else {
        println("\n**** Nothing to show for milestone: $milestone")
    }
}

fun Response<List<PullRequest>>.handleCodes(): List<PullRequest>? {
    return when(code()){
        RESPONSE_OK->  body()
        else-> {
            println("Error Code: ${code()}")
            println(errorBody()?.string())
            null
        }
    }
}
