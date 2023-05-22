package ru.aqsi.aqsidemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import ru.aqsi.commons.models.printingModels.FeedPaper
import ru.aqsi.commons.models.printingModels.QrShtrihCodePrintingModel
import ru.aqsi.commons.receivers.AqsiResultReceiver
import ru.aqsi.commons.rmk.*


class MainActivity : AppCompatActivity() {
    private val button1 by lazy { findViewById<MaterialButton?>(R.id.main_button_test1) }
    private val button2 by lazy { findViewById<MaterialButton?>(R.id.main_button_test2) }
    private val button3 by lazy { findViewById<MaterialButton?>(R.id.main_button_test3) }
    private val button4 by lazy { findViewById<MaterialButton?>(R.id.main_button_test4) }
    private val button5 by lazy { findViewById<MaterialButton?>(R.id.main_button_test5) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener { this.handleButtonClick1() }
        button2.setOnClickListener { this.handleButtonClick2() }
        button3.setOnClickListener { this.handleButtonClick3() }
        button4.setOnClickListener { this.handleButtonClick4() }
        button5.setOnClickListener { this.handleButtonClick5() }
    }

    private fun handleButtonClick1() {
        val activity = this
        AqsiRMK.print(this, object : AqsiResultReceiver.ResultReceiverCallBack {
            override fun onError(exception: Exception?) {
                Toast.makeText(activity, exception?.message ?: "ERROR", Toast.LENGTH_LONG).show()
            }
            override fun onSuccess(data: String?) {
                Toast.makeText(activity, data ?: "SUCCESS", Toast.LENGTH_LONG).show()
            }
        }) {
            center { // Текст внутри блока будет выровнен по центру
                +"Текст по центру"
            }
            +FeedPaper(1) // Прокрутка бумаги, можно использовать любые экземпляры [BasePrintingModel]
            +("Текст слева" to "Текст справа") // 1-ый аргумент внутри скобок будет распечатан слева строки, 2-й аргумент справа. Это эквивалент +Pair("Текст слева", "Текст справа")
            largeSize {
                center {
                    +"ЦЕНТР" // Текст будет напечатан самым крупным шрифтом, а также будет выровнен по центру.
                }
            }
            +QrShtrihCodePrintingModel(qrString = "https://example.com")
        }

    }
    private fun handleButtonClick2() {

    }
    private fun handleButtonClick3() {

    }
    private fun handleButtonClick4() {

    }
    private fun handleButtonClick5() {

    }
}