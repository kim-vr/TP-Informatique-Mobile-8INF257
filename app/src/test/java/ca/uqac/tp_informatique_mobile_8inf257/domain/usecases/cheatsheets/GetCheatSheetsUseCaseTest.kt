package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseCheatSheets
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCheatSheetsUseCaseTest {
    private lateinit var getCheatSheetsUseCase: GetCheatSheetsUseCase
    private val fakeDao = FakeDatabaseCheatSheets()

    @Before
    fun setUp() = runBlocking {
        getCheatSheetsUseCase = GetCheatSheetsUseCase(fakeDao)

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
    fun `should return all cheat sheets from dao`() = runBlocking {
        val cheatSheets = getCheatSheetsUseCase().first()

        assertEquals(2, cheatSheets.size)
        assertTrue(cheatSheets.any { it.title == "Kotlin" && it.document == "kotlin_basics.png" })
        assertTrue(cheatSheets.any { it.title == "SQL" && it.document == "sql_queries.png" })
    }

    @Test
    fun `should return empty list when no cheat sheets exist`() = runBlocking {
        fakeDao.cheatSheets.clear()

        val cheatSheets = getCheatSheetsUseCase().first()
        assertTrue(cheatSheets.isEmpty())
    }
}