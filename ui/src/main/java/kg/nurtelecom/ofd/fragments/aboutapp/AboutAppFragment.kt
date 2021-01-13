package kg.nurtelecom.ofd.fragments.aboutapp

import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.ui.databinding.FragmentAboutAppBinding


class AboutAppFragment : SimpleFragment<FragmentAboutAppBinding>() {
    override fun getBinding(): FragmentAboutAppBinding = FragmentAboutAppBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }
}