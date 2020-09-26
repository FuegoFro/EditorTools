package ksp.kos.ideaplugin.completion

import com.intellij.codeInsight.completion.CompletionType
import ksp.kos.ideaplugin.KerboScriptPlatformTestBase

class KerboScriptCompletionContributorTest : KerboScriptPlatformTestBase() {
    override val subdir = "CompletionContributor"

    fun testCompletionSuggestsVariablesInScope() {
        myFixture.configureByFiles(
            "TestScope.ks",
            "Imported1.ks",
            "Imported2.ks",
            "Imported3.ks",
            "Imported4.ks",
            "Imported5.ks",
            "Imported6.ks",
        )
        myFixture.complete(CompletionType.BASIC, 1)
        // Sort for easier comparison in case of errors
        val strings = myFixture.lookupElementStrings!!.sorted()

        assertContainsElements(
            strings,
            listOf(
                // "global_top_level_a", // TODO - Make lazyglobal work
                "global_top_level_b",
                "global_top_level_c",
                "global_top_level_d",
                "global_top_level_e",
                "local_top_level_a",
                "local_top_level_b",
                "lock_a",
                "imported_global_1",
                "imported_global_3",
                "imported_global_5",
                "imported_global_6",
                "imported_global_func",
                "imported_inner_global",
                "func_a",
                "func_b",
                // "global_in_func_a", // TODO - Make lazyglobal work
                "global_in_func_b",
                "global_in_func_c",
                "global_in_func_d",
                "global_in_func_e",
                "func_global_in_func",
                "lock_default_visibility_in_func",
                "lock_global_in_func",
                "param_a",
                "param_b",
                "param_c",
                "local_a",
                "local_b",
                "local_c",
                "local_d",
                "local_e",
                "local_f",
                "for_var",
                "from_var",
                "step_var",
                "global_after_caret",
                "local_after_function",
                "global_func_after",
                // Some builtin globals, should keep capitalization
                "time",
                "SHIP",
                "Mun",
            ).sorted()
        )

        assertDoesntContain(
            strings,
            listOf(
                // Should not be visible
                "imported_local",
                "imported_local_func",
                // TODO - Handle function versions of RUNPATH(...) and RUNONCEPATH(...), at least for string literal arguments
                "Imported2",
                "Imported4",
                "param_should_not_show_up_1",
                "param_should_not_show_up_2",
                "local_should_not_show_up",
                "func_default_visibility_should_not_show_up",
                "func_local_should_not_show_up",
                "lock_local_should_not_show_up",
                "local_after_caret_should_not_show_up",
                "local_after_block_should_not_show_up",
                // Different capitalizations
                "TIME",
                "mun",
                // Complete garbage names
                "foobar",
                "NEXTNODEE",
            )
        )
    }

    fun testPartialNamesAreCaseInsensitive() {
        TODO("Write this test")
    }
}
