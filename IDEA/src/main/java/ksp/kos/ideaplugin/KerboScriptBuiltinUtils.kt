package ksp.kos.ideaplugin

import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiManager
import ksp.kos.ideaplugin.reference.context.FileContext
import java.net.URL

private var ksFile: KerboScriptFile? = null

private fun URL.toVirtualFile(): VirtualFile? {
    val urlStr = VfsUtilCore.convertFromUrl(this)
    return VirtualFileManager.getInstance().findFileByUrl(urlStr)
}

fun getBuiltinKsFile(): KerboScriptFile? {
    if (ksFile == null) {
        val builtinVirtualFile = FileContext.BuiltinResolver::class.java.classLoader
            .getResource("builtin.ks")
            ?.toVirtualFile()
            ?: return null

        val project = ProjectManager.getInstance().defaultProject
        val psiFile = PsiManager.getInstance(project).findFile(builtinVirtualFile)
        ksFile = psiFile as? KerboScriptFile
    }
    return ksFile
}
