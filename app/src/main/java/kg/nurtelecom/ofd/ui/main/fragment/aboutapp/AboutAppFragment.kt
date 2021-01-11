package kg.nurtelecom.ofd.ui.main.fragment.aboutapp

import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.ofd.databinding.FragmentAboutAppBinding

class AboutAppFragment : SimpleFragment<FragmentAboutAppBinding>() {
    override fun getBinding(): FragmentAboutAppBinding = FragmentAboutAppBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }
}