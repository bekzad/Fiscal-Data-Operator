package kg.nurtelecom.ofd.aboutapp

import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.FragmentAboutAppBinding

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