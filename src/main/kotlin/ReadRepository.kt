import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

fun readRepository(path: String) : Repository {
    val repositoryBuilder = FileRepositoryBuilder()
    return repositoryBuilder.setGitDir(
        File(path))
        .readEnvironment() // scan environment GIT_* variables
        .findGitDir() // scan up the file system tree
        .setMustExist(true)
        .build()
}