package kg.nurtelecom.ofd.ui.edittext

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.solver.SolverVariableValues
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.InputTextFieldBinding

class InputTextField (context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        InputTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    init {
        setIcon(R.drawable.ic_baseline_visibility_off)
    }

    fun setInputType(type: Int) {
        binding.etInputText.inputType = type
    }

    fun setHint(text: String) {
        binding.etInputText.hint = text
    }

    fun setEyesIconIsVisible(value: Boolean) {
        if (value) {
            binding.inputFieldIcon.visibility = VISIBLE
        }else{
            binding.inputFieldIcon.visibility = GONE
        }
    }

    fun setOnTextChanged(listener: TextWatcher) {
        binding.etInputText.addTextChangedListener(listener)
    }

   private fun setIcon(resId: Int) {
        binding.inputFieldIcon.setTag(resId)
    }

    fun switchIconType() {
        println("lol")
        when (binding.inputFieldIcon.tag){
            R.drawable.ic_baseline_visibility_off ->{
                binding.etInputText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                setIcon(R.drawable.ic_baseline_visibility)
                println("sos")
            }
            R.drawable.ic_baseline_visibility ->{
                binding.etInputText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                setIcon(R.drawable.ic_baseline_visibility_off)
            }
        }
    }

    fun setOnIconChanged(listener: OnClickListener){
        binding.inputFieldIcon.setOnClickListener(listener)
    }

    fun fetchIconType() {
        println(binding.inputFieldIcon.tag)

    }

    fun fetchTag(resId: Int){
        

    }
}
