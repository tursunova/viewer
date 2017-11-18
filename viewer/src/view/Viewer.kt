package view

import controller.*
import model.*
import Observer
import java.awt.*
import javax.swing.*
import java.awt.GridBagConstraints

abstract class ViewInterface(title : String?) : JFrame(title), Observer{
    fun showDialogMessage(message : String){
        JOptionPane.showMessageDialog(this, message)
    }
}


class Viewer(title: String?) : ViewInterface(title){

    var model: Model? = null
    var controller: ControllerInterface? = null
    var painter: Drawer? = null

    init {
        setBounds(100, 50, 600, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = GridBagLayout()

        val menuBar = JMenuBar()
        val menuFile = JMenu("File")

        val openFileItem = JMenuItem("Open")
        menuFile.add(openFileItem)
        val closeFileItem = JMenuItem("Close")
        menuFile.add(closeFileItem)

        openFileItem.addActionListener{

            val fileChooser = JFileChooser()
            fileChooser.showDialog(this, "open")
            val file = fileChooser.selectedFile

            if (file != null) {
                controller!!.readFile(file.absolutePath)
            }
        }

        closeFileItem.addActionListener {
            if (painter != null)
                this.remove(painter)
            repaint()
        }

        menuBar.add(menuFile)
        jMenuBar = menuBar

        isVisible = true
        controller = Controller(this)
    }


    override fun update(model: Model) {

        if (painter != null )
            remove(painter)
        repaint()

        when (model) {
            is BMP8 ->
                painter = BMP8Drawer(model)

            is BMP24 ->
                painter = BMP24Drawer(model)

            else ->
                println("unsupported format")
        }

        painter!!.setSize(model.width, model.height)

        val cns = getGridBagConstraints()
        contentPane.add(painter, cns)

        repaint()
        isVisible = true
    }

    fun getGridBagConstraints() : GridBagConstraints{
        val cns = GridBagConstraints()

        cns.weightx = 1.0
        cns.weighty = 1.0

        cns.ipadx = painter!!.width
        cns.ipady = painter!!.height

        cns.anchor = GridBagConstraints.CENTER
        return cns
    }
}

