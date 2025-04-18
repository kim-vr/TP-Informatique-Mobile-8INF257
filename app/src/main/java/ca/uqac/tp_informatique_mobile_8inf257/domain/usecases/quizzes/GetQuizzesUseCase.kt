package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers
import kotlinx.coroutines.flow.Flow

class GetQuizzesUseCase(private val quizDao: QuizzesDao) {
    operator fun invoke(): Flow<List<QuizWithQuestionsAndAnswers>> {
        return quizDao.getQuizzesWithQuestionsAndAnswers()
    }
}
