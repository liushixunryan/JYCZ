package cn.ryanliu.jycz.util

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import com.blankj.utilcode.util.KeyboardUtils

class EditNoEnterFilter(val editText: EditText):InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source != null) {
            val s = source.toString()
            if (s.contains("\n")) {
                // Hide soft keyboard
                KeyboardUtils.hideSoftInput(editText)
                // Lose focus
                editText.clearFocus()
                // Remove all newlines
                return s.replace("\n".toRegex(), "")
            }
        }
        return null
    }
}