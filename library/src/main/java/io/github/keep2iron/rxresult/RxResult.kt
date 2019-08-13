package io.github.keep2iron.rxresult

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.github.keep2iron.rxresult.util.Schedules
import io.reactivex.Observable
import java.io.Serializable
import java.lang.ref.WeakReference

class RxResult private constructor() {

  companion object {
    const val TAG = "RxResult"
  }

  var activityRef: WeakReference<FragmentActivity>? = null
  var fragmentRef: WeakReference<Fragment>? = null

  var context: Context? = null

  var intent: Intent? = null

  constructor(fragment: Fragment) : this() {
    fragmentRef = WeakReference(fragment)
    context = fragment.context
    fragment.lifecycle.addObserver(object : LifecycleObserver {
      @OnLifecycleEvent(ON_DESTROY)
      fun onDestroy() {
        Schedules.disposed(fragment.childFragmentManager)
      }
    })
  }

  constructor(activity: FragmentActivity) : this() {
    activityRef = WeakReference(activity)
    context = activity
    activity.lifecycle.addObserver(object : LifecycleObserver {
      @OnLifecycleEvent(ON_DESTROY)
      fun onDestroy() {
        Schedules.disposed(activity.supportFragmentManager)
      }
    })
  }

  fun prepare(intent: Intent, vararg args: Pair<String, Any>): RxResult {
    for (arg in args) {
      when (val value = arg.second) {
        is String -> {
          intent.putExtra(arg.first, value)
        }
        is Int -> {
          intent.putExtra(arg.first, value)
        }
        is Double -> {
          intent.putExtra(arg.first, value)
        }
        is Float -> {
          intent.putExtra(arg.first, value)
        }
        is Byte -> {
          intent.putExtra(arg.first, value)
        }
        is Boolean -> {
          intent.putExtra(arg.first, value)
        }
        is Bundle -> {
          intent.putExtra(arg.first, value)
        }
        is Long -> {
          intent.putExtra(arg.first, value)
        }
        is Char -> {
          intent.putExtra(arg.first, value)
        }
        is Short -> {
          intent.putExtra(arg.first, value)
        }
        is Parcelable -> {
          intent.putExtra(arg.first, value)
        }
        is IntArray -> {
          intent.putExtra(arg.first, value)
        }
        is ByteArray -> {
          intent.putExtra(arg.first, value)
        }
        is FloatArray -> {
          intent.putExtra(arg.first, value)
        }
        is DoubleArray -> {
          intent.putExtra(arg.first, value)
        }
        is BooleanArray -> {
          intent.putExtra(arg.first, value)
        }
        is Serializable -> {
          intent.putExtra(arg.first, value)
        }
        is LongArray -> {
          intent.putExtra(arg.first, value)
        }
        is CharSequence -> {
          intent.putExtra(arg.first, value)
        }
      }
    }
    this.intent = intent

    return this
  }

  inline fun <reified T : Activity> prepare(
    vararg args: Pair<String, Any>
  ): RxResult {
    checkNotNull(context)
    return prepare(Intent(context, T::class.java), *args)
  }

  fun requestForResult(requestCode: Int = 0x1024): Observable<Result> {

    val fragmentManager = when {
      fragmentRef != null -> {
        fragmentRef?.get()?.childFragmentManager
          ?: throw IllegalArgumentException("No launch context")
      }
      activityRef != null -> {
        activityRef?.get()
          ?.supportFragmentManager ?: throw IllegalArgumentException("No launch context")
      }
      else -> {
        throw IllegalArgumentException("No launch context")
      }
    }

    checkNotNull(context)
    checkNotNull(intent) { "Do you call prepare() before ?" }

    val observable = Schedules.schedule(fragmentManager, intent!!, requestCode)

    context = null
    intent = null

    return observable
  }
}