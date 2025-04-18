package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets

import ca.uqac.tp_informatique_mobile_8inf257.domain.FakeDatabaseCheatSheets
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteCheatSheetUseCaseTest{
    private lateinit var deleteCheatSheetUseCase: DeleteCheatSheetUseCase
    private val fakeDao = FakeDatabaseCheatSheets()

    @Before
    fun setUp() {
        deleteCheatSheetUseCase = DeleteCheatSheetUseCase(fakeDao)

        runBlocking {
            fakeDao.upsertCheatSheet(
                CheatSheet(
                    id = 1,
                    title = "Fiche Java",
                    description = "Les bases de Java",
                    document = "test.jpg"
                )
            )
        }
    }

    @Test
    fun `should delete existing cheat sheet`() = runBlocking {
        val cheatSheet = fakeDao.getCheatSheet(1)
        assertNotNull(cheatSheet)

        deleteCheatSheetUseCase(cheatSheet!!)

        val cheatSheets = fakeDao.getCheatSheets().first()
        assertTrue(cheatSheets.isEmpty())
    }

    @Test
    fun `should do nothing if cheat sheet does not exist`() = runBlocking {
        val nonExisting = CheatSheet(
            id = 99,
            title = "Inexistant",
            description = "Pas là",
            document = "N/A"
        )

        deleteCheatSheetUseCase(nonExisting)

        val cheatSheets = fakeDao.getCheatSheets().first()
        assertEquals(1, cheatSheets.size) // celle ajoutée dans setUp
    }
}