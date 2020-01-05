package io.github.keep2iron.rxresult.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.button

class TargetActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_test)
    if (intent != null) {
      val stringBuilder = StringBuilder()
      if (intent.hasExtra("argInt")) {
        stringBuilder.append("argInt : " + intent.getIntExtra("argInt", 0)).append("\n")
      }
      if(intent.hasExtra("argFloat")){
        stringBuilder.append("argFloat : " + intent.getFloatExtra("argFloat", 0f)).append("\n")
      }
      if(intent.hasExtra("argDouble")){
        stringBuilder.append("argDouble : " + intent.getDoubleExtra("argDouble", 0.0)).append("\n")
      }
      Toast.makeText(this,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
    }
    button.setOnClickListener {
      setResult(RESULT_OK, Intent().apply {
        putExtra("intData", 0)
        putExtra("strData", TargetActivity::class.java.name)
      })
      finish()
    }
  }
}