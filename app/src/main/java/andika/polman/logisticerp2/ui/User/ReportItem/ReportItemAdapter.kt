package andika.polman.logisticerp2.ui.User.ReportItem

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.Report
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportItemAdapter(
    private val list: List<Report>
) : RecyclerView.Adapter<ReportItemAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIdReport: TextView = view.findViewById(R.id.tv_Titlereport)

        val txtIncomeReport: TextView = view.findViewById(R.id.tv_id_barangmasuk_report)
        val txtOutcomeReport : TextView = view.findViewById(R.id.tv_id_barangkeluar_report)
        val txtFinalReport : TextView = view.findViewById(R.id.tv_id_stokakhir_report)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_report, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Report = list[position]

        holder.txtIdReport.text = "${Report.nama_produk}"
        holder.txtIncomeReport.text = "Barang Masuk: ${Report.barang_masuk}"
        holder.txtOutcomeReport.text = "Barang Keluar: ${Report.barang_keluar}"
        holder.txtFinalReport.text = "Stok: ${Report.stok_akhir}"


    }

    override fun getItemCount(): Int = list.size
}