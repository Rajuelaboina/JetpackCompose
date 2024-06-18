package com.phycare.residentbeacon


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.phycare.residentbeacon.model.CompleteProgramSearchItem
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.ResidentCompleteSearchItem
import com.phycare.residentbeacon.model.Speciality
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResidentViewModel:ViewModel() {
    private val apiService = RetrofitInstance.api
   // var stateListResponse:List<StatesItem> by mutableStateOf(listOf())
    val stateListResponse = mutableStateListOf<StatesItem>()
   fun getAllStates(){
       stateListResponse.clear()
       stateListResponse.add(StatesItem("All"))
       viewModelScope.launch {
           val locationList = apiService.getStates()
           //stateListResponse = locationList
           stateListResponse.addAll(locationList)
       }
    }


    // Pgy data
   /* val nameList = mutableStateListOf<PGYItem>()
    var pgyListResponse: List<PGYItem> by mutableStateOf(listOf())
    fun getAllPGy(){
        viewModelScope.launch {
            val pgyList = apiService.getPGYDetails()
            pgyListResponse = pgyList
        }
    }
*/
    val pgyListResponse = mutableStateListOf<PGYItem>()
    fun getAllPGy(){
        pgyListResponse.clear()
        pgyListResponse.add(PGYItem("All"))
        viewModelScope.launch {
            val pgyList = apiService.getPGYDetails()
            pgyListResponse.addAll(pgyList)
        }
    }

   // Speciality
    // var specialityListResponse: List<SpecialityItem> by mutableStateOf(listOf())
      val specialityListResponse = mutableStateListOf<SpecialityItem>()
    fun getAllSpeciality(){
        specialityListResponse.clear()
        specialityListResponse.add(SpecialityItem("All"))
        viewModelScope.launch {
            val specialityList = apiService.getSpecialityDetails()
            //specialityListResponse = specialityList
            specialityListResponse.addAll(specialityList)
        }
    }
  // Complete search --------------------------
  var residentCompleteSearchListResponse: List<ResidentCompleteSearchItem> by mutableStateOf(listOf())
    fun getResidentCompleteSearch(resident: String,location: String,pgy: String){
        viewModelScope.launch {
            val completeList = apiService.getResidentCompleteSearch(resident,location,pgy)
            residentCompleteSearchListResponse = completeList
        }
        Log.e("View Size","residentCompleteSearchListResponse: ${residentCompleteSearchListResponse.size}")
    }

    var isLoading = mutableStateOf(false)
    private var _getUserData: MutableLiveData<List<ResidentCompleteSearchItem>> = MutableLiveData<List<ResidentCompleteSearchItem>>()
    var getUserData: LiveData<List<ResidentCompleteSearchItem>> = _getUserData

    fun getResidentCompleteSearch2(resident: String,location: String,pgy: String){
        viewModelScope.launch {
            val completeList = apiService.getResidentCompleteSearch(resident,location,pgy)

                isLoading.value = true
                _getUserData.value = completeList

          //  residentCompleteSearchListResponse2.value = completeList
        }
        //Log.e("View Size","residentCompleteSearchListResponse: ${residentCompleteSearchListResponse.size}")
    }


    // filter Complete search --------------------------
    var residentCompleteSearchList = mutableStateListOf<ResidentCompleteSearchItem>()
    fun getResidentCompleteSearchFilter(resident: String,locaton: String,pgy: String){
        viewModelScope.launch {
            val completeList = apiService.getResidentCompleteSearch(resident,locaton,pgy)
                //  residentCompleteSearchList.add(it)

          //  residentCompleteSearchListResponseFilter = completeList
        }

    }


    // Program complete Search

    var programComSearchListResponse: List<CompleteProgramSearchItem> by mutableStateOf(listOf())
    fun getProgramCompleteSearchData(programName:String, location:String, speciality:String){
       // CoroutineScope(IO).cancel()
        viewModelScope.launch {
            val programList = apiService.getProgramCompleteSearch(programName,location,speciality)
            programComSearchListResponse = programList
        }
    }

}