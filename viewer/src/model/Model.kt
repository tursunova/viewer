package model

import Subject

interface Model: Subject {

    var width: Int
    var height : Int
    var size : Int
    var bytesForPixel : Int
    var pixelStartIndex : Int
    var countColorsInTable : Int
    var colorTable: ByteArray?
    var pixelArray: ByteArray?


    fun inflate(data: ByteArray)

    fun getValue(data: ByteArray, startIndex: Int, length: Int) : Int{
        var value = 0
        var order = 1

        for (shift in 0..length - 1) {
            var byte : Int = data[startIndex + shift].toInt()
            if (byte < 0)
                byte += 256
            value += ( byte * order )
            order *= 256
        }

        return value
    }

    override fun Notify() {
        for (observer in observers) {
            observer.update(this)
        }
    }

}