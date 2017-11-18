package view

import model.Model
import java.awt.Color
import java.awt.Graphics


class BMP24Drawer(model: Model) : Drawer(model){

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        if (g == null) {
            return
        }

        var ind = model.pixelArray!!.size - 1
        val shift = (ind + 1) / model.height - 3 * model.width

        for (i in 0..model.height - 1) {

            ind -= shift

            for (j in model.width - 1 downTo 0) {

                var red = model.pixelArray!![ind --].toInt()
                if (red < 0)
                    red += 256

                var green = model.pixelArray!![ind --].toInt()
                if (green < 0)
                    green += 256

                var blue = model.pixelArray!! [ind --].toInt()
                if (blue < 0)
                    blue += 256

                g.color = Color(red, green, blue)
                g.drawLine(j, i, j, i)
            }

        }
    }

}