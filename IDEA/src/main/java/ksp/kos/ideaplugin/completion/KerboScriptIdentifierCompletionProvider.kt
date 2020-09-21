package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.parentsOfType
import com.intellij.util.ProcessingContext
import ksp.kos.ideaplugin.KerboScriptFile
import ksp.kos.ideaplugin.getBuiltinKsFile
import ksp.kos.ideaplugin.psi.KerboScriptScope
import ksp.kos.ideaplugin.psi.isDeclarationVisibleToUsage
import ksp.kos.ideaplugin.reference.OccurrenceType
import ksp.kos.ideaplugin.reference.PsiFileResolver
import ksp.kos.ideaplugin.reference.ReferableType

class KerboScriptIdentifierCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        val inFileDeclarations = parameters.position.originalElement
            .parentsOfType<KerboScriptScope>()
            .flatMap { it.cachedScope.getDeclarations(ReferableType.VARIABLE).values }
            .filter { declaration -> isDeclarationVisibleToUsage(parameters.position.originalElement, declaration.syntax) }

        val containingFile = parameters.position.originalElement.containingFile as KerboScriptFile
        val fileResolver = PsiFileResolver(containingFile)
        val importedDeclaration = containingFile.semantics.imports.values
            .mapNotNull { fileReference -> fileResolver.resolveFile(fileReference.name) }
            .flatMap { file -> file.semantics.getDeclarations(ReferableType.VARIABLE).values }
        val builtinDeclaration = getBuiltinKsFile()?.semantics
            ?.getDeclarations(ReferableType.VARIABLE)
            ?.values
            ?: emptyList()
        val externalDeclarations = (importedDeclaration + builtinDeclaration)
            .filter { declarationValue -> declarationValue.syntax?.type?.occurrenceType == OccurrenceType.GLOBAL }

        result.addAllElements(
            (inFileDeclarations + externalDeclarations)
                .map { declaration -> LookupElementBuilder.create(declaration.name) }
                .toList()
        )
    }
}
