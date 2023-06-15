package com.linggash.nutrifruity.ui.setting

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
import com.linggash.nutrifruity.databinding.FragmentSettingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref : SettingPreferences

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        pref = SettingPreferences.getInstance(requireContext().dataStore)
        var isOn : Boolean
        runBlocking {
            isOn = pref.getSoundSetting().first()
        }
        setView(isOn)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setView(isOn: Boolean){
        if (!isOn){
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
        }else {
            spLoaded = false
        }
        binding.switchSound.isChecked = isOn
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            runBlocking {
                pref.saveSoundSetting(isChecked)
            }
        }
    }
}