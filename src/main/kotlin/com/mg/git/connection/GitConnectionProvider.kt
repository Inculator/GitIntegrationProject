package com.mg.git.connection

import org.gitlab.api.GitlabAPI

object GitConnectionProvider {
    lateinit var gitlabAPI: GitlabAPI
    @JvmField
    var projectId = 0

    fun createGitConnectionInstance(hostUrl: String, token: String) {
        gitlabAPI = GitlabAPI.connect(hostUrl, token)
    }

    fun fetchProjectId (nameSpace: String, projectName: String) {
        projectId = gitlabAPI.getProject(nameSpace, projectName).id
    }
}