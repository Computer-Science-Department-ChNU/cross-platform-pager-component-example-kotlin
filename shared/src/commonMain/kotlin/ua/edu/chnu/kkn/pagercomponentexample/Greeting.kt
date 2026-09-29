package ua.edu.chnu.kkn.pagercomponentexample

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}