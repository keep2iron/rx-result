![](images/banner.png)

# Rx-Result

![Release](https://api.bintray.com/packages/keep2iron/maven/rx-result/images/download.svg)![BuildStatus](https://travis-ci.org/keep2iron/rx-result.svg?branch=master)

**Rx-Rsult** wrapper startActivityResult some logic.

#### install

````groovy
dependencies {
    implementation 'io.github.keep2iron:rx-result:$latest_version'
}
````

#### Simple usage snippet

````kotlin
RxResult(this)
    .prepare<TargetActivity>()
    .requestForResult("arg1" to "value")  //with argument
    .filter { it.result }				  //filter intent resultCode == Activity.RESULT_OK
    .subscribe {
        Toast.makeText(
            requireContext(),
            "it : ${it.data.getStringExtra("strData")}",
            Toast.LENGTH_SHORT
        ).show()
    }
````

