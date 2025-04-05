package andika.polman.logisticerp2.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Produk
import android.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductFragment : Fragment() {
    private lateinit var txtNamaProduk: TextView
    private lateinit var txtKodeBarang: TextView
    private lateinit var txtIdKategori: TextView
    private lateinit var txtDetailMinimum: TextView
    private lateinit var txtHargaBeli: TextView
    private lateinit var txtHargaJual: TextView
    private lateinit var btnEditProduct: Button
    private lateinit var btnDeleteProduct: Button
    private  var recentProduk: Produk? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_product, container, false)

        txtNamaProduk = view.findViewById(R.id.tv_nama_produk)
        txtKodeBarang = view.findViewById(R.id.tv_kode_barang2)
        txtIdKategori = view.findViewById(R.id.tv_id_kategori)
        txtDetailMinimum = view.findViewById(R.id.tv_stok_minimum)
        txtHargaBeli = view.findViewById(R.id.tv_harga_beli2)
        txtHargaJual = view.findViewById(R.id.tv_harga_jual)
        btnEditProduct = view.findViewById(R.id.btn_editProduk)
        btnDeleteProduct = view.findViewById(R.id.btn_deleteProduk)

        btnEditProduct.setOnClickListener {
            EditProduct()
        }

        btnDeleteProduct.setOnClickListener {
            DeleteProduct()
        }


        val idProduk = arguments?.getInt("id_produk") ?: -1
        if (idProduk != -1) {
            fetchDetailProduk(idProduk)
        }

        return view
    }

    private fun EditProduct() {
        if (recentProduk == null) {
            Toast.makeText(requireContext(), "Data produk belum tersedia", Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle().apply {
            putInt("id_produk", recentProduk!!.id_produk)
        }

        findNavController().navigate(R.id.action_detailproductFragment_to_formdetailproductfragment, bundle)
    }


    private fun DeleteProduct() {
        if (recentProduk == null) {
            Toast.makeText(requireContext(), "Produk tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(requireContext()) // Menggunakan requireContext() agar sesuai dengan Fragment
            .setTitle("Hapus Produk")
            .setMessage("Apakah Anda yakin ingin menghapus produk \"${recentProduk?.nama_produk}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                recentProduk?.id_produk?.let { idProduk ->
                    RetrofitClient.instance.deleteProduct(idProduk).enqueue(object : Callback<ResponseMessage> {
                        override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Produk berhasil dihapus", Toast.LENGTH_SHORT).show()
                                requireActivity().supportFragmentManager.popBackStack() // Kembali ke fragment sebelumnya
                            } else {
                                Toast.makeText(requireContext(), "Gagal menghapus produk", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                            Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }


    private fun fetchDetailProduk(idProduk: Int) {
        RetrofitClient.instance.getDetailProduct(idProduk).enqueue(object : Callback<Produk> {
            override fun onResponse(call: Call<Produk>, response: Response<Produk>) {
                response.body()?.let { produk ->
                    recentProduk = produk
                    txtNamaProduk.text = produk.nama_produk
                    txtKodeBarang.text = "Kode: ${produk.kode_barang}"
                    txtIdKategori.text = "Kategori: ${produk.id_kategori}"
                    txtDetailMinimum.text = "Stok Minimum: ${produk.harga_jual}"
                    txtHargaBeli.text = "Harga Beli: ${produk.harga_beli}"
                    txtHargaJual.text = "Harga Jual: ${produk.harga_jual}"
                }
            }

            override fun onFailure(call: Call<Produk>, t: Throwable) {
                txtNamaProduk.text = "Gagal memuat data"
            }
        })
    }
}