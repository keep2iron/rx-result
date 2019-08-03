package io.github.keep2iron.rxresult.util

import android.content.Intent
import androidx.fragment.app.FragmentManager
import io.github.keep2iron.rxresult.Result
import io.github.keep2iron.rxresult.RxResultFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

internal object Schedules {

  private fun scheduleSubject(fragment: RxResultFragment, requestCode: Int) {
    val subjectTag = RxResultFragment.SUBJECT_TAG.format(requestCode)
    val subject = fragment.getSubject(RxResultFragment.SUBJECT_TAG.format(requestCode))
    if (subject == null) {
      fragment.setSubject(subjectTag, PublishSubject.create())
    }
  }

  fun schedule(
    supportFragmentManager: FragmentManager,
    intent: Intent,
    requestCode: Int
  ): Observable<Result> {
    var fragment: RxResultFragment? =
      supportFragmentManager.findFragmentByTag(
        RxResultFragment::class.java.name
      ) as RxResultFragment?
    if (fragment == null) {
      fragment = RxResultFragment.newInstance(intent, requestCode)
      scheduleSubject(fragment, requestCode)
    } else {
      fragment.reset(intent, requestCode)
      scheduleSubject(fragment, requestCode)
      fragment.launchIntent()
    }


    if (!fragment.isAdded) {
      supportFragmentManager.beginTransaction()
        .add(fragment, RxResultFragment::class.java.name)
        .commit()
    }

    val subjectTag = RxResultFragment.SUBJECT_TAG.format(requestCode)
    return fragment.getSubject(subjectTag)
      ?: throw IllegalArgumentException("$subjectTag corresponding subject not set.")
  }

  fun disposed(
    supportFragmentManager: FragmentManager
  ) {
    val fragment: RxResultFragment? =
      supportFragmentManager.findFragmentByTag(
        RxResultFragment::class.java.name
      ) as RxResultFragment?
    if (fragment != null) {
      supportFragmentManager.beginTransaction()
        .remove(fragment)
        .commitAllowingStateLoss()
    }
  }

}