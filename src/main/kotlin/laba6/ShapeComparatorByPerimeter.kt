package laba6

import laba3.CalcShape

class ShapeComparatorByPerimeter : Comparator<CalcShape> {
    @Override
    override fun compare(shape1: CalcShape, shape2: CalcShape): Int {
        return shape1.calcPerimeter().compareTo(shape2.calcPerimeter())
    }
}