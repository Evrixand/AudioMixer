package com.example.audiomixer.ui.mixer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.audiomixer.IP_COMP
import com.example.audiomixer.PORT_COMP
import com.example.audiomixer.adapters.MixerAdapter
import com.example.audiomixer.databinding.FragmentMixerBinding
import com.example.audiomixer.model.AudioProgram
import com.example.audiomixer.model.RequestType
import com.example.audiomixer.model.SmartRequest
import com.example.audiomixer.requests.AsyncRequest
import com.example.audiomixer.ui.programs.ProgramsViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Suppress("DEPRECATION")
class MixerFragment : Fragment() {
    private var _binding: FragmentMixerBinding? = null
    lateinit var adapter: MixerAdapter
    private var programList: ArrayList<AudioProgram> = arrayListOf()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mixerViewModel =
            ViewModelProvider(this).get(MixerViewModel::class.java)

        _binding = FragmentMixerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //msgText = binding.msgText

        initRcView()

        //getListAudioProgram()

        binding.btnConnect.setOnClickListener(){
            getListAudioProgram()
        }


        return root
    }

    private fun getListAudioProgram(){

        val json = Json {
            encodeDefaults = true
        }

        programList.clear()
        try {
            val updateRequest = SmartRequest()
            updateRequest.Type = RequestType.update.value

            val requestJson = json.encodeToString(updateRequest)

            val myRequest = AsyncRequest()
            myRequest.execute(IP_COMP, PORT_COMP.toString(), requestJson)

//            var preDecodedItem = SmartRequest()
//            try {
//                preDecodedItem = json.decodeFromString(myRequest.get())
//            }
//            catch (e: Exception){
//                if (preDecodedItem.Type == ResponseType.failed.value){
//                    Toast.makeText(getContext(), "Connect failed", Toast.LENGTH_SHORT).show()
//                    APP.navController.navigate(R.id.action_navigation_mixer_to_navigation_init)
//                }
//                return
//            }


            if (!myRequest.get().isEmpty()){
                val decodedItem = json.decodeFromString<List<AudioProgram>>(myRequest.get())


                for (item in decodedItem){
                    programList.add(item)
                }
                adapter.refreshPersons(programList)
            }
        }
        catch (e: Exception){
            Log.d("requestErrors", e.message.toString())
        }
    }

    private fun initRcView() = with(binding){
        adapter = MixerAdapter()
        adapter.submitList(programList)
        mixerListView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}