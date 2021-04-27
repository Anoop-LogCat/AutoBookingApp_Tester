package production.logcat.myapplication.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import production.logcat.myapplication.R
import production.logcat.myapplication.models.DriverModel
import production.logcat.myapplication.models.customerObject

class DriverAdapter(private val driverList: List<DriverModel>) : RecyclerView.Adapter<DriverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.drivercard, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(driverList[position])
    }
    override fun getItemCount(): Int {
        return driverList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(driver: DriverModel) {
            val driverName: TextView =itemView.findViewById(R.id.driverName)
            val driverDistance: TextView =itemView.findViewById(R.id.driverDistance)
            val driverAge: TextView =itemView.findViewById(R.id.driverAge)
            val driverTime: TextView =itemView.findViewById(R.id.driverTime)
            val driverPhone: TextView =itemView.findViewById(R.id.driverPhone)
            val profile:ImageView = itemView.findViewById(R.id.profile_drivers)
            val book: Button =itemView.findViewById(R.id.bookNowButton)
            book.setOnClickListener {
                messageDialogBox(itemView,driver)
            }
            val capitalized = StringBuilder(driver.driverName)
            capitalized.setCharAt(0, Character.toUpperCase(capitalized[0]))
            if(driver.driverProfile=="no image"){
                profile.setImageDrawable(itemView.resources.getDrawable(R.drawable.defaultdriver))
            }
            else{
                Picasso.get().load(driver.driverProfile).resize(1100,1300).centerCrop().into(profile)
            }
            driverName.text=capitalized.toString()
            driverDistance.text="${driver.driverDistance} km away"
            driverAge.text="Age : ${driver.driverAge} Years"
            driverTime.text="Working Time : ${driver.driverTime}"
            driverPhone.text=driver.driverPhone
        }


        private fun messageDialogBox(view:View,driverObject: DriverModel){
            val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.tagbookinglayout, null)
            val mBuilder = MaterialAlertDialogBuilder(view.context).setView(mDialogView)
            mBuilder.background = view.resources.getDrawable(R.drawable.tagdialogbackgroundstyle)
            val dialog=mBuilder.show()
            val dialogTitle:TextView=mDialogView.findViewById(R.id.dialogTitle)
            val dialogImage:ImageView=mDialogView.findViewById(R.id.dialogTitleImage)
            val dialogBody:TextView=mDialogView.findViewById(R.id.dialogBody)
            val dialogOk:Button=mDialogView.findViewById(R.id.okButton)
            val dialogCancel:Button=mDialogView.findViewById(R.id.cancelButton)
            dialogTitle.text="Safety Alert"
            dialogBody.text="This option will send the drivers details to your nominee if you provided"
            dialogImage.setImageResource(R.drawable.verification_icon)
            dialogCancel.visibility=View.VISIBLE
            dialogOk.visibility = View.VISIBLE
            dialogOk.text="Send & Book"
            dialogCancel.text="Skip & Book"
            dialogCancel.setOnClickListener {
                dialog.dismiss()
                driverObject.bookWithoutSendMessage(driverObject)
            }
            dialogOk.setOnClickListener {
                dialog.dismiss()
                val arrayAdapter = ArrayAdapter<String>(itemView.context, android.R.layout.simple_list_item_1)
                customerObject!!.nominee.keys.forEach{ id ->
                    if(customerObject!!.nominee[id].toString().compareTo("1234567890")!=0){
                        arrayAdapter.add("$id : ${customerObject!!.nominee[id].toString()}")
                    }
                }
                if(!arrayAdapter.isEmpty){
                    val builderSingle: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                    builderSingle.setTitle("Select Nominee")
                    builderSingle.setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
                    builderSingle.setAdapter(arrayAdapter) { dialog, which ->
                        dialog.dismiss()
                        val strName = arrayAdapter.getItem(which)!!.split(":").last().removePrefix(" ")
                        driverObject.bookWithSendMessage(driverObject,strName)
                    }
                    builderSingle.show()
                }
                else{
                    Toast.makeText(itemView.context,"no nominee number added",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}