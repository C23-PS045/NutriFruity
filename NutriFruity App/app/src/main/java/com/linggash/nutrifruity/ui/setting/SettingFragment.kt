package com.linggash.nutrifruity.ui.setting

import android.content.Context
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.FragmentSettingBinding
import com.linggash.nutrifruity.ui.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: SettingViewModel

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext(), requireContext().dataStore)
        viewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]

        viewModel.getThemeSettings().observe(requireActivity()){
            setView(it)
        }
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
            viewModel.saveThemeSetting(isChecked)
        }
    }
}