package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class Supplier(
    @SerializedName("id_supplier") val id_supplier: Int,
    @SerializedName("nama_supplier") val nama_supplier: String,
    @SerializedName("kontak") val kontak: String,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("produksupllie") val produksupllie: String,

)
