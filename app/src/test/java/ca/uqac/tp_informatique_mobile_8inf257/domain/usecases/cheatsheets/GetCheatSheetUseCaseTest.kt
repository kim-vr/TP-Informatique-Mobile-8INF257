package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseCheatSheets
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCheatSheetUseCaseTest {
    private lateinit var getCheatSheetUseCase: GetCheatSheetUseCase
    private val fakeDao = FakeDatabaseCheatSheets()

    @Before
    fun setUp() = runBlocking {
        getCheatSheetUseCase = GetCheatSheetUseCase(fakeDao)

        fakeDao.upsertCheatSheet(
            CheatSheet(
                id = 1,
                title = "Kotlin",
                description = "Syntaxe de base",
                document = "kotlin_basics.png"
            )
        )

        fakeDao.upsertCheatSheet(
            CheatSheet(
                id = 2,
                title = "SQL",
                description = "Requêtes basiques",
                document = "sql_queries.png"
            )
        )
    }

    @Test
    fun `should return cheat sheet by id`() = runBlocking {
        val cheatSheet = getCheatSheetUseCase(1)

        assertNotNull(cheatSheet)
        assertEquals(1, cheatSheet?.id)
        assertEquals("Kotlin", cheatSheet?.title)
        assertEquals("kotlin_basics.png", cheatSheet?.document)
    }

    @Test
    fun `should return null when cheat sheet does not exist`() = runBlocking {
        val cheatSheet = getCheatSheetUseCase(999)

        assertNull(cheatSheet)
    }
}