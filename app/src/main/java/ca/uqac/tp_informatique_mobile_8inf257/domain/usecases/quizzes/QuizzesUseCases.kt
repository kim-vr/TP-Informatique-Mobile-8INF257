package ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes

data class QuizzesUseCases (
    val getQuizzes : GetQuizzesUseCase,
    val getQuiz: GetQuizUseCase,
    val upsertQuiz: UpsertQuizUseCase,
    val deleteQuiz: DeleteQuizUseCase
)