package andika.polman.logisticerp2.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukxKategori
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductFragment : Fragment() {
    private lateinit var recyclerViewProduk: RecyclerView
    private lateinit var produkAdapter: ProductAdapter
    private var produkList = mutableListOf<ProdukxKategori>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        recyclerViewProduk = view.findViewById(R.id.rv_product)
        recyclerViewProduk.layoutManager = LinearLayoutManager(requireContext())
        produkAdapter = ProductAdapter(produkList) { produk ->
            val bundle = Bundle().apply {
                putInt("id_produk", produk.id_produk)
            }
            findNavController().navigate(R.id.action_productFragment_to_detailproductfragment, bundle)
        }
        recyclerViewProduk.adapter = produkAdapter

        fetchProduk()

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addproduct)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_formProductFragment)
        }

        return view
    }

    private fun fetchProduk() {
        RetrofitClient.instance.getDataProduct().enqueue(object : Callback<List<ProdukxKategori>> {
            override fun onResponse(call: Call<List<ProdukxKategori>>, response: Response<List<ProdukxKategori>>) {
                response.body()?.let {
                    produkList.clear()
                    produkList.addAll(it)
                    produkAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ProdukxKategori>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }
}
