package production.logcat.myapplication.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import production.logcat.myapplication.WelcomeScreen
import production.logcat.myapplication.authentication.Login
import production.logcat.myapplication.authentication.SignUp

class WelcomePageAdapter (fragmentActivity: WelcomeScreen) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> SignUp()
            else-> Login()
        }
    }
}