package com.mg.plugin

import java.awt.Component
import javax.swing.DefaultListModel
import javax.swing.JLabel
import javax.swing.JList

fun makeJListComponent(list : DefaultListModel<String>, name: String = ""): JList<String> {
    val jList = JList<String>(list)
    jList.setVisibleRowCount(4)
    jList.setFixedCellHeight(20)
    jList.setFixedCellWidth(600)
    jList.setName(name)
    return jList
}

fun makeJLabelComponent(label : String, name: String = ""): JLabel {
    val label = JLabel(label)
    label.name = name
    label.alignmentX = Component.CENTER_ALIGNMENT
    return label
}