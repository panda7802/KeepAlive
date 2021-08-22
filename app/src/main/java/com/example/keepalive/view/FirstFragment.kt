package com.example.keepalive.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.keepalive.KeepAliveConstants
import com.example.keepalive.R
import kotlinx.android.synthetic.main.fragment_1st.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_1st, container, false)
//        v.findViewById<Button>(R.id.btNext1).setOnClickListener {
////            Navigation.findNavController(v).navigate(R.id.action_placeholder_to_secondFragment)
////            activity?.let { it1 ->
////                Navigation.findNavController(it1, R.id.demo_fragment)
////                    .navigate(R.id.action_placeholder_to_secondFragment)
////            }
////            activity?.supportFragmentManager.findFragmentById(R.id.demo_fragment).getN
//            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.demo_fragment) as NavHostFragment
//            val navController = navHostFragment.navController
//            navController.navigate(R.id.action_placeholder_to_secondFragment)
//        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btNext1.setOnClickListener{
            Log.i(KeepAliveConstants.TAG,"To 2dn")
            Navigation.findNavController(view).navigate(R.id.action_placeholder_to_secondFragment)
        }
    }
}