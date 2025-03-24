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
import andika.polman.logisticerp2.ui.product.ProductAdapter
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductCategoryFragment : Fragment() {



    private lateinit var recyclerViewProdukKategori: RecyclerView
    private lateinit var kategoriAdapter: ProductCategoryAdapter
    private var kategoriList = mutableListOf<ProdukKategori>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_category, container, false)
        recyclerViewProdukKategori = view.findViewById(R.id.rv_productcategory)
        recyclerViewProdukKategori.layoutManager = LinearLayoutManager(requireContext())
        kategoriAdapter = ProductCategoryAdapter(kategoriList){ produkKategori,action ->
            when(action){
                ProductCategoryAdapter.ActionType.EDIT->EditCategory(produkKategori)
                ProductCategoryAdapter.ActionType.DELETE->DeleteCategory(produkKategori)
                else -> throw IllegalArgumentException("Unknown action: $action")
            }

        }
        recyclerViewProdukKategori.adapter = kategoriAdapter

        fetchProduk()

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addcategory)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productKategoriFragment_to_formkategoriproductfragment)
        }

        return  view

    }

    private fun EditCategory(kategori: ProdukKategori)
    {
        val bundle = Bundle().apply {
            putInt("id_kategori", kategori!!.id_kategori)
        }
        findNavController().navigate(R.id.action_productKategoriFragment_to_formkategoriproductfragment,bundle)
    }

    private fun DeleteCategory(kategori: ProdukKategori)
    {
        AlertDialog.Builder(requireContext()) // Menggunakan requireContext() agar sesuai dengan Fragment
            .setTitle("Hapus Produk kategori")
            .setMessage("Apakah Anda yakin ingin menghapus produk \"${kategori?.nama_kategori}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                kategori?.id_kategori?.let { idProdukKategori ->
                    RetrofitClient.instance.deleteKategoriProduct(idProdukKategori).enqueue(object : Callback<ResponseMessage> {
                        override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Kategori berhasil dihapus", Toast.LENGTH_SHORT).show()
                                requireActivity().supportFragmentManager.popBackStack() // Kembali ke fragment sebelumnya
                            } else {
                                Toast.makeText(requireContext(), "Gagal menghapus kategori produk", Toast.LENGTH_SHORT).show()
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






    private fun fetchProduk() {
        RetrofitClient.instance.getKategoriProduct().enqueue(object : Callback<List<ProdukKategori>> {
            override fun onResponse(call: Call<List<ProdukKategori>>, response: Response<List<ProdukKategori>>) {
                response.body()?.let {
                    kategoriList.clear()
                    kategoriList.addAll(it)
                    kategoriAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ProdukKategori>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }


}