package com.mercadopago.mpos.fcu.data.models

import com.google.gson.annotations.SerializedName

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
