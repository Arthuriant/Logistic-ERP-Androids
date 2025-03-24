package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class ProdukKategori(
    @SerializedName("id_kategori") val id_kategori: Int,
    @SerializedName("nama_kategori") val nama_kategori: String,
    @SerializedName("kode_kategori") val kode_kategori: String,
    @SerializedName("deskripsi") val deskripsi: String,


    )
