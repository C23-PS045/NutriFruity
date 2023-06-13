package com.linggash.nutrifruity.ui.game

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.databinding.ActivityGameBinding
import com.linggash.nutrifruity.model.FruitChoice
import com.linggash.nutrifruity.ui.ViewModelFactory

class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this@GameActivity)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]

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
                    imgFruit.setContent {
                        AsyncImage(
                            model = listChoice[number].image,
                            contentDescription = getString(R.string.fruit),
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(200.dp)
                        )
                    }
                    tvChoice1.text = listChoice[number].listChoice[0]
                    tvChoice2.text = listChoice[number].listChoice[1]
                    tvChoice3.text = listChoice[number].listChoice[2]

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
            val tvTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
            tvTitle.text = getString(R.string.correct)
            val btn = dialog.findViewById<Button>(R.id.btnDialogNext)
                btn.setOnClickListener {
                    dialog.dismiss()
                    viewModel.next()
                }
            val img = dialog.findViewById<ImageView>(R.id.imgFruitDialog)
            Glide
                .with(this)
                .load(fruitChoice.image)
                .centerCrop()
                .override(500)
                .into(img)
            val tvFruit = dialog.findViewById<TextView>(R.id.tvThis)
            tvFruit.text = getString(R.string.this_is_fruit) + " " + fruitChoice.answer
            dialog.show()
        } else{
            val tvTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
            tvTitle.text = getString(R.string.wrong)
            val btn = dialog.findViewById<Button>(R.id.btnDialogNext)
            btn.text = resources.getString(R.string.retry)
            btn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

}