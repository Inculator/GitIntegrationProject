package com.mg.mergerequest

import com.mg.git.discussion.gitLabMergeRequestsWithDiscussions
import com.mg.git.merge.getAllMergeRequests
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TestMergeRequestDiscussions : GitTestCommons() {

    @Test
    fun readMergeRequest_shouldReadMergeRequest() {
        var listOfMergeRequest = getAllMergeRequests(projectId)
        var listOfMergeRequestsModels = gitLabMergeRequestsWithDiscussions(listOfMergeRequest.get(0))
        assertNotNull(listOfMergeRequestsModels)
        assertEquals(2, listOfMergeRequestsModels.listOfMRDiscussions.size)
    }
}
