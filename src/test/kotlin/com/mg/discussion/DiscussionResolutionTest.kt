package com.mg.discussion

import com.mg.git.discussion.ResolveMRDiscussionsController
import com.mg.git.merge.getAllMergeRequests
import com.mg.mergerequest.GitTestCommons
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DiscussionResolutionTest : GitTestCommons() {

    private val discussionId = "256862daa56878b47ff8eafc774a2f9bbf0c7926"

    @Test
    @Order(1)
    fun resolveDiscussion_shouldResolveDiscussionThread() {
        val listOfMergeRequest = getAllMergeRequests(projectId)
        val resolveMergeRequestDiscussions = ResolveMRDiscussionsController()
        val result = resolveMergeRequestDiscussions.resolveDiscussion(discussionId, listOfMergeRequest.get(0), true);
        assertEquals(true, result)
    }

    @Test
    @Order(10)
    fun unResolveDiscussion_shouldUnResolveDiscussionThread() {
        val listOfMergeRequest = getAllMergeRequests(projectId)
        val resolveMergeRequestDiscussions = ResolveMRDiscussionsController()
        val result = resolveMergeRequestDiscussions.resolveDiscussion(
            discussionId,
            listOfMergeRequest.get(0),
            false
        );
        assertEquals(false, result)
    }
}
