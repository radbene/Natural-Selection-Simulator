package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MapDirectionTest : FunSpec({
    test("Test next") {
        MapDirection.NORTH.next() shouldBe MapDirection.EAST
        MapDirection.EAST.next() shouldBe MapDirection.SOUTH
        MapDirection.SOUTH.next() shouldBe MapDirection.WEST
        MapDirection.WEST.next() shouldBe MapDirection.NORTH
    }

    test("Test previous") {
        MapDirection.NORTH.previous() shouldBe MapDirection.WEST
        MapDirection.WEST.previous() shouldBe MapDirection.SOUTH
        MapDirection.SOUTH.previous() shouldBe MapDirection.EAST
        MapDirection.EAST.previous() shouldBe MapDirection.NORTH
    }

    test("Test toUnitVector") {
        MapDirection.NORTH.toUnitVector() shouldBe Vector2d(0, 1)
        MapDirection.EAST.toUnitVector() shouldBe Vector2d(1, 0)
        MapDirection.SOUTH.toUnitVector() shouldBe Vector2d(0, -1)
        MapDirection.WEST.toUnitVector() shouldBe Vector2d(-1, 0)
    }
})