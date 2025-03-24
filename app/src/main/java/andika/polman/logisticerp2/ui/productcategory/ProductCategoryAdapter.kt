package andika.polman.logisticerp2.ui.productcategory

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.ProdukKategori
import andika.polman.logisticerp2.ui.product.ProductAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductCategoryAdapter(
    private val list: List<ProdukKategori>,
    private val onItemClick: (ProdukKategori, ActionType) -> Unit
) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    enum class ActionType {
        EDIT, DELETE
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNamaProdukKategori: TextView = view.findViewById(R.id.tv_categoryname)
        val txtKodeKategori: TextView = view.findViewById(R.id.tv_categorycode)
        val txtDeskripsiKategori: TextView = view.findViewById(R.id.categorydescription)
        val imgBtnEditKategori : ImageButton = view.findViewById(R.id.btn_editcategory)
        val imgBtnDeleteKategori : ImageButton = view.findViewById(R.id.btn_deletecategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_category, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kategori = list[position]
        holder.txtNamaProdukKategori.text = kategori.nama_kategori
        holder.txtKodeKategori.text = "Kode Barang: ${kategori.kode_kategori}"
        holder.txtDeskripsiKategori.text = "Kategori: ${kategori.deskripsi}"

        holder.imgBtnEditKategori.setOnClickListener { onItemClick(kategori,ActionType.EDIT) }
        holder.imgBtnDeleteKategori.setOnClickListener { onItemClick(kategori,ActionType.DELETE) }
    }

    override fun getItemCount(): Int = list.size
}
