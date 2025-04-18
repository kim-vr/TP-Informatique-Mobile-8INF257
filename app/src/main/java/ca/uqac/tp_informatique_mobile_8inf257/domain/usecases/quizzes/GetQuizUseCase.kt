package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers

class GetQuizUseCase(private val quizDao: QuizzesDao) {
    suspend operator fun invoke(quizId: Int): QuizWithQuestionsAndAnswers? {
        return quizDao.getQuizWithQuestionsAndAnswers(quizId)
    }
}
