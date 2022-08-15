package com.eng.selfsuggestion.view.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.databinding.FragmentHomeBinding
import com.eng.selfsuggestion.model.ArrivedModel
import com.eng.selfsuggestion.model.SendingModel
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import com.eng.selfsuggestion.repository.SendingRepository
import com.eng.selfsuggestion.view.dialog.WriteSpellDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var _binding : FragmentHomeBinding
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding.btnWriteSpell.setOnClickListener {
            val dialogFragment = WriteSpellDialogFragment()
            dialogFragment.show(parentFragmentManager, "WriteSpellDialogFragment")

        }
        scopeIO = CoroutineScope(Dispatchers.IO)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
        // 파이어베이스에서 랜덤으로 데일리 가져오기
        scopeIO.launch { 
            val routineRef = RoutineRepository
            val sendingRef = SendingRepository
            val daily = routineRef.getRandomRoutine()
            val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)!!

            with(pref.edit()) {
                putString("docId", daily.docId)
                commit()
            }
            // get random arrived : return random element
            val list = ArrivedRepository.getMessage()
            
            // 오늘 온 스페셜 메세지가 없을경우
            if (list.size <=0){
                val special_other =sendingRef.getRandomSending()

                // 다른사람이 보낸 메세지로 대체한다.
                with(Dispatchers.Main){
                    _binding.txtDailySpell.text = special_other.content
                }
            }else{
                val random = (0..list.size).random()  // 1 <= n <= 20
                val special_special = list.get(random)

                // 오늘 온 메세지 내용을 보여준다.
                with(Dispatchers.Main){
                    _binding.txtDailySpell.text = special_special.content
                }
            }

            // show daily random
            with(Dispatchers.Main){
                _binding.txtTodaySpell.text = daily.content
            }
        }
    }
}