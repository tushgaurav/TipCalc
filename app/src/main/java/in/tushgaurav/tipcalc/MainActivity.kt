package `in`.tushgaurav.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import org.w3c.dom.Text

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 10
private const val INITIAL_PERSON_COUNT = 1

class MainActivity : AppCompatActivity() {
    private lateinit var editBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var editPersonCount: TextView
    private lateinit var tvPerPersonAmount: TextView

    private lateinit var tipPercentLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editBaseAmount = findViewById(R.id.editBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tipPercentLabel = findViewById(R.id.tipPercentage)
        editPersonCount = findViewById(R.id.editNumberPerson)
        tvPerPersonAmount = findViewById(R.id.tvPerPersonCost)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        editPersonCount.text = "$INITIAL_PERSON_COUNT"



        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tipPercentLabel.text = "$p1%"
                computeTipAndTotal()
                computePerPersonCost()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        editBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
                computePerPersonCost()
            }

        })

        editPersonCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                computePerPersonCost()
            }

        })
    }

    private fun computePerPersonCost() {
        if (editPersonCount.text.isEmpty()) {
            tvPerPersonAmount.text = tvTotalAmount.text.toString()
            return }
        var personCount = editPersonCount.text.toString().toInt()
        var totalAmount = tvTotalAmount.text.toString().toDouble()
        var perPersonCost = totalAmount / personCount

        tvPerPersonAmount.text = perPersonCost.toString()
    }

    private fun computeTipAndTotal() {
        if (editBaseAmount.text.isEmpty()) {12
            tvTotalAmount.text = ""
            tvTipAmount.text = ""
            return
        }
        val baseAmount: Double = editBaseAmount.text.toString().toDouble()
        val tipPercentage: Int = seekBarTip.progress

        val tipAmount: Double = baseAmount * tipPercentage / 100
        val totalAmount: Double = baseAmount + tipAmount

        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}