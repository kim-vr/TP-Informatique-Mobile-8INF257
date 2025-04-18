package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseCheatSheets
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import ca.uqac.tp_informatique_mobile_8inf257.utils.CheatSheetException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpsertCheatSheetUseCaseTest {
    private lateinit var upsertCheatSheetUseCase: UpsertCheatSheetUseCase
    private val fakeDao = FakeDatabaseCheatSheets()

    @Before
    fun setUp() {
        upsertCheatSheetUseCase = UpsertCheatSheetUseCase(fakeDao)
    }

    @Test
    fun `should upsert cheat sheet when title and document are valid`() = runBlocking {
        val cheatSheet = CheatSheet(
            id = 1,
            title = "Java",
            description = "Syntaxe de base",
            document = "java_basics.png"
        )

        // Upsert the cheat sheet
        upsertCheatSheetUseCase(cheatSheet)

        val retrievedCheatSheet = fakeDao.getCheatSheet(1)
        assertNotNull(retrievedCheatSheet)
        assertEquals("Java", retrievedCheatSheet?.title)
        assertEquals("java_basics.png", retrievedCheatSheet?.document)
    }

    @Test(expected = CheatSheetException::class)
    fun `should throw CheatSheetException when title is empty`() = runBlocking {
        val cheatSheet = CheatSheet(
            id = 2,
            title = "",
            description = "Requêtes basiques",
            document = "sql_basics.png"
        )

        // Try to upsert a cheat sheet with an empty title
        upsertCheatSheetUseCase(cheatSheet)
    }

    @Test(expected = CheatSheetException::class)
    fun `should throw CheatSheetException when document is empty`() = runBlocking {
        val cheatSheet = CheatSheet(
            id = 3,
            title = "Python",
            description = "Syntaxe de base",
            document = ""
        )

        // Try to upsert a cheat sheet with an empty document
        upsertCheatSheetUseCase(cheatSheet)
    }

    @Test
    fun `should upsert cheat sheet when title and document are valid even if already exists`() = runBlocking {
        val cheatSheet = CheatSheet(
            id = 4,
            title = "JavaScript",
            description = "Syntaxe avancée",
            document = "js_advanced.png"
        )

        // Upsert the cheat sheet for the first time
        upsertCheatSheetUseCase(cheatSheet)

        // Update the cheat sheet with the same ID but different content
        val updatedCheatSheet = CheatSheet(
            id = 4,
            title = "JavaScript (Updated)",
            description = "Syntaxe avancée",
            document = "js_advanced_updated.png"
        )
        upsertCheatSheetUseCase(updatedCheatSheet)

        // Verify the cheat sheet is updated
        val retrievedCheatSheet = fakeDao.getCheatSheet(4)
        assertNotNull(retrievedCheatSheet)
        assertEquals("JavaScript (Updated)", retrievedCheatSheet?.title)
        assertEquals("js_advanced_updated.png", retrievedCheatSheet?.document)
    }
}