package io.github.keep2iron.rxresult.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afollestad.inlineactivityresult.startActivityForResult
import io.github.keep2iron.rxresult.RxResult
import kotlinx.android.synthetic.main.fragment_main.button1
import kotlinx.android.synthetic.main.fragment_main.button2
import kotlinx.android.synthetic.main.fragment_main.button3

class MainFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    button1.setOnClickListener {
      RxResult(this)
        .prepare<TargetActivity>()
        .requestForResult()
        .filter { it.result }
        .subscribe {
          Toast.makeText(
            requireContext(),
            "button1 it : ${it.data.getStringExtra("strData")}",
            Toast.LENGTH_SHORT
          ).show()
        }
    }
    button2.setOnClickListener {
      startActivityForResult<Target2Activity> { success, data ->
        if (success) {
          Toast.makeText(
            requireContext(),
            "button2 it : ${data?.getStringExtra("strData")}",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
    button3.setOnClickListener {
      startActivityForResult(Intent(requireContext(), Target3Activity::class.java), 0x1024)
//            RxResult(this)
//                .prepare<Target3Activity>()
//                .requestForResult()
//                .subscribe {
//                    Toast.makeText(requireContext(), "button3 it : ${it.data.getStringExtra("strData")}", Toast.LENGTH_SHORT)
//                        .show()
//                }
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    Toast.makeText(
      requireContext(),
      "onActivityResult resultCode :${resultCode} data : ${data?.getStringExtra("strData")}",
      Toast.LENGTH_SHORT
    )
      .show()
  }

}