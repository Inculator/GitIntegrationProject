package com.mg.mergerequest

import com.mg.git.connection.GitConnectionProvider
import org.junit.jupiter.api.BeforeAll

open class GitTestCommons {
    companion object {
        var projectId: Int = 0
        @BeforeAll
        @JvmStatic
        fun beforeAllTest() {
            val hostUrl = "https://innersource.soprasteria.com/"
            val token = System.getenv("ENV_GIT_TOKEN")
            val nameSpace = "t-tool"
            val projectName = "t-tool"
            GitConnectionProvider.getGitConnectionInstance(hostUrl, token)
            GitConnectionProvider.fetchProjectId(nameSpace, projectName)
            projectId = GitConnectionProvider.projectId
        }
    }
}
