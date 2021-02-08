package kg.nurtelecom.ofd.fragments.aboutapp

import android.os.Bundle
import android.view.View
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.FragmentAboutAppBinding


class AboutAppFragment : SimpleFragment<FragmentAboutAppBinding>() {
    override fun getBinding(): FragmentAboutAppBinding = FragmentAboutAppBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity.setToolbarTitle(R.string.info_about_app)
    }

    companion object {
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }
}