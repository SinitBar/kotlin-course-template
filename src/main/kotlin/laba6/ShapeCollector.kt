package laba6

import laba3.*

object ShapeComparators {
    val CompareByAreaAscending = ShapeComparatorByArea()
    val CompareByAreaDescending: Comparator<CalcShape> = ShapeComparatorByArea().reversed()
    val CompareByPerimeterAscending = ShapeComparatorByPerimeter()
    val CompareByPerimeterDescending: Comparator<CalcShape> = ShapeComparatorByPerimeter().reversed()
    val CompareCirclesByRadiusAscending = compareBy<Circle> { it.radius }
    val CompareCirclesByRadiusDescending: Comparator<Circle> = CompareCirclesByRadiusAscending.reversed()
}

class ShapeCollector<T : CalcShape> {

    private val allShapes = mutableListOf<T>()

    fun add(new: T) = allShapes.add(new) // adds a figure into collector

    fun addAll(newList: List<T>) = allShapes.addAll(newList)
    // adds a collection of figures into the collector

    fun getAll(): List<T> = allShapes.toList()
    // returns a list of all figures in the collector

    fun getAllSorted(comparator: Comparator<in T>): List<T> = getAll().sortedWith(comparator)
    // return all figures from the collector in order
    // specified by data comparator
    // in because it is not needed to return T and we should go up to parent class to use comparator for shape
    // when have a concrete figure (let circle/square/rectangle/triangle be shape)

    fun getAllByClass(what: Class<out T>): List<T> { // out is to let shape be circle/triangle/rectangle/square
        return allShapes.filter { element -> (element::class.java == what) }.toList()
    }
}