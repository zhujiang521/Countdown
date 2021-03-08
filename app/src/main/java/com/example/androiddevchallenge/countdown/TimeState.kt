/*
 * Copyright 2020 The Android Open Source Project
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

import java.util.regex.Pattern

// Consider an email valid if there's some text before and after a "@"
// private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
private const val SECOND_VALIDATION_REGEX = "^[0-9]{0,2}"

class SecondState :
    TextFieldState(validator = ::isSecondValid, errorFor = ::secondValidationError)

/**
 * Returns an error to be displayed or null if no error was found
 */
private fun secondValidationError(second: String): String {
    return "Invalid time: $second"
}

private fun isSecondValid(second: String): Boolean {
    return Pattern.matches(SECOND_VALIDATION_REGEX, second)
}
