package io.github.keep2iron.rxresult.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.button

class TargetActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_test)
    button.setOnClickListener {
      setResult(RESULT_OK, Intent().apply {
        putExtra("intData", 0)
        putExtra("strData", TargetActivity::class.java.name)
      })
      finish()
    }
  }

}