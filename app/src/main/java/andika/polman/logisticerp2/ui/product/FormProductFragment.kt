package andika.polman.logisticerp2.ui.product

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormProductFragment : Fragment() {
    private lateinit var spinnerProductCategory: Spinner
    private var kategoriList: List<ProdukKategori> = listOf()
    private var kategoriAdapter: ArrayAdapter<String>? = null
    private var selectedCategoryId: Int = 0

    private lateinit var tvTitleForm : TextView
    private lateinit var etProductName: EditText
    private lateinit var etProductCode: EditText
    private lateinit var etStockMinimum: EditText
    private lateinit var etProductBuy: EditText
    private lateinit var etProductSell: EditText
    private lateinit var btnSimpanProduk: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_form_product, container, false)
        tvTitleForm = view.findViewById(R.id.tv_titleformproduk)
        etProductName = view.findViewById(R.id.et_productname)
        etProductCode = view.findViewById(R.id.et_productnamecode)
        etStockMinimum = view.findViewById(R.id.et_stokminimum)
        etProductBuy = view.findViewById(R.id.et_productbuy)
        etProductSell = view.findViewById(R.id.et_productsell)
        btnSimpanProduk = view.findViewById(R.id.btn_simpanproduk)

        spinnerProductCategory = view.findViewById(R.id.spinner_productcategory)
        loadKategoriProduk()


        val idProduk = arguments?.getInt("id_produk") ?: -1

        if (idProduk != -1) {
            tvTitleForm.text = "Ubah Produk"
            fetchDetailProduk(idProduk)
        }
        else
        {
            tvTitleForm.text = "Tambah produk"
        }




        btnSimpanProduk.setOnClickListener {
            // Ambil data dari EditText
            val namaProduk = etProductName.text.toString().trim()
            val kodeBarang = etProductCode.text.toString().trim()
            val idKategori = selectedCategoryId
            val stokMinimum = etStockMinimum.text.toString().toIntOrNull() ?: 0
            val hargaBeli = etProductBuy.text.toString().toFloatOrNull() ?: 0f
            val hargaJual = etProductSell.text.toString().toFloatOrNull() ?: 0f

            if (idProduk == -1) {
                // Menambah produk baru
                tambahProduk(namaProduk, kodeBarang, idKategori, stokMinimum, hargaBeli, hargaJual)
            } else {
                // Mengupdate produk yang sudah ada
                updateProduk(idProduk, namaProduk, kodeBarang, idKategori, stokMinimum, hargaBeli, hargaJual)
            }

        }

        return view
    }

    private fun loadKategoriProduk() {
        RetrofitClient.instance.getKategoriProduct().enqueue(object : Callback<List<ProdukKategori>> {
            override fun onResponse(call: Call<List<ProdukKategori>>, response: Response<List<ProdukKategori>>) {
                if (response.isSuccessful) {
                    kategoriList = response.body() ?: listOf()
                    val kategoriNames = kategoriList.map { it.nama_kategori } // Nama kategori
                    kategoriAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, kategoriNames)
                    kategoriAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerProductCategory.adapter = kategoriAdapter

                    // Pilih kategori jika sedang mengedit produk
                    val selectedIndex = kategoriList.indexOfFirst { it.id_kategori == selectedCategoryId }
                    if (selectedIndex != -1) {
                        spinnerProductCategory.setSelection(selectedIndex)
                    }

                    spinnerProductCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedCategoryId = kategoriList[position].id_kategori
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedCategoryId = 0
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ProdukKategori>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal memuat kategori", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchDetailProduk(idProduk: Int) {
        RetrofitClient.instance.getDetailProduct(idProduk).enqueue(object : Callback<Produk> {
            override fun onResponse(call: Call<Produk>, response: Response<Produk>) {
                response.body()?.let { produk ->

                    etProductName.setText(produk.nama_produk)
                    etProductCode.setText(produk.kode_barang)
                    selectedCategoryId = produk.id_kategori
                    etStockMinimum.setText(produk.stok_minimum.toString())
                    etProductBuy.setText(produk.harga_beli.toString())
                    etProductSell.setText(produk.harga_jual.toString())

                }
            }

            override fun onFailure(call: Call<Produk>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal !", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProduk(idProduk: Int, nama_produk: String, kode_barang: String, id_kategori: Int, stok_minimum: Int, harga_beli: Float, harga_jual: Float) {
        val produkUpdate = Produk(
            id_produk = idProduk,
            nama_produk = nama_produk,
            kode_barang = kode_barang,
            id_kategori = id_kategori,
            stok_minimum = stok_minimum,
            harga_beli = harga_beli,
            harga_jual = harga_jual
        )

        RetrofitClient.instance.updateProduct(idProduk, produkUpdate).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToProductFragment()
                    Toast.makeText(requireContext(), "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal memperbarui produk: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun tambahProduk(nama_produk: String, kode_barang: String,id_kategori: Int,stok_minimum: Int, harga_beli: Float, harga_jual: Float) {
        val produkBaru = Produk(
            id_produk = 0,
            nama_produk = nama_produk,
            kode_barang = kode_barang,
            id_kategori = id_kategori,
            stok_minimum = stok_minimum,
            harga_beli = harga_beli,
            harga_jual = harga_jual,

        )

        RetrofitClient.instance.tambahProduk(produkBaru).enqueue(object : Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToProductFragment()
                    Toast.makeText(requireContext(), "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika respons dari server gagal, tampilkan pesan error
                    Toast.makeText(requireContext(), "Produk menambahkan buku: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Produk menambahkan buku", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeToProductFragment()
    {
        findNavController().navigate(R.id.action_formproductFragment_to_ProductFragment)
    }


}