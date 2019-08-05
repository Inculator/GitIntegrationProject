package com.mg.git.connection

import com.mg.git.connection.MakeGitConnection

fun getProjectId (): Int {
    return MakeGitConnection.gitlabAPI.getProject("t-tool", "t-tool").id
}