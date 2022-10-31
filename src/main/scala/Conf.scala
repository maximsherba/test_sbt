import org.rogach.scallop.ScallopConf

class Conf (args: Seq[String]) extends  ScallopConf(args) {
  val output = opt[String](default = Some("./src/main/resources/result.json"), descr = "Output file")
  verify()
}
