package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDao
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Answer
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Question
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers

class UpsertQuizUseCase(private val quizDao: QuizzesDao) {
    suspend operator fun invoke(quizWithQuestionsAndAnswers: QuizWithQuestionsAndAnswers) {
        quizDao.upsertQuizWithQuestionsAndAnswers(quizWithQuestionsAndAnswers)
    }
}

