package com.example.studying

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mask = MaskFormatWatcher(
            MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        )

        mask.installOn(binding.etPhone)

        binding.etPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}