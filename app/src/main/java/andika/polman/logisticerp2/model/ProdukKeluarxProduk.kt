package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class ProdukKeluarxProduk(
    @SerializedName("id_transaksi") val id_transaksi: Int,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("id_produk") val id_produk: Int,
    @SerializedName("jumlah") val jumlah: Int,
    @SerializedName("harga_jual") val harga_jual: Float,
    @SerializedName("nama_produk") val nama_produk: String,

)
