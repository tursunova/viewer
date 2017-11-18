package view

import model.Model
import java.awt.Color
import java.awt.Graphics

class BMP8Drawer (model: Model) : Drawer(model){

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        if (g == null) {
            return
        }

        var ind = model.pixelArray!!.size - 1

        val shift = (ind + 1) / model.height - model.width

        for (i in 0..model.height - 1) {
            ind -= shift
            for (j in model.width - 1 downTo 0) {

                var col = model.pixelArray!![ind --].toInt()

                if (col < 0) {
                    col += 256
                }
                col *= 4
                var blue = model.colorTable!![col++].toInt()
                if (blue < 0)
                    blue += 256

                var green = model.colorTable!![col++].toInt()
                if (green < 0)
                    green += 256

                var red = model.colorTable!![col].toInt()
                if (red < 0)
                    red += 256

                g.color = Color(red, green, blue)

                g.drawLine(j, i, j, i)
            }
        }
    }

}