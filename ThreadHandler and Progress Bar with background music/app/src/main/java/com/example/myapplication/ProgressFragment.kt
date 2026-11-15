package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressFragment : Fragment(R.layout.frag_progress) {
    private val vm: WorkViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bar = view.findViewById<CircularProgressIndicator>(R.id.progressBar)
        val txtStatus = view.findViewById<TextView>(R.id.txt)
        val txtProgress = view.findViewById<TextView>(R.id.txtProgress)

        bar.isIndeterminate = false
        bar.progress = 0
        txtProgress.text = "0%"

        vm.status.observe(viewLifecycleOwner) { s ->
            txtStatus.text = s
            when {
                s.startsWith("準備中") -> {
                    bar.isIndeterminate = true
                    txtProgress.text = "%"
                }
                s.startsWith("工作中") -> {
                    bar.isIndeterminate = false
                }
                s.startsWith("背景工作結束") -> {
                    bar.isIndeterminate = false
                    bar.progress = 100
                    txtProgress.text = "100%"
                }
                s.startsWith("背景工作中斷") -> {
                    bar.isIndeterminate = false
                }
            }
        }

        vm.progress.observe(viewLifecycleOwner) { p ->
            if (!bar.isIndeterminate) {
                bar.max = 100
                bar.progress = p
                txtProgress.text = "$p%"
            }
        }
    }
}