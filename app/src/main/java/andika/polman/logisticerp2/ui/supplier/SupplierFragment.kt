package andika.polman.logisticerp2.ui.supplier

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Supplier

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


class SupplierFragment : Fragment() {

    private lateinit var recyclerViewSupplier: RecyclerView
    private lateinit var supplierAdapter: SupplierAdapter
    private var SupplierList = mutableListOf<Supplier>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_supplier, container, false)
        recyclerViewSupplier = view.findViewById(R.id.rv_supplier)
        recyclerViewSupplier.layoutManager = LinearLayoutManager(requireContext())
        supplierAdapter = SupplierAdapter(SupplierList){ supplier,action ->
            when(action){
                SupplierAdapter.ActionType.EDIT->EditSupplier(supplier)
                SupplierAdapter.ActionType.DELETE->DeleteSupplier(supplier)
                else -> throw IllegalArgumentException("Unknown action: $action")
            }

        }
        recyclerViewSupplier.adapter = supplierAdapter

        fetchProduk()

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addsupplier)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_SupplierFragment_to_FormSupplerFragment)
        }
        return  view
    }

    private fun EditSupplier(Supplier: Supplier)
    {
        val bundle = Bundle().apply {
            putInt("id_Supplier", Supplier!!.id_supplier)
        }
        findNavController().navigate(R.id.action_SupplierFragment_to_FormSupplerFragment,bundle)
    }

    private fun DeleteSupplier(Supplier: Supplier)
    {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Produk Supplier")
            .setMessage("Apakah Anda yakin ingin menghapus produk \"${Supplier?.nama_supplier}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                Supplier?.id_supplier?.let { idSupplier ->
                    RetrofitClient.instance.deleteSupplier(idSupplier).enqueue(object :
                        Callback<ResponseMessage> {
                        override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Supplier berhasil dihapus", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_FormSupplerFragment_to_SupplierFragment)
                            } else {
                                Toast.makeText(requireContext(), "Gagal menghapus Supplier produk", Toast.LENGTH_SHORT).show()
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
        RetrofitClient.instance.getSupplier().enqueue(object :
            Callback<List<Supplier>> {
            override fun onResponse(call: Call<List<Supplier>>, response: Response<List<Supplier>>) {
                response.body()?.let {
                    SupplierList.clear()
                    SupplierList.addAll(it)
                    supplierAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Supplier>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }


}