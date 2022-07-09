import java.io.File

lateinit var results : List<Result>

fun main(args: Array<String>) {



    val fileName = "data.txt"

    var file = File(fileName)

    file = writeLines(file)

    val returnedList = readDataTxt(file)



    results = doLogic(returnedList)

    println("data.txt as list: $results")




}

fun doLogic(list : List<String>): List<Result> {

    var lstTemp : MutableList<Result> = mutableListOf<Result>()
    list.forEach { line ->
        run {
            val arrayTemp = line.split(",")
            if (arrayTemp[0] == "Type") {
                val tempResult = Result(arrayTemp[0], arrayTemp[1], arrayTemp[2], null, null, null,
                null)
                lstTemp.add(tempResult)
            } else {
                val tempResult = Result(
                    arrayTemp[0], null, null, arrayTemp[1].toDouble(),
                    arrayTemp[2].toDouble(), arrayTemp[3].toInt(), arrayTemp[4].toBoolean()
                )
                lstTemp.add(tempResult)
            }
        }
    }
    return lstTemp
}

fun writeLines(file : File) : File {

    file.writeText("Type,normal,Normal Price\n" +
            "Type,clearance,Clearance Price\n" +
            "Type,price_in_cart,Price In Cart\n" +
            "Product,59.99,39.98,10,false\n" +
            "Product,49.99,49.99,8,false\n" +
            "Product,79.99,49.98,5,false")

    return file

}

fun readDataTxt(file: File): List<String> {
    return file.readLines()
}





data class Result (
    val priceType : String? = null,
    val identification : String? = null,
    val displayName : String? = null,
    val regularPrice : Double? = null,
    val clearancePrice : Double? = null,
    val quantity : Int? = null,
    val priceInCart : Boolean? = null
)

