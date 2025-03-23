package andika.polman.logisticerp2.model
import com.google.gson.annotations.SerializedName

data class Produk(
    @SerializedName("id_produk") val id_produk: Int,
    @SerializedName("nama_produk") val nama_produk: String,
    @SerializedName("kode_barang") val kode_barang: String,
    @SerializedName("id_kategori") val id_kategori: Int,
    @SerializedName("stok_minimum") val stok_minimum: Int,
    @SerializedName("harga_beli") val harga_beli: Float,
    @SerializedName("harga_jual") val harga_jual: Float,

)
