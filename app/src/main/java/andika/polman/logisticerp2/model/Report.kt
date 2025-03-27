package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("id_laporan") val id_laporan: Int,
    @SerializedName("id_produk") val id_produk: Int,
    @SerializedName("stok_awal") val stok_awal: Int,
    @SerializedName("barang_masuk") val barang_masuk: Int,
    @SerializedName("barang_keluar") val barang_keluar: Int,
    @SerializedName("stok_akhir") val stok_akhir: Int,
    @SerializedName("nama_produk") val nama_produk: String

)
