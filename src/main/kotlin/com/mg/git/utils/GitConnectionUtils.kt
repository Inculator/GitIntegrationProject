package com.mg.git.utils


import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.*
class GitConnectionUtils {

    companion object {

        @kotlin.jvm.JvmStatic
        var hostURL: String = ""

        fun consultProjectGitURL(url: String?): HostURLModel {
            var url1 = url + File.separator + ".git"
            val files = File(url1).listFiles()
            for (file in files) {
                if (file.endsWith("config")) {
                    val prop = Properties()
                    prop.load(FileInputStream(file.path))
                    hostURL = prop.getProperty("url")
                }
            }
            return retrieveHost()
        }
        
        private fun retrieveHost() : HostURLModel {
            val url = URL(hostURL)
            val pathParam = url.path.split('/')
            val alias = pathParam[1]
            val project = pathParam[2].substring(0, pathParam[2].indexOf('.'))
            return HostURLModel("https://${url.host}/",alias,project)
        }

    }
}
