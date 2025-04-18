package ca.uqac.tp_informatique_mobile_8inf257.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = Quiz::class,
        parentColumns = ["id"],
        childColumns = ["quizId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizId: Int,
    val text: String,
    val correctAnswerIndex: Int
)

@Entity(
    tableName = "answers",
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["id"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Answer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: Int,
    val text: String,
    val isCorrect: Boolean
)

data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<Answer>
)

data class QuizWithQuestionsAndAnswers(
    @Embedded val quiz: Quiz,
    @Relation(
        parentColumn = "id",
        entityColumn = "quizId",
        entity = Question::class
    )
    val questions: List<QuestionWithAnswers>
)






