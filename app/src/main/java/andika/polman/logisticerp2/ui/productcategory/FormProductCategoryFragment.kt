package andika.polman.logisticerp2.ui.productcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukKategori
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormProductCategoryFragment : Fragment() {
    private lateinit var tvTitleForm : TextView
    private lateinit var etProductCategoryName: EditText
    private lateinit var etProductCategoryCode: EditText
    private lateinit var etProductCategoryDescription: EditText
    private lateinit var btnSimpanKategori: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_form_product_category, container, false)
        tvTitleForm = view.findViewById(R.id.tv_titleformcategory)
        etProductCategoryName = view.findViewById(R.id.et_categoryname)
        etProductCategoryCode = view.findViewById(R.id.et_categorycode)
        etProductCategoryDescription = view.findViewById(R.id.et_categorydescription)
        btnSimpanKategori = view.findViewById(R.id.btn_simpancategory)


        val idKategori = arguments?.getInt("id_kategori") ?: -1

        if (idKategori != -1) {
            tvTitleForm.text = "Ubah Produk Kategori"
            fetchDetailProdukKategori(idKategori)
        }
        else
        {
            tvTitleForm.text = "Tambah produk Kategori"
        }




        btnSimpanKategori.setOnClickListener {

            val namaKategori = etProductCategoryName.text.toString().trim()
            val kodeKategori = etProductCategoryCode.text.toString().trim()
            val deskripsiKategori = etProductCategoryDescription.text.toString().trim()
            if (idKategori == -1) {
                tambahProdukKategori(namaKategori, kodeKategori, deskripsiKategori)
            } else {
                updateProdukKategori(idKategori, namaKategori, kodeKategori, deskripsiKategori)
            }

        }


        return  view
    }

    private fun  fetchDetailProdukKategori(idKategori: Int) {
        RetrofitClient.instance.getDetailProductCategory(idKategori).enqueue(object : Callback<ProdukKategori> {
            override fun onResponse(call: Call<ProdukKategori>, response: Response<ProdukKategori>) {
                response.body()?.let { kategori ->

                    etProductCategoryName.setText(kategori.nama_kategori)
                    etProductCategoryCode.setText(kategori.kode_kategori)
                    etProductCategoryDescription.setText(kategori.deskripsi)


                }
            }

            override fun onFailure(call: Call<ProdukKategori>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal !", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProdukKategori(idKategori: Int, nama_kategori: String, kode_kategori: String, deskripsi_kategori: String) {
        val kategoriUpdate = ProdukKategori(
            id_kategori = idKategori,
            nama_kategori = nama_kategori,
            kode_kategori = kode_kategori,
            deskripsi = deskripsi_kategori
        )

        RetrofitClient.instance.updateKategoriProduct(idKategori, kategoriUpdate).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToProductCategoryFragment()
                    Toast.makeText(requireContext(), "Produk Kategori berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal memperbarui produk Kategori: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun tambahProdukKategori(nama_kategori: String, kode_kategori: String,deskripsi_kategori: String) {
        val kategoriBaru = ProdukKategori(
            id_kategori = 0,
            nama_kategori =nama_kategori,
            kode_kategori = kode_kategori,
            deskripsi = deskripsi_kategori
            )

        RetrofitClient.instance.tambahKategoriProduk(kategoriBaru).enqueue(object : Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToProductCategoryFragment()
                    Toast.makeText(requireContext(), "Produk Kategori berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika respons dari server gagal, tampilkan pesan error
                    Toast.makeText(requireContext(), "Produk Kategori menambahkan buku: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Produk menambahkan buku", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeToProductCategoryFragment()
    {
        findNavController().navigate(R.id.action_formkategoriproductfragment_to_productKategoriFragment)
    }


}