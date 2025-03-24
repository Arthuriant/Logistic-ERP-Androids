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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormSupplierFragment : Fragment() {

    private lateinit var tvTitleFormSupplier : TextView
    private lateinit var etSupplierNama: EditText
    private lateinit var etSupplierKontak: EditText
    private lateinit var etSupplierAlamat: EditText
    private lateinit var etSupplierProduk: EditText
    private lateinit var btnSimpanSupllier: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_form_supplier, container, false)
        tvTitleFormSupplier = view.findViewById(R.id.tv_titleSupplier)
        etSupplierNama = view.findViewById(R.id.et_suppliername)
        etSupplierKontak = view.findViewById(R.id.et_suppliercontact)
        etSupplierAlamat = view.findViewById(R.id.et_supplieraddres)
        etSupplierProduk = view.findViewById(R.id.et_suppliersupplies)
        btnSimpanSupllier = view.findViewById(R.id.btn_simpansupplier)



        val idSupplier = arguments?.getInt("id_Supplier") ?: -1

        if (idSupplier != -1) {
            tvTitleFormSupplier.text = "Ubah Produk Supplier"
            fetchDetailSupplier(idSupplier)
        }
        else
        {
            tvTitleFormSupplier.text = "Tambah produk Supplier"
        }

        btnSimpanSupllier.setOnClickListener {

            val supplierNama = etSupplierNama.text.toString().trim()
            val supplierKontak =etSupplierKontak.text.toString().trim()
            val supplierAlamat =etSupplierAlamat.text.toString().trim()
            val supplierProduk =etSupplierProduk.text.toString().trim()

                if (idSupplier == -1) {
                tambahSupplier(supplierNama, supplierKontak, supplierAlamat,supplierProduk)
            } else {
                updateSupplier(idSupplier, supplierNama, supplierKontak, supplierAlamat,supplierProduk)
            }
        }
        
        return view
    }

    private fun  fetchDetailSupplier(idSupplier: Int) {
        RetrofitClient.instance.getDetailSupplier(idSupplier).enqueue(object :
            Callback<Supplier> {
            override fun onResponse(call: Call<Supplier>, response: Response<Supplier>) {
                response.body()?.let { Supplier ->

                    etSupplierNama.setText(Supplier.nama_supplier)
                    etSupplierKontak.setText(Supplier.kontak)
                    etSupplierAlamat.setText(Supplier.alamat)
                    etSupplierProduk.setText(Supplier.produksupllie)

                }
            }

            override fun onFailure(call: Call<Supplier>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal !", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateSupplier(idSupplier: Int, supplierNama: String, supplierKontak: String , supplierAlamat: String,supplierProduk: String) {
        val SupplierUpdate = Supplier(
            id_supplier = idSupplier,
            nama_supplier = supplierNama,
            kontak = supplierKontak,
            alamat = supplierAlamat,
            produksupllie = supplierProduk
        )

        RetrofitClient.instance.updateSupplier(idSupplier, SupplierUpdate).enqueue(object :
            Callback<ResponseMessage> {
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToSupplierFragment()
                    Toast.makeText(requireContext(), "Produk Supplier berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal memperbarui produk Supplier: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun tambahSupplier(supplierNama: String, supplierKontak: String , supplierAlamat: String,supplierProduk: String) {
        val SupplierBaru = Supplier(
            id_supplier = 0,
            nama_supplier = supplierNama,
            kontak = supplierKontak,
            alamat = supplierAlamat,
            produksupllie = supplierProduk

        )

        RetrofitClient.instance.tambahSupplier(SupplierBaru).enqueue(object :
            Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToSupplierFragment()
                    Toast.makeText(requireContext(), "Produk Supplier berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika respons dari server gagal, tampilkan pesan error
                    Toast.makeText(requireContext(), "Produk Supplier menambahkan buku: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Produk menambahkan buku", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeToSupplierFragment()
    {
        findNavController().navigate(R.id.action_FormSupplerFragment_to_SupplierFragment)
    }


}