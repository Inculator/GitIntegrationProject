package com.mg.git.merge

import com.mg.mergerequest.GitLabUserNotesModel
import org.gitlab.api.models.GitlabMergeRequest

data class MergeRequestModel(var mergeRequest : GitlabMergeRequest, var listOfMRDiscussions: List<GitLabUserNotesModel>)