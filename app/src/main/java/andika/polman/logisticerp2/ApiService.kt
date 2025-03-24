package andika.polman.logisticerp2

import andika.polman.logisticerp2.model.LoginRequest
import andika.polman.logisticerp2.model.LoginResponse
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukKategori
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("data_produk/{id}")
    fun getDetailProduct(@Path("id") idProduk: Int): Call<Produk>

    @GET("kategori_produk/{id}")
    fun getDetailProductCategory(@Path("id") idCategory: Int): Call<ProdukKategori>

    @GET("data_produk")
    fun getDataProduct(): Call<List<Produk>>

    @POST("data_produk")
    fun tambahProduk(@Body produk: Produk): Call<ResponseMessage>

    @PUT("data_produk/{id}")
    fun updateProduct(@Path("id") id: Int, @Body produk: Produk): Call<ResponseMessage>

    @DELETE("data_produk/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<ResponseMessage>

    @GET("kategori_produk")
    fun getKategoriProduct(): Call<List<ProdukKategori>>

    @POST("kategori_produk")
    fun tambahKategoriProduk(@Body produkKategori: ProdukKategori): Call<ResponseMessage>

    @PUT("kategori_produk/{id}")
    fun updateKategoriProduct(@Path("id") id: Int, @Body kategori: ProdukKategori): Call<ResponseMessage>

    @DELETE("kategori_produk/{id}")
    fun deleteKategoriProduct(@Path("id") id: Int): Call<ResponseMessage>

}
