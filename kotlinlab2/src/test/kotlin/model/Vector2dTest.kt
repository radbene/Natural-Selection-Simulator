package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class Vector2dTest : FunSpec({
    test("Test equals") {
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(1, 2)
        val vector3 = Vector2d(2, 1)
        val vector4 = Vector2d(4, 4)
        val vector5 = Vector2d(4, 4)

        vector1 shouldBe vector2
        vector1 shouldNotBe vector3
        vector1 shouldNotBe vector4
        vector4 shouldBe vector5
    }

    test("Test toString") {
        val vector = Vector2d(1, 2)
        vector.toString() shouldBe "(1,2)"
    }

    test("Test precedes") {
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(1, 2)
        val vector3 = Vector2d(2, 1)
        val vector4 = Vector2d(1, 3)
        val vector5 = Vector2d(3, 2)
        val vector6 = Vector2d(3, 4)
        val vector7 = Vector2d(4, 3)
        val vector8 = Vector2d(4, 4)

        vector1.precedes(vector2) shouldBe true
        vector1.precedes(vector3) shouldBe false
        vector1.precedes(vector4) shouldBe true
        vector1.precedes(vector5) shouldBe true
        vector1.precedes(vector6) shouldBe true
        vector1.precedes(vector7) shouldBe true
        vector1.precedes(vector8) shouldBe true
    }

    test("Test follows") {
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(1, 2)
        val vector3 = Vector2d(2, 1)
        val vector4 = Vector2d(1, 3)

        vector1.follows(vector2) shouldBe true
        vector1.follows(vector3) shouldBe false
        vector1.follows(vector4) shouldBe false
    }

    test("+"){
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(3, 4)
        val vector3 = Vector2d(4, 6)
        vector1 + vector2 shouldBe vector3
    }

    test("-"){
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(3, 4)
        val vector3 = Vector2d(-2, -2)
        vector1 - vector2 shouldBe vector3
    }

    test("opposite"){
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(-1, -2)
        -vector1 shouldBe vector2
        -(-vector1) shouldBe vector1
    }

    test("times"){
        val vector1 = Vector2d(1, 2)
        val vector2 = Vector2d(3, 4)
        val vector3 = Vector2d(3, 8)
        vector1 * vector2 shouldBe vector3
    }
})