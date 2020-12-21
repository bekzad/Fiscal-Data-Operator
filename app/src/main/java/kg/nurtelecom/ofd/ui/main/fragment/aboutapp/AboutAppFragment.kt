package kg.nurtelecom.ofd.ui.main.fragment.aboutapp

import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.FragmentAboutAppBinding

class AboutAppFragment : SimpleFragment<FragmentAboutAppBinding>() {
    override fun getBinding(): FragmentAboutAppBinding = FragmentAboutAppBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.info_about_app)
    }

    companion object {
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }
}