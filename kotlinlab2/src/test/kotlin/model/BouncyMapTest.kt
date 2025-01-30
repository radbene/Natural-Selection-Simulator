package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn

class BouncyMapTest : FunSpec({
    test("place animal") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(2, 2))
        map.place(animal)
        map.objectAt(Vector2d(2, 2)) shouldBe animal
        map.getElements() shouldHaveSize 1
    }

    test("move animal") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(2, 2))
        map.place(animal)
        map.move(animal, MoveDirection.FORWARD)
        map.objectAt(Vector2d(2, 2)) shouldBe null
        map.objectAt(Vector2d(2, 3)) shouldBe animal
        map.getElements() shouldHaveSize 1

        map.move(animal, MoveDirection.LEFT)
        map.objectAt(Vector2d(2, 3)) shouldBe animal
        (map.objectAt(Vector2d(2, 3))?.orientation) shouldBe MapDirection.WEST
        map.getElements() shouldHaveSize 1

        map.move(animal, MoveDirection.BACKWARD)
        map.objectAt(Vector2d(2, 3)) shouldBe null
        map.objectAt(Vector2d(3, 3)) shouldBe animal
        map.getElements() shouldHaveSize 1

        map.move(animal, MoveDirection.RIGHT)
        map.objectAt(Vector2d(3, 3)) shouldBe animal
        (map.objectAt(Vector2d(3, 3))?.orientation) shouldBe MapDirection.NORTH
        map.getElements() shouldHaveSize 1
    }

    test("place many animals") {
        val map = BouncyMap(5, 5)
        val animal1 = Animal(Vector2d(2, 2))
        val animal2 = Animal(Vector2d(2, 3))
        val animal3 = Animal(Vector2d(2, 4))
        map.place(animal1)
        map.place(animal2)
        map.place(animal3)
        map.getElements() shouldHaveSize 3
    }

    test("place animal out of bounds") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(6, 6))
        try {
            map.place(animal)
        } catch (e: IllegalArgumentException) {
            e.message shouldBe "Cannot place animal at (6,6)"
        }
        map.getElements() shouldHaveSize 0
    }

    test("move animal out of border") {
        val map = BouncyMap(5, 5)
        val animal = Animal(Vector2d(4, 4))
        map.place(animal)
        map.move(animal, MoveDirection.FORWARD)
        map.objectAt(Vector2d(5, 5)) shouldBe null
        map.getElements() shouldHaveSize 1
    }

    test("Run out of free positions") {
        val map = BouncyMap(2, 2)
        val animal1 = Animal(Vector2d(0, 0))
        val animal2 = Animal(Vector2d(1, 0))
        val animal3 = Animal(Vector2d(0, 1))
        val animal4 = Animal(Vector2d(1, 1))
        val animal5 = Animal(Vector2d(1, 1))
        map.place(animal1)
        map.place(animal2)
        map.place(animal3)
        map.place(animal4)
        try {
            map.place(animal5)
        }
        catch (e: RuntimeException) {
            e.message shouldBe "Map is null or full"
        }
        map.getElements() shouldHaveSize 4
        animal1 shouldBeIn map.getElements()
        animal2 shouldBeIn map.getElements()
        animal3 shouldBeIn map.getElements()
        animal4 shouldBeIn map.getElements()
        animal5 shouldNotBeIn map.getElements()
    }
})

