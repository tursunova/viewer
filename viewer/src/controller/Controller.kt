package controller

import model.*
import view.ViewInterface
import java.io.FileInputStream
import java.io.IOException

interface ControllerInterface {

    fun readFile(file : String)
    fun createModel(data: ByteArray)

}

class Controller (var view: ViewInterface, var model : Model = BMP8()) : ControllerInterface{

    override fun readFile(file : String){

        val streamIn : FileInputStream
        try {
            streamIn = FileInputStream(file)
        }
        catch (e : IOException) {
            println("file not found")
            return
        }

        var data : ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)

        var file_extension = file.split(".")[1].subSequence(0, 2)
        var file_type = String(data.copyOfRange(0, 2))


        if (file_extension != file_type.toLowerCase()) {
            view.showDialogMessage("incorrect format")
            println("incorrect format")
            return
        }

        createModel(data)
    }

    override fun createModel(data: ByteArray) {
        var type : String = String(data.copyOfRange(0, 2))

        if (type == "BM") {
            when (data[0x1C].toInt()) {
                8 ->
                    if (model !is BMP8)
                        model = BMP8()
                24 ->
                    if (model !is BMP24)
                        model = BMP24()
            }
        } else {
            view.showDialogMessage("unsupported format")
            println("unsupported format")
            return
        }

        model.attachObserver(view)
        model.inflate(data)
    }
}