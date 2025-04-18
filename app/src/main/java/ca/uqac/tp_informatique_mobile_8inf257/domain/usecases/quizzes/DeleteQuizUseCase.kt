package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz

class DeleteQuizUseCase(private val quizDao: QuizzesDao) {
    suspend operator fun invoke(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }
}