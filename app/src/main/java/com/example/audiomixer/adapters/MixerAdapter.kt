package com.example.audiomixer.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.audiomixer.IP_COMP
import com.example.audiomixer.PORT_COMP
import com.example.audiomixer.SYSTEM_VOLUME
import com.example.audiomixer.databinding.MixerItemBinding
import com.example.audiomixer.model.AudioProgram
import com.example.audiomixer.model.RequestType
import com.example.audiomixer.requests.AsyncRequest
import com.example.audiomixer.model.SmartRequest
import com.google.android.material.slider.Slider
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MixerAdapter : ListAdapter<AudioProgram, MixerAdapter.ItemHolder>(ItemComparator()) {

    private var persons = emptyList<AudioProgram>()

    class ItemHolder(private val binding: MixerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(program: AudioProgram) = with(binding){

            if(program.IsMuted){
                btnMute.text = "M"
                sliderVolume.isEnabled = false
            }
            else{
                btnMute.text = ""
                sliderVolume.isEnabled = true
            }

            programName.text = program.Name
            sliderVolume.value = program.Volume
            sliderVolume.addOnChangeListener(){ slider: Slider, value: Float, b: Boolean ->
                program.Volume = value
                val myRequest = AsyncRequest()
                myRequest.execute(IP_COMP, PORT_COMP.toString(), serialisationProgram(program))
            }
            btnMute.setOnClickListener(){
                if (program.IsMuted){
                    btnMute.text = ""
                    program.IsMuted = false
                    sliderVolume.isEnabled = true
                    serialisationProgramRequest(program)

                    if (program.Name == SYSTEM_VOLUME){
                    }
                    else{

                    }
                }
                else {
                    btnMute.text = "M"
                    program.IsMuted = true
                    sliderVolume.isEnabled = false
                    serialisationProgramRequest(program)
                    if (program.Name == SYSTEM_VOLUME){

                    }
                    else{

                    }
                }
            }
        }
        fun serialisationProgram(program: AudioProgram): String{
            val json = Json {
                encodeDefaults = true
            }
            val changeVolumeRequest = SmartRequest()
            changeVolumeRequest.Type = RequestType.volume.value

            val programSer = json.encodeToString(program)
            changeVolumeRequest.Data = programSer

            return json.encodeToString(changeVolumeRequest)
        }
        fun serialisationProgramRequest(program: AudioProgram){
            val myRequest = AsyncRequest()
            myRequest.execute(IP_COMP, PORT_COMP.toString(), serialisationProgram(program))
        }
        fun serialisationAllPrograms(programs: List<AudioProgram>): String{
            val json = Json {
                encodeDefaults = true
            }
            val changeVolumeRequest = SmartRequest()
            changeVolumeRequest.Type = RequestType.masterVolume.value

            val programSer = json.encodeToString(programs)
            changeVolumeRequest.Data = programSer

            return json.encodeToString(changeVolumeRequest)
        }
        fun serialisationAllProgramsRequest(programs: List<AudioProgram>){
            val myRequest = AsyncRequest()
            myRequest.execute(IP_COMP, PORT_COMP.toString(), serialisationAllPrograms(programs))
        }
        companion object {
            fun create(parent: ViewGroup): ItemHolder{
                return  ItemHolder(MixerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }
    class ItemComparator : DiffUtil.ItemCallback<AudioProgram>(){
        override fun areItemsTheSame(oldItem: AudioProgram, newItem: AudioProgram): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AudioProgram, newItem: AudioProgram): Boolean {
            return oldItem.SessionId == newItem.SessionId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(persons[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshPersons(newPrograms: List<AudioProgram> ){
        this.persons = newPrograms
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewAttachedToWindow(holder: ItemHolder) {
        super.onViewAttachedToWindow(holder)
        val person = persons[holder.adapterPosition]

        holder.itemView.setOnClickListener(){

            //MainListFragment.nanToDetails(person)
        }
        holder.itemView.setOnLongClickListener(){

            //MainListFragment.navToEdit(person)

            return@setOnLongClickListener true
        }

    }

    override fun onViewDetachedFromWindow(holder: ItemHolder) {
        holder.itemView.setOnClickListener(null)
    }


}