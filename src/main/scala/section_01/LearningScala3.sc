object LearningScala3 {
  // Functions
  
  // Format is def <function name>(parameter name: type...) : return type = { expression }
  // Don't forget the = before the expression!
  def squareIt(x: Int) : Int = {
  	x * x
  }                                               //> squareIt: (x: Int)Int
  
  def cubeIt(x: Int): Double = {
    java.lang.Math.pow(x, 2)
  }                                               //> cubeIt: (x: Int)Double

  def multiplyValues(x: Int, y: Int): Int = {x * y}
                                                  //> multiplyValues: (x: Int, y: Int)Int

  println(squareIt(2))                            //> 4
  println(cubeIt(2))                              //> 4.0
  println(multiplyValues(2, 3))                   //> 6
  
  // Functions can take other functions as parameters
  def transformNumberAndFunction(x: Int, f: Int => Double) : Double = {
  	f(x)
  }                                               //> transformNumberAndFunction: (x: Int, f: Int => Double)Double

  def transformTwoNumbersAndFunction(x: Int, y: Int, f: (Int, Int) => Int) : Int = {
    f(x, y)
  }                                               //> transformTwoNumbersAndFunction: (x: Int, y: Int, f: (Int, Int) => Int)Int

  val result = transformNumberAndFunction(2, cubeIt)
                                                  //> result  : Double = 4.0

  println (result)                                //> 4.0

  val result2 = transformTwoNumbersAndFunction(2, 3, multiplyValues)
                                                  //> result2  : Int = 6
  
  println(result2)                                //> 6
  
  // "Lambda functions", "anonymous functions", "function literals"
  // You can declare functions inline without even giving them a name
  // This happens a lot in Spark.
  transformNumberAndFunction(3, x => x + x)       //> res0: Double = 6.0
  
  transformNumberAndFunction(10, x => x / 2)      //> res1: Double = 5.0
  
  transformNumberAndFunction(2, x => {val y = x * 2; y * y})
                                                  //> res2: Double = 16.0
  
  // EXERCISE
  // Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
  // Write a function that converts a string to upper-case, and use that function of a few test strings.
  // Then, do the same thing using a function literal instead of a separate, named function.

  def transformToUpperCase(x: String): String = {
    x.toString().toUpperCase
  }                                               //> transformToUpperCase: (x: String)String

  val upperCase = transformToUpperCase("teste")   //> upperCase  : String = TESTE

}