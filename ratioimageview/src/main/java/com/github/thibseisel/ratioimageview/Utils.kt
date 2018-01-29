/*
 * Copyright 2018 Thibault Seisel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.thibseisel.ratioimageview

/**
 * Parse the aspectRatio string property to a number.
 * The dimension string accepts the following formats:
 * - `W:H` where `W` is width and `H` the height (fraction form)
 * - `R` where R is the result of width / height (decimal form)
 *
 * @param dimensionRatio The dimension ratio encoded as a String.
 * Must follow one of the formats described above.
 * @return The decoded aspect ratio, or [Float.NaN] if input is incorrect.
 */
internal fun parseRatio(dimensionRatio: String?): Float {

    if (dimensionRatio != null) {
        val colonIndex = dimensionRatio.indexOf(':')
        if (colonIndex in 0 until dimensionRatio.length) {
            // "width:height" format. Separate nominator and denominator.
            val nominator = dimensionRatio.substring(0, colonIndex)
            val denominator = dimensionRatio.substring(colonIndex + 1)

            if (nominator.isNotEmpty() && denominator.isNotEmpty()) {
                try {
                    val nominatorValue = nominator.toFloat()
                    val denominatorValue = denominator.toFloat()

                    if (nominatorValue > 0f && denominatorValue > 0f) {
                        return nominatorValue / denominatorValue
                    }

                } catch (nfe: NumberFormatException) {
                    // Ignored format exception
                }
            }
        } else if (dimensionRatio.isNotEmpty()) {
            // "ratio" format. Convert to float if possible.
            try {
                val ratio = dimensionRatio.toFloat()
                return if (ratio > 0f) ratio else Float.NaN
            } catch (nfe: NumberFormatException) {
                // Ignored format exception
            }
        }
    }

    // Disable aspect ratio when String cannot be parsed
    return Float.NaN
}