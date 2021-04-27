package production.logcat.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import okhttp3.OkHttpClient
import production.logcat.myapplication.models.FireCustomerModel
import production.logcat.myapplication.models.customerObject
import production.logcat.myapplication.models.dialogAlertBox


class SplashScreen : Fragment() {

    private val user = FirebaseAuth.getInstance().currentUser
    private var navController: NavController?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if(user!=null){
            val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if(connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED){
                GetUserData().execute()
            }
            else{
                dialogAlertBox(resources.getDrawable(R.drawable.tagdialogbackgroundstyle), requireContext(), "No Connectivity", "Please turn on the internet and press OK", "OK", "", R.drawable.smartphone, okFunc = {
                    GetUserData().execute()
                }, cancelFunc = {}, okVisible = true, cancelVisible = false)
            }
        }
        else{ navController!!.navigate(R.id.action_splashScreen_to_welcomeScreen) }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class GetUserData : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {
            return try {
                val url = "https://us-central1-auto-pickup-apps.cloudfunctions.net/ViewCustomer/${user?.uid}"
                val request: okhttp3.Request = okhttp3.Request.Builder().url(url).build()
                val response= OkHttpClient().newCall(request).execute()
                response.body()?.string().toString()
            }catch (e: Exception){
                "ERROR"
            }
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result!!.compareTo("ERROR")!=0){
                customerObject = Gson().fromJson(result, FireCustomerModel::class.java)
                navController!!.navigate(R.id.action_splashScreen_to_home2)
            }
        }
    }
}