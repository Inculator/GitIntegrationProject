package com.mg.git.connection

import org.gitlab.api.GitlabAPI

object MakeGitConnection {
    lateinit var gitlabAPI: GitlabAPI
    fun makeConnectionToGit() {
        gitlabAPI = GitlabAPI.connect("https://innersource.soprasteria.com/", "${System.getProperty("ENV_GIT_TOKEN")}")
    }
}