package ru.aqsi.aqsidemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import ru.aqsi.commons.models.Cashier
import ru.aqsi.commons.models.printingModels.FeedPaper
import ru.aqsi.commons.models.printingModels.QrShtrihCodePrintingModel
import ru.aqsi.commons.models.printingModels.StringPrintingModel
import ru.aqsi.commons.receivers.AqsiResultReceiver
import ru.aqsi.commons.rmk.*
import java.util.UUID


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
            +StringPrintingModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed dictum nibh nisi, quis posuere ex cursus et. Maecenas mattis ullamcorper enim, tempor dignissim magna. Integer eu felis nec dui sagittis egestas eu a mi. Donec nibh orci, scelerisque eget dui in, commodo tincidunt leo. Integer condimentum finibus diam id congue. Aenean vestibulum vitae leo vitae venenatis. Curabitur urna quam, euismod eget lobortis quis, tempor vel tortor. Vestibulum risus lectus, vehicula vel ipsum et, convallis congue quam. Vivamus leo sapien, lacinia a efficitur vel, tristique feugiat felis. Nulla non bibendum eros. Nunc congue elit sapien, tempor laoreet elit feugiat vel. Etiam posuere bibendum justo in hendrerit. Aenean ultricies erat quis nulla iaculis malesuada. Suspendisse a enim mollis, luctus erat nec, vestibulum enim.", 10)
            +FeedPaper(5)
        }

    }
    private fun handleButtonClick2() {
        val activity = this
        getAllCashiers().forEach {
            if (it.cashierName == "" && it.cashierPosition ?: "" == "" && it.pin ?: "" == "" ) {
                if (ActiveCashierDB.setActiveCashier(activity, it.id ?: "")) {
                    val i = ActiveCashierDB.removeAllActiveCashiers(activity)
                    Log.d("DROP CASHIER", "Qty=${i}")
                }
            }
        }
        val list: List<Cashier> = getAllCashiers()
        list.firstOrNull()?.id?.let {
            ActiveCashierDB.setActiveCashier(activity, it)
        }
    }
    private fun handleButtonClick3() {
        val cashier = Cashier(
            cashierName = "New cashier",
            cashierPosition = Cashier.ORDINARY,
            pin = "1234",
            inn = "001122334455"
        )
        val out = createCashier(cashier)
        Toast.makeText(this, "Create cashier ${if (out)" OK" else "FAILED"}", Toast.LENGTH_LONG).show()
    }
    private fun handleButtonClick4() {
        val activity = this
        getAllCashiers().forEach {
            if (it.cashierName == "New cashier" ) {
                if (ActiveCashierDB.setActiveCashier(activity, it.id ?: "")) {
                    val out = ActiveCashierDB.removeAllActiveCashiers(activity)
                    Toast.makeText(this, "Cashier drop ${if (out == 1)" OK" else "FAILED"}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun handleButtonClick5() {
        val activity = this
        getAllCashiers().forEach {
            if (it.cashierPosition != Cashier.ADMIN && it.id ?: "" != "") {
                val cashier = Cashier(
                    cashierName = UUID.randomUUID().toString(),
                    cashierPosition = Cashier.ORDINARY,
                    pin = "1234",
                    inn = "001122334455"
                )
                val out = updateCashier(it.id ?: "", cashier)
                Toast.makeText(this, "Cashier update ${if (out)" SUCCESS" else "FAIL"}", Toast.LENGTH_LONG).show()
            }
        }

    }
}