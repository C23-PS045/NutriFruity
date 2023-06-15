package com.linggash.nutrifruity.ui.home

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.SettingPreferences
import com.linggash.nutrifruity.data.dataStore
import com.linggash.nutrifruity.databinding.FragmentHomeBinding
import com.linggash.nutrifruity.ui.camera.CameraActivity
import com.linggash.nutrifruity.ui.game.GameActivity
import com.linggash.nutrifruity.ui.list.FruitListActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pref : Boolean
        runBlocking {
            pref = SettingPreferences.getInstance(requireContext().dataStore).getSoundSetting().first()
        }
        setView(pref)
    }

    private fun setView(isOn: Boolean){
        if (isOn){
            sp = SoundPool.Builder()
                .setMaxStreams(10)
                .build()
            sp.setOnLoadCompleteListener{ _, _, status ->
                if (status == 0){
                    spLoaded = true
                }else {
                    Toast.makeText(requireActivity(), "Gagal load", Toast.LENGTH_SHORT).show()
                }
            }
            soundId = sp.load(requireContext(), R.raw.btn, 1)
        } else {
            spLoaded = false
        }
        binding.cvCameraCard.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            val intent = Intent(requireActivity(), CameraActivity::class.java)
            startActivity(intent)
        }
        binding.cvListCard.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            val intent = Intent(requireActivity(), FruitListActivity::class.java)
            startActivity(intent)
        }
        binding.cvGameCard.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            val intent = Intent(requireActivity(), GameActivity::class.java)
            startActivity(intent)
        }
    }
}