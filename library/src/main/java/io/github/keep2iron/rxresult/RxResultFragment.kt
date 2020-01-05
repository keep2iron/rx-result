package io.github.keep2iron.rxresult

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author keep2iron <a href="http://keep2iron.github.io">Contract me.</a>
 * @version 1.0
 * @since 2018/07/20 21:19
 */
class RxResultFragment : Fragment() {

  private val subjects = HashMap<String, PublishSubject<Result>>()

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (!didStart()) {
      startActivityForResult(getLaunchIntent(), requestCode())
      didStart(true)
    }
  }

  companion object {
    private const val KEY_LAUNCH_INTENT = "launch_intent"
    private const val KEY_DID_ATTACH = "did_attach"
    private const val KEY_REQUEST_CODE = "request_code"

    const val SUBJECT_TAG = "hock_fragment_%d"

    @JvmStatic
    fun newInstance(
      intent: Intent,
      requestCode: Int
    ): RxResultFragment {
      val hockFragment = RxResultFragment()
      val arguments = Bundle()
      arguments.putParcelable(KEY_LAUNCH_INTENT, intent)
      arguments.putInt(KEY_REQUEST_CODE, requestCode)
      hockFragment.arguments = arguments

      return hockFragment
    }
  }

  @CheckResult private fun getLaunchIntent(): Intent {
    return arguments?.getParcelable(
      KEY_LAUNCH_INTENT
    ) ?: throw IllegalStateException(
      "No launch intent provided"
    )
  }

  fun launchIntent() {
    startActivityForResult(getLaunchIntent(), requestCode())
    didStart(true)
  }

  private fun didStart(): Boolean {
    return arguments?.getBoolean(KEY_DID_ATTACH) ?: throw IllegalStateException(
      "No arguments provided"
    )
  }

  private fun didStart(didStart: Boolean) {
    arguments?.putBoolean(KEY_DID_ATTACH, didStart) ?: throw IllegalStateException(
      "No arguments provided"
    )
  }

  private fun requestCode(): Int {
    return arguments?.getInt(KEY_REQUEST_CODE) ?: throw IllegalStateException(
      "No arguments provided"
    )
  }

  fun reset(
    intent: Intent,
    requestCode: Int
  ) {
    arguments?.putParcelable(KEY_LAUNCH_INTENT, intent) ?: throw IllegalStateException(
      "No arguments provided"
    )
    arguments?.putInt(KEY_REQUEST_CODE, requestCode) ?: throw IllegalStateException(
      "No arguments provided"
    )
  }

  fun setSubject(tag: String, subject: PublishSubject<Result>) {
    subjects[tag] = subject
  }

  fun getSubject(tag: String): PublishSubject<Result>? {
    return subjects[tag]
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    if (requestCode == this.requestCode()) {
      val subject = subjects.remove(SUBJECT_TAG.format(requestCode))

      if (subject == null) {
        Log.e(
          RxResult.TAG,
          "RxResult.onActivityResult invoked but didn't find the corresponding activity request."
        )
        return
      }

      if (data != null) {
        subject.onNext(Result(data, resultCode == Activity.RESULT_OK))
      } else {
        subject.onNext(Result(Intent(), resultCode == Activity.RESULT_OK))
      }
      subject.onComplete()
    }
  }
}