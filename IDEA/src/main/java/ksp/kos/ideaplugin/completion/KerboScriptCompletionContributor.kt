package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import ksp.kos.ideaplugin.KerboScriptLanguage
import ksp.kos.ideaplugin.psi.KerboScriptTypes

class KerboScriptCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(KerboScriptTypes.IDENTIFIER).withLanguage(KerboScriptLanguage.INSTANCE),
            KerboScriptIdentifierCompletionProvider(),
        )
    }
}
