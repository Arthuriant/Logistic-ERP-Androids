package andika.polman.logisticerp2.ui.User.ExitItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.ProdukKeluarxProduk

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExititemFragment : Fragment() {

    private lateinit var recyclerViewProdukKeluarxProduk: RecyclerView
    private lateinit var exitAdapter: ExitItemAdapter
    private var exitList = mutableListOf<ProdukKeluarxProduk>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view  =  inflater.inflate(R.layout.fragment_exititem, container, false)
        recyclerViewProdukKeluarxProduk = view.findViewById(R.id.rv_ExitItem)
        recyclerViewProdukKeluarxProduk.layoutManager = LinearLayoutManager(requireContext())
        exitAdapter = ExitItemAdapter(exitList)
        recyclerViewProdukKeluarxProduk.adapter = exitAdapter

        fetchProduk()

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addExitItem)
        fabAdd.setOnClickListener {
            Log.d("DEBUG", "FloatingActionButton clicked!") // Log untuk debugging
            findNavController().navigate(R.id.action_ExitFragment_to_FormExitFragment)
        }
        return  view
    }

    private fun fetchProduk() {
        RetrofitClient.instance.getExitProduct().enqueue(object :
            Callback<List<ProdukKeluarxProduk>> {
            override fun onResponse(call: Call<List<ProdukKeluarxProduk>>, response: Response<List<ProdukKeluarxProduk>>) {
                response.body()?.let {
                    exitList.clear()
                    exitList.addAll(it)
                    exitAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ProdukKeluarxProduk>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }


}