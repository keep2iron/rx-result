package io.github.keep2iron.rxresult

import android.content.Intent

data class Result(
  val data: Intent,
  val result: Boolean
)