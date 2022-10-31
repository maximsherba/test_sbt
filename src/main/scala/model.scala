object model {

  case class Countries(name: Name, capital: Seq[String], region: String, area: Double)

  case class Name(official: String)

  case class Export(name: String, capital: String, area: Double)

}
