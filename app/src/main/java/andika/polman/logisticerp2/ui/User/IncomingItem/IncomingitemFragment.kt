package andika.polman.logisticerp2.ui.User.IncomingItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.ProdukMasukxProdukxSupplier

import andika.polman.logisticerp2.ui.productcategory.ProductCategoryAdapter
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IncomingitemFragment : Fragment() {


    private lateinit var recyclerViewProdukMasukxProdukxSupplier: RecyclerView
    private lateinit var incomingAdapter: IncomingItemAdapter
    private var IncomingList = mutableListOf<ProdukMasukxProdukxSupplier>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  =   inflater.inflate(R.layout.fragment_incomingitem, container, false)
        recyclerViewProdukMasukxProdukxSupplier = view.findViewById(R.id.rv_IncomingItem)
        recyclerViewProdukMasukxProdukxSupplier.layoutManager = LinearLayoutManager(requireContext())
        incomingAdapter = IncomingItemAdapter(IncomingList)
        recyclerViewProdukMasukxProdukxSupplier.adapter = incomingAdapter

        fetchProduk()

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addIncomingItem)
        fabAdd.setOnClickListener {
            Log.d("DEBUG", "FloatingActionButton clicked!") // Log untuk debugging
            findNavController().navigate(R.id.action_IncomingFragment_to_FormIncomingFragment)
        }

        return  view
    }

    

    private fun fetchProduk() {
        RetrofitClient.instance.getIncomingProduct().enqueue(object :
            Callback<List<ProdukMasukxProdukxSupplier>> {
            override fun onResponse(call: Call<List<ProdukMasukxProdukxSupplier>>, response: Response<List<ProdukMasukxProdukxSupplier>>) {
                response.body()?.let {
                    IncomingList.clear()
                    IncomingList.addAll(it)
                    incomingAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ProdukMasukxProdukxSupplier>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }



}