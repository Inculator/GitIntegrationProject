package com.mg.git.connection

import com.mg.git.connection.MakeGitConnection

fun getProjectId (nameSpace: String, projectName: String): Int {
    return MakeGitConnection.gitlabAPI.getProject(nameSpace, projectName).id
}