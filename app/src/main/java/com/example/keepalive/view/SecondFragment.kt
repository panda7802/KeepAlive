package com.example.keepalive.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.keepalive.KeepAliveConstants
import com.example.keepalive.R

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_2nd, container, false)
        Log.i(KeepAliveConstants.TAG,"onCreateView")
        v.findViewById<Button>(R.id.btPre).setOnClickListener {
            activity?.let { it1 ->
                Log.i(KeepAliveConstants.TAG, "back")
                Navigation.findNavController(it1, R.id.demo_fragment)
                    .navigate(R.id.action_secondFragment_to_placeholder)
            }
        }
        return v
    }

}