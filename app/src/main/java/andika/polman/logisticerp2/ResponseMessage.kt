package andika.polman.logisticerp2

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message") val message: String
)
