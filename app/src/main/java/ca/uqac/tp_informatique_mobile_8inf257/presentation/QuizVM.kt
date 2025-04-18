package ca.uqac.tp_informatique_mobile_8inf257.presentation

import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Answer
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Question
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuizWithQuestionsAndAnswers
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.QuestionWithAnswers
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.Quiz
import kotlin.random.Random

// ViewModel pour le Quiz avec les Questions et Réponses
data class QuizVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val questions: List<QuestionWithAnswersVM> = emptyList()
) {
    companion object {
        fun fromEntity(entity: QuizWithQuestionsAndAnswers): QuizVM {
            return QuizVM(
                id = entity.quiz.id,
                title = entity.quiz.title,
                questions = entity.questions.map { questionWithAnswers ->
                    QuestionWithAnswersVM(
                        questionId = questionWithAnswers.question.id,
                        text = questionWithAnswers.question.text,
                        correctAnswerIndex = questionWithAnswers.question.correctAnswerIndex,
                        answers = questionWithAnswers.answers.map { answer ->
                            AnswerVM.fromEntity(answer)
                        }
                    )
                }
            )
        }
    }

}

fun QuizVM.toEntityFull(): QuizWithQuestionsAndAnswers {
    return QuizWithQuestionsAndAnswers(
        quiz = Quiz(id = this.id, title = this.title),
        questions = this.questions.map { questionVM ->
            val questionEntity = Question(
                id = questionVM.questionId,
                quizId = this.id,
                text = questionVM.text,
                correctAnswerIndex = questionVM.correctAnswerIndex
            )

            val answerEntities = questionVM.answers.mapIndexed { index, answerVM ->
                Answer(
                    id = answerVM.id,
                    questionId = questionEntity.id,
                    text = answerVM.text,
                    isCorrect = index == questionVM.correctAnswerIndex
                )
            }

            QuestionWithAnswers(
                question = questionEntity,
                answers = answerEntities
            )
        }
    )
}

// ViewModel pour une Question avec ses réponses
data class QuestionWithAnswersVM(
    val questionId: Int = 0,
    val text: String = "",
    val answers: List<AnswerVM> = emptyList(),
    var correctAnswerIndex: Int = -1
)

// ViewModel pour une Réponse
data class AnswerVM(
    val id: Int = 0,
    val text: String = "",
    val isCorrect: Boolean = false
) {
    companion object {
        fun fromEntity(entity: Answer): AnswerVM {
            return AnswerVM(
                id = entity.id,
                text = entity.text,
                isCorrect = entity.isCorrect
            )
        }
    }
}

fun AnswerVM.toEntity(questionId: Int): Answer {
    return Answer(
        id = this.id,
        questionId = questionId,
        text = this.text,
        isCorrect = this.isCorrect
    )
}

fun QuizVM.toEntity() : Quiz {
    return Quiz(
        id = this.id,
        title = this.title
    )
}
