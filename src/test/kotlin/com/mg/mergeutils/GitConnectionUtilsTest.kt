package com.mg.mergeutils

import com.mg.git.utils.GitConnectionUtils
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GitConnectionUtilsTest {

    @Test
    fun getHostUrl_ShouldGetHostDetails() {
        var hostURLModel = GitConnectionUtils.consultProjectGitURL("E:\\Office_Workspace\\Suits")
        assertEquals(hostURLModel.host ,"https://innersource.soprasteria.com/")
    }
}