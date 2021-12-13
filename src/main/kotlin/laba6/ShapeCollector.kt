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

    fun add(new: T) { // adds a figure into collector
        allShapes.add(new)
    }

    fun addAll(newList: List<T>) { // adds a collection of figures into the collector
        for (newElement in newList)
            allShapes.add(newElement)
    }

    fun getAll(): List<T> { // returns a list of all figures in the collector
        val list = mutableListOf<T>()
        for (element in allShapes)
            list.add(element)
        return list.toList()
    }

    fun getAllSorted(comparator: Comparator<in T>): List<T> = getAll().sortedWith(comparator)
    // return all figures from the collector in order
    // specified by data comparator

    inline fun <reified A : T> getAllByClass(what: Class<A>): List<A> { // return the shapes of Class<A> type
        val list = mutableListOf<A>()
        for (element in getAll())
            if (element is A) list.add(element)
        return list.toList()
    }
}