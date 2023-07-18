import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import java.lang.Integer.max

@Suppress("MagicNumber")
class MaskVisualTransformation(
    private val decimalDigits: Int = 2,
    private val thousandsSeparator: Char = ' ',
    private val decimalSeparator: Char = DecimalFormat().decimalFormatSymbols.decimalSeparator,
    private val showZeroValue: Boolean = false
) : VisualTransformation {

    private val regex = Regex("[^0-9]")

    companion object {
        const val thousandsInterval = 3
    }

    override fun filter(text: AnnotatedString): TransformedText {
        var inputText = text.text

        if (inputText.isEmpty() && showZeroValue)
            return TransformedText(AnnotatedString(inputText), OffsetMapping.Identity)

        val fractionPart = if (inputText.length >= decimalDigits) {
            inputText.subSequence(inputText.length - decimalDigits, inputText.length)
        } else {
            inputText.padStart(decimalDigits, '0')
        }

        val intPart: StringBuilder = StringBuilder(
            if (inputText.length > decimalDigits) {
                inputText.subSequence(0, inputText.length - decimalDigits)
            } else {
                "0"
            }
        )

        val intPartLength = intPart.length

        if (intPart.length > thousandsInterval) {
            val thousandsSeparatorCount: Int = (intPart.length - 1) / thousandsInterval
            for (index in 1..thousandsSeparatorCount) {
                intPart.insert(intPartLength - (index * thousandsInterval), thousandsSeparator)
            }
        }

        val maskedText = intPart.toString() + decimalSeparator + fractionPart
        return TransformedText(
            AnnotatedString(maskedText),
            getOffsetMapping(inputText, maskedText, decimalDigits)
        )
    }

    private fun getOffsetMapping(unmaskedText: String, maskedText: String, decimalDigits: Int) =
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                when {
                    unmaskedText.length <= decimalDigits -> {
                        maskedText.length - (unmaskedText.length - offset)
                    }

                    else -> {
                        offset + offsetMaskCount(offset, maskedText)
                    }
                }

            override fun transformedToOriginal(offset: Int): Int =
                when {
                    unmaskedText.length <= decimalDigits -> {
                        max(unmaskedText.length - (maskedText.length - offset), 0)
                    }

                    else -> {
                        offset - maskedText.take(offset).count { !it.isDigit() }
                    }
                }

            private fun offsetMaskCount(offset: Int, maskedText: String): Int {
                var maskOffsetCount = 0
                var dataCount = 0

                for (maskChar in maskedText) {
                    if (!maskChar.isDigit()) maskOffsetCount++
                    else if (++dataCount > offset) break
                }

                return maskOffsetCount
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber")
@Preview
@Composable
fun PreviewMaskVisualTransformation() {
    var text by remember { mutableStateOf("9.f017474464610") }
    TextField(
        value = text,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {
            text = it
        },
        placeholder = @Composable {
            Text(text = "Enter Value")
        },
        visualTransformation = MaskVisualTransformation(showZeroValue = false),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
}