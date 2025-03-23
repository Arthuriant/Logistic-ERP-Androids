package andika.polman.logisticerp2.model
import andika.polman.logisticerp2.model.User

data class LoginResponse(
    val status: String,
    val message: String,
    val user: User
)
