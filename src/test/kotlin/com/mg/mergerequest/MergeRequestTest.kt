package com.mg.mergerequest

import com.mg.git.connection.MakeGitConnection
import com.mg.git.connection.getProjectId
import org.gitlab.api.models.GitlabMergeRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MergeRequestTest {

    @Test
    fun getMergeRequests_shouldGetMergeRequests(){
        MakeGitConnection.makeConnectionToGit()
        val projectId = getProjectId()
        var listOfMergeRequest : List<GitlabMergeRequest> = MakeGitConnection.gitlabAPI.getOpenMergeRequests(projectId)

        assertEquals(1, listOfMergeRequest.size)
    }
}

