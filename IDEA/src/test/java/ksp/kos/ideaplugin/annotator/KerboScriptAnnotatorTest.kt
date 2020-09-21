package ksp.kos.ideaplugin.annotator

import ksp.kos.ideaplugin.KerboScriptPlatformTestBase


internal class KerboScriptAnnotatorTest : KerboScriptPlatformTestBase() {
    override val subdir = "AnnotatorUndefinedVariableErrors"

    fun testAnnotator() {
        myFixture.configureByFiles(
            "Test.ks",
            "Imported1.ks",
            "Imported2.ks",
            "Imported3.ks",
            "Imported4.ks",
            "Imported5.ks",
            "Imported6.ks",
        )
        myFixture.checkHighlighting()
    }
}
