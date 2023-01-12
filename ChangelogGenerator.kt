package com.mercadopago.mpos.fcu

import com.mercadopago.mpos.fcu.data.api.RepoName.MPOS_ANDROID
import com.mercadopago.mpos.fcu.data.repository.GitHubRepository
import com.mercadopago.mpos.fcu.utils.printChangelog

/**
 * Created by MafeMeli.
 * This file takes the response with a list of merged PR's from github api.
 * @See (https://docs.github.com/es/rest/pulls/pulls?apiVersion=2022-11-28#list-pull-requests for more information.)
 * Follow the next steps to generate the changelog:
 * 1. Go to utils/Constants.kt and paste the API key
 *      generate GITHUB token:
 *          https://docs.github.com/es/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token
 *      Note: Make sure to check: repo, admin:org->read, admin:public_key
 * 2. Replace the MAJOR, MINOR and MAX_PATH for the list Milestones.
 *      Example: MAJOR=5, MINOR=0 and MAX_PATCH=3, generates the changelo for: 5.0.0, 5.0.1, 5.0.2
 * 3. Use the green arrow near to the main function to execute the code
 * 4. Get the generated changelog from the console.
 */

private const val MAJOR = 5
private const val MINOR = 0
private const val MAX_PATCH = 10
private val patch = (0..MAX_PATCH)

fun main() {

    val milestone = patch.map { "$MAJOR.$MINOR.$it" }

    GitHubRepository().getPullRequestList(MPOS_ANDROID) { pullRequests ->
        milestone.forEach { pullRequests.printChangelog(it) }
    }
}
