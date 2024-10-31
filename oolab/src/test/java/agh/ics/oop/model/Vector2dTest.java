package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void SameVectorsEqual(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);

        assertTrue(v1.equals(v2));
    }

    @Test
    void DifferentVectorsNotEqual(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,0);

        assertFalse(v1.equals(v2));
    }

    @Test
    void StringFormat(){
        Vector2d v1 = new Vector2d(1,2);

        assertEquals("(1,2)", v1.toString());
        assertNotEquals("(1, 2)", v1.toString());
    }

    @Test
    void PrecedingVectors(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,3);
        Vector2d v3 = new Vector2d(1,4);

        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v3));
    }

    @Test
    void FollowingVectors(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,3);
        Vector2d v3 = new Vector2d(1,4);

        assertTrue(v2.follows(v1));
        assertFalse(v3.follows(v2));
    }

    @Test
    void UpperRightTest(){
        Vector2d v1 = new Vector2d(-1,2);
        Vector2d v2 = new Vector2d(-2,3);
        Vector2d v3 = new Vector2d(-1,3);
        Vector2d v4 = new Vector2d(-2,4);
        Vector2d v5 = new Vector2d(-1,5);
        Vector2d v6 = new Vector2d(-2,5);

        assertEquals(v3,v1.upperRight(v2));
        assertNotEquals(v6,v4.upperRight(v5));
    }

    @Test
    void LowerLeftTest(){
        Vector2d v1 = new Vector2d(-1,2);
        Vector2d v2 = new Vector2d(-2,3);
        Vector2d v3 = new Vector2d(-2,2);
        Vector2d v4 = new Vector2d(-2,4);
        Vector2d v5 = new Vector2d(-1,5);
        Vector2d v6 = new Vector2d(-1,4);

        assertEquals(v3,v1.lowerLeft(v2));
        assertNotEquals(v6,v4.lowerLeft(v5));
    }


    @Test
    void AddingVectors(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(0,0);
        Vector2d v4 = new Vector2d(-1,-1);
        Vector2d v5 = new Vector2d(-2,-2);

        assertEquals(v1,v1.add(v3));
        assertEquals(v3,v1.add(v4));
        assertEquals(v2,v1.add(v1));
        assertEquals(v5,v4.add(v4));
        assertEquals(v2.add(v1),v1.add(v2));

    }


    @Test
    void SubtractingVectors(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(0,0);
        Vector2d v4 = new Vector2d(-1,-1);
        Vector2d v5 = new Vector2d(-2,-2);

        assertEquals(v1,v2.subtract(v1));
        assertEquals(v3,v1.subtract(v1));
        assertEquals(v3,v4.subtract(v4));
        assertEquals(v5,v4.subtract(v1));
        assertNotEquals(v2.subtract(v1),v1.subtract(v2));
    }

    @Test
    void OppositeVectors(){
        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(0,0);
        Vector2d v4 = new Vector2d(-1,-1);
        Vector2d v5 = new Vector2d(-2,-2);

        assertEquals(v4,v1.opposite());
        assertEquals(v1,v4.opposite());
        assertEquals(v3,v3.opposite());
    }




}