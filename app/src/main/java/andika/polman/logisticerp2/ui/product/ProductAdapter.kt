package andika.polman.logisticerp2.ui.product

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.Produk
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val list: List<Produk>,
    private val onItemClick: (Produk) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNamaProduk: TextView = view.findViewById(R.id.tv_nama_produk)
        val txtKodeBarang: TextView = view.findViewById(R.id.tv_kode_barang)
        val txtIdKategori: TextView = view.findViewById(R.id.tv_id_kategori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_produk, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = list[position]
        holder.txtNamaProduk.text = produk.nama_produk
        holder.txtKodeBarang.text = "Kode Barang: ${produk.kode_barang}"
        holder.txtIdKategori.text = "Kategori: ${produk.id_kategori}"

        holder.itemView.setOnClickListener { onItemClick(produk) }
    }

    override fun getItemCount(): Int = list.size
}
