package production.logcat.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import production.logcat.myapplication.R
import production.logcat.myapplication.models.StandModel
import production.logcat.myapplication.models.dialogAlertBox

class StandAdapter(private val standList:List<StandModel>) : RecyclerView.Adapter<StandAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.standcard, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(standList[position])
    }
    override fun getItemCount(): Int {
        return standList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(standList: StandModel) {
            val standName: TextView =itemView.findViewById(R.id.standName)
            val standMode: TextView =itemView.findViewById(R.id.standMode)
            val standDistance: TextView =itemView.findViewById(R.id.standDistance)
            val standLandMark: TextView =itemView.findViewById(R.id.standLandMark)
            val layout: LinearLayout =itemView.findViewById(R.id.layout_in_standCard)
            layout.setOnClickListener {
                if(Integer.parseInt(standList.standDistance)<=5){
                    standList.nextPage(standList.standName,standList.standLandMark)
                }
                else{
                    dialogAlertBox(itemView.resources.getDrawable(R.drawable.tagdialogbackgroundstyle), itemView.context, "Oops !!", "Sorry... Auto stand is too away please select another one", "OK", "", R.drawable.warning, okFunc = {}, cancelFunc = {}, okVisible = true, cancelVisible = false)
                }
            }
            standMode.text=standList.standMode
            standName.text=standList.standName
            standDistance.text=when(standList.standDistance.compareTo("0")==0){
                true->"less than 1 km"
                else->"${standList.standDistance} km Away"
            }
            standLandMark.text=standList.standLandMark
        }
    }
}