package com.linggash.nutrifruity.ui.game

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.data.SettingPreferences
import com.linggash.nutrifruity.data.dataStore
import com.linggash.nutrifruity.databinding.ActivityGameBinding
import com.linggash.nutrifruity.model.FruitChoice
import com.linggash.nutrifruity.ui.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: GameViewModel

    private lateinit var sp: SoundPool
    private var soundIdPop: Int = 0
    private var soundIdTrue: Int = 0
    private var soundIdWrong: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        factory = ViewModelFactory.getInstance(this@GameActivity)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]

        var pref : Boolean
        runBlocking {
            pref = SettingPreferences.getInstance(dataStore).getSoundSetting().first()
        }
        setView(pref)
    }

    private fun setView(isOn: Boolean) {
        if (isOn){
            sp.setOnLoadCompleteListener{ _, _, status ->
                if (status == 0){
                    spLoaded = true
                }else {
                    Toast.makeText(this, "Gagal load", Toast.LENGTH_SHORT).show()
                }
            }
            soundIdPop = sp.load(this, R.raw.btn, 1)
            soundIdWrong = sp.load(this, R.raw.wrong, 1)
            soundIdTrue = sp.load(this, R.raw.right, 1)
        }else{
            spLoaded = false
        }
        viewModel.getFruit().observe(this){ result ->
            if (result != null){
                when (result){
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val listChoice = Game.getChoice(result.data)
                        startGame(listChoice)

                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Tidak ada koneksi Internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun startGame(listChoice: List<FruitChoice>) {
        viewModel.number.observe(this){ number ->
            if (number < listChoice.size){
                val dialog = Dialog(this@GameActivity)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.game_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

                binding.apply {
                    Glide.with(this@GameActivity)
                        .load(listChoice[number].image)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_gallery).error(R.drawable.ic_error))
                        .into(binding.imgFruit)
                    binding.imgFruit.setColorFilter(getColor(R.color.white))
                    choice1.text = listChoice[number].listChoice[0]
                    choice2.text = listChoice[number].listChoice[1]
                    choice3.text = listChoice[number].listChoice[2]

                    choice1.setOnClickListener {
                            onClickChoice(listChoice[number], 0, dialog)
                    }
                    choice2.setOnClickListener {
                            onClickChoice(listChoice[number], 1, dialog)
                    }
                    choice3.setOnClickListener {
                            onClickChoice(listChoice[number], 2, dialog)
                    }
                }
            }else{
                val dialog = Dialog(this@GameActivity)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.game_finish_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                dialog.findViewById<Button>(R.id.btnDialogFinish).setOnClickListener {
                    finish()
                }
                dialog.show()
            }

        }
    }

    private fun onClickChoice(
        fruitChoice: FruitChoice,
        number: Int,
        dialog: Dialog
    ) {
        if (fruitChoice.listChoice[number] == fruitChoice.answer){
            if (spLoaded) {
                sp.play(soundIdTrue, 1f, 1f, 0, 0, 1f)
            }
            val tvTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
            tvTitle.text = getString(R.string.correct)
            val btn = dialog.findViewById<Button>(R.id.btnDialogNext)
            btn.setOnClickListener {
                if (spLoaded) {
                    sp.play(soundIdPop, 1f, 1f, 0, 0, 1f)
                }
                dialog.dismiss()
                viewModel.next()
            }
            btn.text = getString(R.string.next)
            val img = dialog.findViewById<ImageView>(R.id.imgFruitDialog)
            Glide
                .with(this)
                .load(fruitChoice.image)
                .centerCrop()
                .override(500)
                .into(img)
            val tvFruit = dialog.findViewById<TextView>(R.id.tvThis)
            tvFruit.visibility = View.VISIBLE
            val text = getString(R.string.this_is_fruit) + " " + fruitChoice.answer
            tvFruit.text = text
            dialog.show()
        } else{
            if (spLoaded) {
                sp.play(soundIdWrong, 1f, 1f, 0, 0, 1f)
            }
            val tvTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
            tvTitle.text = getString(R.string.wrong)
            val btn = dialog.findViewById<Button>(R.id.btnDialogNext)
            btn.text = resources.getString(R.string.retry)
            btn.setOnClickListener {
                if (spLoaded) {
                    sp.play(soundIdPop, 1f, 1f, 0, 0, 1f)
                }
                dialog.dismiss()
            }
            val tvFruit = dialog.findViewById<TextView>(R.id.tvThis)
            tvFruit.visibility = View.GONE
            dialog.show()
        }
    }

}