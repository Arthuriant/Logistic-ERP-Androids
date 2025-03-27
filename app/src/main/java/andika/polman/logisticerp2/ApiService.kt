package andika.polman.logisticerp2

import andika.polman.logisticerp2.model.LoginRequest
import andika.polman.logisticerp2.model.LoginResponse
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukKategori
import andika.polman.logisticerp2.model.ProdukKeluar
import andika.polman.logisticerp2.model.ProdukKeluarxProduk
import andika.polman.logisticerp2.model.ProdukMasuk
import andika.polman.logisticerp2.model.ProdukMasukxProdukxSupplier
import andika.polman.logisticerp2.model.ProdukxKategori
import andika.polman.logisticerp2.model.Report
import andika.polman.logisticerp2.model.Supplier
import andika.polman.logisticerp2.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    //SPESICAL

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("data_produk/{id}")
    fun getDetailProduct(@Path("id") idProduk: Int): Call<Produk>

    @GET("kategori_produk/{id}")
    fun getDetailProductCategory(@Path("id") idCategory: Int): Call<ProdukKategori>

    @GET("supplier/{id}")
    fun getDetailSupplier(@Path("id") idSupplier: Int): Call<Supplier>

    @GET("users/{id}")
    fun getDetailUser(@Path("id") idUser: Int): Call<User>



    //PRODUK

    @GET("data_produk")
    fun getDataProduct(): Call<List<ProdukxKategori>>

    @GET("data_produk")
    fun getDataProductAdd(): Call<List<Produk>>

    @POST("data_produk")
    fun tambahProduk(@Body produk: Produk): Call<ResponseMessage>

    @PUT("data_produk/{id}")
    fun updateProduct(@Path("id") id: Int, @Body produk: Produk): Call<ResponseMessage>

    @DELETE("data_produk/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<ResponseMessage>

    //KATEGORI PRODUK

    @GET("kategori_produk")
    fun getKategoriProduct(): Call<List<ProdukKategori>>

    @POST("kategori_produk")
    fun tambahKategoriProduk(@Body produkKategori: ProdukKategori): Call<ResponseMessage>

    @PUT("kategori_produk/{id}")
    fun updateKategoriProduct(@Path("id") id: Int, @Body kategori: ProdukKategori): Call<ResponseMessage>

    @DELETE("kategori_produk/{id}")
    fun deleteKategoriProduct(@Path("id") id: Int): Call<ResponseMessage>

    //SUPPLIER
    @GET("supplier")
    fun getSupplier(): Call<List<Supplier>>

    @POST("supplier")
    fun tambahSupplier(@Body supplier: Supplier): Call<ResponseMessage>

    @PUT("supplier/{id}")
    fun updateSupplier(@Path("id") id: Int, @Body supplier: Supplier): Call<ResponseMessage>

    @DELETE("supplier/{id}")
    fun deleteSupplier(@Path("id") id: Int): Call<ResponseMessage>

    //User
    @GET("users")
    fun getAllUser(): Call<List<User>>

    @GET("users/admins")
    fun getAllUserAdmin(): Call<List<User>>

    @GET("users/users")
    fun getAllUserUser(): Call<List<User>>

    @POST("users")
    fun tambahUser(@Body user: User): Call<ResponseMessage>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<ResponseMessage>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<ResponseMessage>

    //Incoming Item
    @GET("barang_masuk")
    fun getIncomingProduct(): Call<List<ProdukMasukxProdukxSupplier>>

    @POST("barang_masuk")
    fun tambahProdukMasuk(@Body produkMasuk: ProdukMasuk): Call<ResponseMessage>

    //Exit Item
    @GET("barang_keluar")
    fun getExitProduct(): Call<List<ProdukKeluarxProduk>>

    @POST("barang_keluar")
    fun tambahProdukKeluar(@Body produkKeluar: ProdukKeluar): Call<ResponseMessage>

    //Report
    @GET("laporan_stok")
    fun getReport(): Call<List<Report>>


}
