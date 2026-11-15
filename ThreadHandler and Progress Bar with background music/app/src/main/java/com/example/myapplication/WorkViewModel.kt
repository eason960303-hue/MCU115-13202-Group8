package com.example.myapplication

import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkViewModel : ViewModel() {
    private val handlerThread = HandlerThread("VM-Work").apply { start() }
    private val worker = Handler(handlerThread.looper)

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    private val _status = MutableLiveData("準備開始")
    val status: LiveData<String> = _status

    private val _isMusicPlaying = MutableLiveData(false)
    val isMusicPlaying: LiveData<Boolean> = _isMusicPlaying

    @Volatile private var running = false

    fun start() {
        if (running) return
        running = true
        _status.postValue("準備中...")
        _progress.postValue(0)
        _isMusicPlaying.postValue(false)

        worker.post {
            try {
                // Discovery / 準備工作
                for (step in 1..30) {
                    if (!running) break
                    Thread.sleep(100)
                }
                if (!running) {
                    _status.postValue("背景工作中斷")
                    _isMusicPlaying.postValue(false)
                    return@post
                }

                // 真正的背景工作
                _status.postValue("工作中...")
                _isMusicPlaying.postValue(true)
                for (i in 1..100) {
                    if (!running) break
                    Thread.sleep(100)
                    _progress.postValue(i)
                }

                if (running) {
                    _status.postValue("背景工作結束")
                } else {
                    _status.postValue("背景工作中斷")
                }
                _isMusicPlaying.postValue(false)
                running = false
            } catch (_: InterruptedException) {
                _status.postValue("背景工作中斷")
                _isMusicPlaying.postValue(false)
                running = false
            }
        }
    }

    fun cancel() {
        running = false
        _isMusicPlaying.postValue(false)
        _status.postValue("背景工作中斷")
    }

    override fun onCleared() {
        running = false
        _isMusicPlaying.postValue(false)
        handlerThread.quitSafely()
        super.onCleared()
    }
}