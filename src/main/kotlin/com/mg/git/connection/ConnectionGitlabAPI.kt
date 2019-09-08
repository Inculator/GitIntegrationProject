package com.mg.git.connection

import com.mg.git.connection.MakeGitConnection

class ConnectionGitlabAPI{
    companion object {
        var projectId = 0;

        fun getProjectId (nameSpace: String, projectName: String): Int {
            projectId = MakeGitConnection.gitlabAPI.getProject(nameSpace, projectName).id
            return projectId
        }
    }
}
