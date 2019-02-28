package examples

import scala.io.StdIn
import scala.util.control.Exception

class Thermometer {

  var celsius: Float = _

  def fahrenheit = celsius * 9 / 5 + 32

  def fahrenheit_=(f: Float): Unit = {
    celsius = (f - 32) * 5 / 9
  }

  override def toString: String = fahrenheit + "F/" + celsius + "C"

}

object Thermometer {

  def main(args: Array[String]): Unit = {

    val thermometer = new Thermometer

    println("Input Fahrenheit:")

    while (true){
      Exception.ignoring(classOf[NumberFormatException])
      thermometer.fahrenheit = StdIn.readFloat()
      println(thermometer)
    }

  }

}