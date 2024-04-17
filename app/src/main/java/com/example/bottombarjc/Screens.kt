package com.example.bottombarjc

//sealed class (закрытый класс) - класс, который имеет предопределенные (конечные) набор подклассов
sealed class Screens(val screen:String) {
    data object Home:Screens("home")//каждый data object представляет определенный экран
    // и идентифицируется соответсвующим строковым идентификатором
    data object Search:Screens("search")
    data object Notification:Screens("notification")
    data object Profile:Screens("profile")
    data object Post:Screens("post")
}