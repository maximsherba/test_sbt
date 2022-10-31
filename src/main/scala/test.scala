import scala.io.{BufferedSource, Source}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.scalaland.chimney.dsl._

import java.io.FileWriter
import model._

/*
Загрузите файл с географическими данными различных стран (https://raw.githubusercontent.com/mledoze/countries/master/countries.json)
Среди стран выберите 10 стран Африки с наибольшей площадью.
Запишите данные о выбранных странах в виде JSON-массива объектов следующей структуры:
[{
“name”: <Официальное название страны на английском языке, строка>,
“capital”: <Название столицы, строка>(если столиц перечислено несколько, выберите первую),
“area”: <Площадь страны в квадратных километрах, число>,
}]
Обеспечьте проект инструкциями для сборки JAR-файла, принимающего на вход имя выходного файла и осуществляющего запись в него.
 */

object test extends App {
  val conf = new Conf(args)
  val source = Source
    .fromURL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json")
    .mkString
  val result = parser.decode[List[Countries]](source) match {
    case Left(error) => throw new RuntimeException(("JSON is incorrect"))
    case Right(country) =>
      val filtered: List[Countries] = country
        .filter(_.region == "Africa")
        .sortBy(_.area)(Ordering[Double].reverse)
        .splitAt(10)
        ._1
      val transformed: List[Export] = filtered.filter(c => c.capital.nonEmpty).map { el =>
        el.into[Export]
          .withFieldConst(_.name, el.name.official)
          .withFieldConst(_.capital, el.capital.head)
          .transform
      }
      transformed
  }
  //val countries: List[Countries] = parser.decode[List[Countries]](source) match {
  //  case Left(error) => throw new RuntimeException(("JSON is incorrect"))
  //  case Right(country) =>
  //    country
  //      .filter(_.region == "Africa")
  //      .sortBy(_.area)(Ordering[Double].reverse)
  //      .splitAt(10)
  //      ._1
  //}
  //val event = countries.map {element =>
  //  element
  //    .into[Export]
  //    .withFieldRenamed(_.name.official, _.name)
  //    .transform
  //}
  //val res: List[Export] = {
  //  countries
  //    .filter(c => c.capital.nonEmpty)
  //    .map(c => Export(name = c.name.official, capital = c.capital.head, area = c.area))
  //}
  val fileName   = conf.output.getOrElse(default = "")
  val fileWriter = new FileWriter(fileName)

  //fileWriter.write(result.asJson.toString())
  fileWriter.write(result.asJson.noSpaces)
  fileWriter.close()

  println(s"JSON $fileName is written succesfully")
}
