package production.logcat.myapplication.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import production.logcat.myapplication.R
import production.logcat.myapplication.models.HistoryModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryAdapter(private val historyList:List<HistoryModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.historycard, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(historyList[position])
    }
    override fun getItemCount(): Int {
        return historyList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(history: HistoryModel) {
            val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("dd MMM yyyy")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val driverName: TextView =itemView.findViewById(R.id.historyDriverName)
            val date: TextView =itemView.findViewById(R.id.history_date)
            val from: TextView =itemView.findViewById(R.id.history_from)
            val to: TextView =itemView.findViewById(R.id.history_to)
            val amount: TextView =itemView.findViewById(R.id.history_amount)
            to.isSelected=true
            from.isSelected=true
            driverName.text="${history.driverName}"
            date.text= LocalDate.parse(history.historyDate).format(format)
            from.text="From : ${history.historyFrom}"
            to.text="To      : ${history.historyTo}"
            amount.text="Rs. ${history.historyAmount}"

        }
    }
}