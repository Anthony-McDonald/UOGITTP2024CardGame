// @GENERATOR:play-routes-compiler
// @SOURCE:/home/anthonymcdonald/Documents/Glasgow University Software Development/ITSD-DT2023-24-Template/conf/routes
// @DATE:Wed Jan 31 14:32:27 GMT 2024


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
