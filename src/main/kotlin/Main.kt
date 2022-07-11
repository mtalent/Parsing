import java.io.File


fun main() {
    val file = File("data.txt").also {
        writeLines(it)
    }
    val returnedList = readDataTxt(file)
    val results = buildObjects(returnedList)
    val counts = doLogic(results)
    formatStrings(counts).also {
        println(it)
    }
}

fun formatStrings(counts: Counts): String {

    val builder = StringBuilder()

    val stringClearance = if (counts.countClearancePrice < 2) {
        String.format("Clearance Price : %d products  @ %.2f"
            ,counts.clearanceHigh)
    }else {
        String.format("Clearance Price : %d products  @ %.2f - %.2f"
            ,counts.clearanceHigh, counts.clearanceLow)
    }
    val stringNormal = if (counts.countRegPrice < 2) {
        String.format("Regular Price : %d products  @ %.2f"
            ,counts.regularPriceHigh)
    }else {
        String.format("Regular Price : %d products  @ %.2f - %.2f"
            ,counts.regularPriceHigh, counts.regularPriceLow)
    }
    val stringCart = String.format("Price in Cart", counts.priceInCartCount)

    if (counts.countRegPrice > counts.countClearancePrice) {
        if (counts.priceInCartCount > counts.countRegPrice) {
            builder.append(stringCart)
                .appendLine(stringNormal)
                .appendLine(stringClearance)
        } else {
            if (counts.priceInCartCount > counts.countClearancePrice) {
                builder.append(stringNormal)
                    .appendLine(stringCart)
                    .appendLine(stringClearance)
            } else {
                builder.append(stringNormal)
                    .appendLine(stringClearance)
                    .appendLine(stringCart)
            }
        }


    }else {
        if (counts.priceInCartCount > counts.countClearancePrice) {
            builder.append(stringCart)
                .appendLine(stringClearance)
                .appendLine(stringNormal)
        } else {
            if (counts.priceInCartCount > counts.countRegPrice) {
                builder.append(stringClearance)
                    .appendLine(stringCart)
                    .appendLine(stringNormal)
            } else {
                builder.append(stringClearance)
                    .appendLine(stringNormal)
                    .appendLine(stringCart)
            }
        }
    }

    return  builder.toString()
}

fun doLogic(results: List<Result>): Counts {
/*
    var countRegPrice = 0
    var countClearancePrice = 0
    var clearanceHigh = 0.00
    var clearanceLow = 0.00
    var priceInCartCount = 0
    var regularPriceHigh = 0.00
    var regularPriceLow = 0.00
*/
    val count = Counts()

    results.forEach {

       if (it.quantity!! > 3) {
           if (it.regularPrice == it.clearancePrice) {

               if (count.regularPriceHigh == 0.00) {
                   count.regularPriceHigh = it.regularPrice!!
                   count.regularPriceLow = it.regularPrice
               }
               count.countRegPrice++
               if (count.regularPriceHigh < it.regularPrice!!)
                   count.regularPriceHigh = it.regularPrice
               if (count.regularPriceLow > it.regularPrice)
                   count.regularPriceLow = it.regularPrice
               if (it.cart == true)
                   count.priceInCartCount++

           }else {
               if (count.clearanceHigh == 0.00) {
                   count.clearanceHigh = it.clearancePrice!!
                   count.clearanceLow = it.clearancePrice
               }
               count.countClearancePrice++
               if (count.clearanceHigh < it.clearancePrice!!)
                   count.clearanceHigh = it.clearancePrice
               if (count.clearanceLow > it.clearancePrice)
                   count.clearanceLow = it.clearancePrice
               if (it.cart == true)
                   count.priceInCartCount++
           }
       }
    }

   return count
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

data class Counts (
    var countRegPrice: Int = 0,
    var countClearancePrice: Int = 0,
    var clearanceHigh: Double = 0.00,
    var clearanceLow: Double = 0.00,
    var priceInCartCount: Int = 0,
    var regularPriceHigh: Double = 0.00,
    var regularPriceLow: Double = 0.00
        )

enum class Type {
    NORMAL,
    CLEARANCE
}

