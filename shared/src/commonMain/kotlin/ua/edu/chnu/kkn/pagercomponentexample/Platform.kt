package ua.edu.chnu.kkn.pagercomponentexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform