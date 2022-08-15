package com.eng.selfsuggestion.view.navigation

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eng.selfsuggestion.databinding.FragmentHomeBinding
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
    private lateinit var pref : SharedPreferences

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
        val routineRef = RoutineRepository
        val arriveRef = ArrivedRepository
        val sendingRef = SendingRepository
        pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)!!

        routineRef.getRandomRoutine().observe(requireActivity(), {
            val routines = it
            Log.i(TAG, "getRandomRoutine: routines 내용" + routines)
            val random: Int = (0..(routines.size-1)).random()  // 1 <= n <= 20
            Log.i(TAG, "getRandomRoutine: routines 랜덤 데이터" + random + "/ " + routines.get(0))
            val daily =routines.get(random)
            Log.i(TAG, "onViewCreated: 랜덤 데일리 데이터"+daily)

            _binding.txtTodaySpell.text = daily.content

            with(pref.edit()) {
                putString("docId", daily.docId)
                commit()
            }

            Log.i(TAG, "onViewCreated: HomeFragment : 오늘의 데일리"+pref.getString("docId",""))

            // 오늘 들어온 메세지 가져오기
            arriveRef.getMessage().observe(requireActivity(),{
                val list = it

                Log.i(TAG, "onViewCreated: 오늘 들어온 메세지Home"+list+" / "+list.size)
                //  오늘 온 스페셜 메세지가 없을경우
                if (list.size <=0){
                    sendingRef.getRandomSending().observe(requireActivity(),{
                        val special_other = it
                        _binding.txtDailySpell.text = special_other.content
                        Log.i(TAG, "onViewCreated: 스페셜"+special_other)
                    })
                }else{
                    val ran = (0..(list.size-1)).random()  // 1 <= n <= 20
                    val special = list.get(ran)

                    // 오늘 온 메세지 내용을 보여준다.
                    _binding.txtDailySpell.text = special.content
                    Log.i(TAG, "onViewCreated: d오늘온거"+special)

                }

            })

        })

    }
}