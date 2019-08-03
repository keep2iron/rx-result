package io.github.keep2iron.rxresult

import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class RxjavaTest {
  @Test
  fun asyncSubject() {
    val subject = AsyncSubject.create<Int>()
    subject.onNext(0)
    subject.onNext(1)
    subject.subscribe {
      println(it)
    }
    subject.onNext(2)
    subject.onComplete()

  }

  @Test
  fun behaviorSubject() {
    val subject = BehaviorSubject.create<Int>()
    subject.subscribe {
      println(it)
    }
    subject.onNext(0)
    subject.onNext(1)
    subject.onNext(2)
  }
}