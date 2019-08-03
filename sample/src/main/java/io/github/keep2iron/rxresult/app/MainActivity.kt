package io.github.keep2iron.rxresult.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment()
            )
            .commit()

//        button1.setOnClickListener {
//            RxResult(this)
//                .prepare<TargetActivity>()
//                .requestForResult()
//                .filter {
//                    it.result
//                }
//                .subscribe {
//                    Toast.makeText(this, "button1 it : ${it.data.getStringExtra("strData")}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//        }
//        button2.setOnClickListener {
//            startActivityForResult<Target2Activity> { success, data ->
//                if (success) {
//                    Toast.makeText(this, "button2 it : ${data?.getStringExtra("strData")}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//        button3.setOnClickListener {
//            //            startActivityForResult(Intent(this, Target3Activity::class.java), 0x1024)
//            RxResult(this)
//                .prepare<Target3Activity>()
//                .requestForResult()
//                .subscribe {
//                    Toast.makeText(this, "button3 it : ${it.data.getStringExtra("strData")}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//        }
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Toast.makeText(this, "onActivityResult it : ${data?.getStringExtra("strData")}", Toast.LENGTH_SHORT)
//            .show()
//    }
}
