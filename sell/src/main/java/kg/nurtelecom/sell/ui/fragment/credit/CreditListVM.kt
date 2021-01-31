package kg.nurtelecom.sell.ui.fragment.credit

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.CreditListRepository
import kotlinx.coroutines.Dispatchers

abstract class CreditListVM : CoreViewModel() {

    abstract var creditListData: MutableLiveData<List<Content>>
    abstract var detailCreditList: MutableLiveData<Result>
    abstract fun fetchCreditList()
    abstract fun fetchDetailCreditList(id: Int)

}

class CreditListVMImpl (private val creditListRepository: CreditListRepository) : CreditListVM(){

    override var creditListData: MutableLiveData<List<Content>> = MutableLiveData()
    override var detailCreditList: MutableLiveData<Result> = MutableLiveData()

    override fun fetchCreditList() {
        safeCall(Dispatchers.IO) {
//            creditListData.postValue()
        }

    }

    override fun fetchDetailCreditList(id: Int) {
        safeCall (Dispatchers.IO){
//            detailCreditList.postValue()

        }
    }
}