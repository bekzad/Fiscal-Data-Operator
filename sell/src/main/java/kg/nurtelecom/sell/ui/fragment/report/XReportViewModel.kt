package kg.nurtelecom.sell.ui.fragment.report

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.z_report.ReportDetailed

abstract class XReportViewModel : CoreViewModel() {
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract fun fetchReportSession()
    abstract fun closeSession()
}

class XReportViewModelImpl : XReportViewModel() {

    init {
        println("XReportViewModelImpl INIT")
    }

    override var sessionReportData: MutableLiveData<ReportDetailed>
        get() = MutableLiveData()
        set(value) {}

    override fun fetchReportSession() {
        println("fetchReportSession()")
    }

    override fun closeSession() {
        println("closeSession()")
    }

}
