package com.mg.mergerequest

import com.mg.git.connection.MakeGitConnection
import com.mg.git.connection.getProjectId
import org.junit.jupiter.api.BeforeAll

open class GitTestCommons {
    companion object {
        var projectId : Int = 0
        @BeforeAll
        @JvmStatic
        fun beforeAllTest() {
            MakeGitConnection.makeConnectionToGit()
            projectId = getProjectId()
        }
    }
}
