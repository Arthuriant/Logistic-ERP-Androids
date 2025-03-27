package andika.polman.logisticerp2.model

import com.google.gson.annotations.SerializedName

data class ProdukMasukxProdukxSupplier(
    @SerializedName("id_transaksi") val id_transaksi: Int,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("id_supplier") val id_supplier: Int,
    @SerializedName("id_produk") val id_produk: Int,
    @SerializedName("jumlah") val jumlah: Int,
    @SerializedName("harga_beli") val harga_beli: Float,
    @SerializedName("nama_produk") val nama_produk: String,
    @SerializedName("nama_supplier") val nama_supplier: String,



)
