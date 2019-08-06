package com.mg.git.connection

import org.gitlab.api.GitlabAPI

object MakeGitConnection {
    lateinit var gitlabAPI: GitlabAPI
    fun getGitConnectionInstance(hostUrl: String, token: String) {
        gitlabAPI = GitlabAPI.connect(hostUrl, token)
    }
}