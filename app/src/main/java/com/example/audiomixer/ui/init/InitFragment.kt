package com.example.audiomixer.ui.init

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.audiomixer.APP
import com.example.audiomixer.IP_COMP
import com.example.audiomixer.PORT_COMP
import com.example.audiomixer.R
import com.example.audiomixer.databinding.FragmentInitBinding
import com.example.audiomixer.model.RequestType
import com.example.audiomixer.requests.AsyncRequest
import com.example.audiomixer.model.SmartRequest
import com.example.audiomixer.requests.QuickRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InitFragment : Fragment() {
    private var _binding: FragmentInitBinding? = null
    private val binding get() = _binding!!

    private var IsLoaded = true
    private var Online = false

    private lateinit var viewModel: InitViewModel


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(InitViewModel::class.java)

        binding.message.text = "waiting for server..."

        UpdateRequestInfo()

        binding.btnUpdate.setOnClickListener(){
            UpdateRequestInfo()
            if (viewModel.online.value!!){
                //SwapActivity()
            }
        }
        return root
    }

    private fun SwapActivity(){
        Toast.makeText(getContext(), "Connected to server", Toast.LENGTH_SHORT).show()
        //APP.navController.navigate(R.id.action_navigation_init_to_navigation_mixer)
    }

    private fun UpdateRequestInfo() = with(binding){
        val json = Json {
            encodeDefaults = true
        }
        try {
            val updateRequest = SmartRequest()
            updateRequest.Type = RequestType.initRequest.value

            val requestJson = json.encodeToString(updateRequest)

//            val myRequest = AsyncRequest()
//            myRequest.execute(IP_COMP, PORT_COMP.toString(), requestJson)

            val quickRequest = QuickRequest()
            val quickResponse = quickRequest.sendRequestToServerWithTimeout(IP_COMP, PORT_COMP, requestJson)

            val response = json.decodeFromString<SmartRequest>(quickResponse)

            if (response.Data == "success"){
                viewModel.isOnline()
                binding.message.text = response.Data
                binding.btnUpdate.visibility = View.VISIBLE
                binding.btnUpdate.text = "ПОЧАТИ"
            }
            else{
                Online = false
                binding.btnUpdate.visibility = View.VISIBLE
                binding.btnUpdate.text = "ОНОВИТИ"
            }
        }
        catch (e: Exception){
            Log.d("UpdateRequestInfo",e.message.toString())
            viewModel.isOnline()
        }
    }
}