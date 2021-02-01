package kg.nurtelecom.sell.ui.fragment.report

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.repository.SessionRepository
import kotlinx.coroutines.Dispatchers

abstract class SessionViewModel : CoreViewModel() {
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract fun fetchReportSession()
    abstract fun closeSession()
}

class SessionViewModelImpl (private val sessionRepository: SessionRepository) : SessionViewModel() {
    override var  sessionReportData: MutableLiveData<ReportDetailed> = MutableLiveData()

    override fun fetchReportSession() {
        safeCall(Dispatchers.IO) {
            sessionReportData.postValue(sessionRepository.fetchReportSession())
        }
    }

    override fun closeSession() {
        safeCall(Dispatchers.IO) {
            sessionReportData.postValue(sessionRepository.closeSession())
        }
    }
}