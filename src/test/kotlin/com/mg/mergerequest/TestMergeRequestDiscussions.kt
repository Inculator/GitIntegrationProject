package com.mg.mergerequest

import com.mg.git.discussion.getMRDiscussions
import com.mg.git.discussion.gitLabMergeRequests
import com.mg.git.merge.MergeRequestModel
import com.mg.git.merge.getMergeRequests
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TestMergeRequestDiscussions : GitTestCommons(){

    @Test
    fun readMergeRequest_shouldReadMergeRequest() {
        var listOfMergeRequest = getMergeRequests(projectId)
        var listOfMergeRequestsModels = gitLabMergeRequests(listOfMergeRequest)
        assertNotNull(listOfMergeRequestsModels)
        assertEquals(2, listOfMergeRequestsModels.size)
    }
}
