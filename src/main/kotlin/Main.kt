import java.io.File


fun main() {
    val fileName = "data.txt"
    var file = File(fileName)
    file = writeLines(file)
    val returnedList = readDataTxt(file)
    val results = buildObjects(returnedList)
    val resString = doLogic(results)
    println(resString)
}

fun doLogic(results: List<Result>): String {

    var countRegPrice = 0
    var countClearancePrice = 0
    var clearanceHigh = 0.00
    var clearanceLow = 0.00
    var priceInCartCount = 0
    var regularPriceHigh = 0.00
    var regularPriceLow = 0.00


    results.forEach {

       if (it.quantity!! > 3) {
           if (it.regularPrice == it.clearancePrice) {

               if (regularPriceHigh == 0.00) {
                   regularPriceHigh = it.regularPrice!!
                   regularPriceLow = it.regularPrice
               }
               countRegPrice++
               if (regularPriceHigh < it.regularPrice!!)
                   regularPriceHigh = it.regularPrice
               if (regularPriceLow > it.regularPrice)
                   regularPriceLow = it.regularPrice
               if (it.cart == true)
                   priceInCartCount++

           }else {
               if (clearanceHigh == 0.00) {
                   clearanceHigh = it.clearancePrice!!
                   clearanceLow = it.clearancePrice
               }
               countClearancePrice++
               if (clearanceHigh < it.clearancePrice!!)
                   clearanceHigh = it.clearancePrice
               if (clearanceLow > it.clearancePrice)
                   clearanceLow = it.clearancePrice
               if (it.cart == true)
                   priceInCartCount++
           }
       }
    }

    if (countClearancePrice < 2) {
        return String.format(
            "Clearance Price : %d products  @ %.2f\n" +
                    "Normal Price : %d @ %.2f - %.2f\n" +
                    "Price in Cart : %d products", countClearancePrice, clearanceHigh,
            countRegPrice, regularPriceLow, regularPriceHigh, priceInCartCount
        )
    } else if (countRegPrice < 2) {
        return String.format(
            "Clearance Price : %d products  @ %.2f - %.2f\n" +
                    "Normal Price : %d @ %.2f\n" +
                    "Price in Cart : %d products", countClearancePrice, clearanceHigh, clearanceLow,
            countRegPrice, regularPriceHigh, priceInCartCount
        )
    } else {
        return String.format(
            "Clearance Price : %d products  @ %.2f - %.2f\n" +
                    "Normal Price : %d @ %.2f - %.2f\n" +
                    "Price in Cart : %d products", countClearancePrice, clearanceHigh, clearanceLow,
            countRegPrice, regularPriceLow, regularPriceHigh, priceInCartCount
        )
    }
}
fun buildObjects(list : List<String>): List<Result> {

    val lstTemp : MutableList<Result> = mutableListOf()
    list.forEach { line ->
        run {
            val arrayTemp = line.split(",")
            if (arrayTemp[0] == "Product") {
               if (arrayTemp[1].toDouble() > arrayTemp[2].toDouble()) {
                   val tempResult = Result(Type.CLEARANCE, arrayTemp[1].toDouble(),
                       arrayTemp[2].toDouble(), arrayTemp[3].toInt(), arrayTemp[4].toBoolean())
                   lstTemp.add(tempResult)
               }else{
                   val tempResult = Result(Type.NORMAL, arrayTemp[1].toDouble(),
                       arrayTemp[2].toDouble(), arrayTemp[3].toInt(), arrayTemp[4].toBoolean())
                   lstTemp.add(tempResult)
               }
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
    val type : Type,
    val regularPrice: Double?,
    val clearancePrice: Double?,
    val quantity: Int?,
    val cart : Boolean?
    )

enum class Type {
    NORMAL,
    CLEARANCE
}

