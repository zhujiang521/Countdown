/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.countdown

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CountdownViewModel : ViewModel() {

    private val _showCountdown = MutableLiveData("00:00")
    val showCountdown: LiveData<String> = _showCountdown

    fun onCountdownChanged(position: String) {
        _showCountdown.value = position
    }

    private val _isActive = MutableLiveData(false)
    val isActive: LiveData<Boolean> = _isActive

    fun onActiveChanged(position: Boolean) {
        _isActive.value = position
    }

    fun startCountdown(second: Int) {
        viewModelScope.launch {
            object : CountDownTimer(second.toLong() * 1000, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    val shi = (millisUntilFinished / 10) % 10
                    val bai = (millisUntilFinished / 100) % 10
                    val mill = millisUntilFinished / 1000
                    Log.e("ZHUJIANG123", "onTick: $millisUntilFinished    $mill  $shi $bai ")
                    onCountdownChanged(
                        if (mill < 10) {
                            "0$mill:$bai$shi"
                        } else {
                            "$mill:$bai$shi"
                        }
                    )
                    onActiveChanged(true)
                }

                override fun onFinish() {
                    onActiveChanged(false)
                    onCountdownChanged("00:00")
                }
            }.start()
        }
    }
}
