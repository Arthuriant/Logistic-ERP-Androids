package andika.polman.logisticerp2.ui.User.ReportItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Report
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportitemFragment : Fragment() {
    private lateinit var recyclerViewReport: RecyclerView
    private lateinit var reportAdapter: ReportItemAdapter
    private var reportList = mutableListOf<Report>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_reportitem, container, false)
        recyclerViewReport = view.findViewById(R.id.rv_Report)
        recyclerViewReport.layoutManager = LinearLayoutManager(requireContext())
        reportAdapter = ReportItemAdapter(reportList)
        recyclerViewReport.adapter = reportAdapter

        fetchProduk()

        return  view
    }

    private fun fetchProduk() {
        RetrofitClient.instance.getReport().enqueue(object :
            Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                response.body()?.let {
                    reportList.clear()
                    reportList.addAll(it)
                    reportAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }


}