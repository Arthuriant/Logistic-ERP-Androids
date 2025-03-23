package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("nama") val nama: String,
    @SerializedName("email") val email: String,
    @SerializedName("password_hash") val password_hash: String,
    @SerializedName("role") val role: Int,
    @SerializedName("created_at") val created_at: String,
)
