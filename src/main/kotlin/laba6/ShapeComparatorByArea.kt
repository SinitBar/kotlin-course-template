package laba6

import laba3.CalcShape

class ShapeComparatorByArea : Comparator<CalcShape> {
    @Override
    override fun compare(shape1: CalcShape, shape2: CalcShape): Int {
        return shape1.calcArea().compareTo(shape2.calcArea())
    }
}



