package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import songs.ULItemNotFoundException;
import songs.ULSortedList;

class ULSortedListTest {

    private class NonComparableString{
        public String str;
        public NonComparableString(String aString) {
            this.str = aString;
        }

        public boolean equals(Object otherObject) {
            boolean same = this == otherObject;
            boolean equal = false;

            // don't bother doing a deep check if this and otherObject are the same object
            // make sure it is safe to cast
            if( !same && otherObject != null && getClass() == otherObject.getClass() ) {
                NonComparableString other = (NonComparableString)otherObject;
                // make sure the size and arrays are equal
                // we don't care if the comparators are equal
                equal = this.str.equals(other.str);
            }

            return same || equal;
        }
    }

    private class NonCmpStrComparator implements Comparator<NonComparableString>{
        public int compare(NonComparableString lhs, NonComparableString rhs) {
            return lhs.str.compareTo(rhs.str);
        }
    }

    @Test
    void testDefaultCapacity() {
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder());
        assertEquals(16, list.capacity(), "default capacity test");
    }

    @Test
    void testCapacityConstructor() {
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder(), 54);
        assertEquals(54, list.capacity(), "initialized capacity test");
    }

    @Test
    void testAdd() {
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder(), 10);

        // add to empty list
        List<Integer> expected = Arrays.asList(5);
        list.add(5);
        checkListEquality(expected, list, "adding to empty list");

        // setup the list for further tests
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);

        // add to the front
        list.add(1);
        expected = Arrays.asList(1,5,6,7,8,9,10);

        checkListEquality(expected, list, "adding to front");

        // add to the end
        list.add(15);
        expected = Arrays.asList(1,5,6,7,8,9,10,15);
        checkListEquality(expected, list, "adding to end");

        // add to the middle
        list.add(2);
        expected = Arrays.asList(1,2,5,6,7,8,9,10,15);
        checkListEquality(expected, list, "adding to middle");

    }

    @Test
    void testContains() {
        // contains on an empty list
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder());
        assertFalse(list.contains(10), "contains should always be false on an empty list");

        // setup
        list.add(4);
        list.add(8);
        list.add(12);
        list.add(16);


        // contains at front
        assertTrue(list.contains(4), "contains first item");
        // not contains at front
        assertFalse(list.contains(2), "does not contain before first item");

        // contains in the middle
        assertTrue(list.contains(12), "contains middle item");
        // not contains in the middle
        assertFalse(list.contains(10), "does not contain in the middle");

        // contains at the end
        assertTrue(list.contains(16), "contains last item");
        // not contains at the end
        assertFalse(list.contains(20), "does not contain after the last item");
    }

    @Test
    void testRemove() {
        // remove from an empty list
        ULSortedList<String> list = new ULSortedList<String>(Comparator.naturalOrder());
        try {
            list.remove("a");
            fail("removed non-existent item from an empty list");
        }catch(ULItemNotFoundException e) {/*should throw*/}

        // setup
        list.add("a");
        list.add("d");
        list.add("g");
        list.add("m");
        list.add("p");
        list.add("s");
        list.add("y");

        List<String> expected = new ArrayList<>(Arrays.asList("a", "d", "g", "m", "p", "s", "y"));

        // remove item at the front
        list.remove("a");
        expected.remove("a");
        checkListEquality(expected, list, "failed to remove item at the front");

        // remove non-existent item at the front
        try {
            list.remove("b");
            fail("removed non-existent item at the front");
        }catch(ULItemNotFoundException e) {/*should throw*/}
        checkListEquality(expected, list, "removing non-existent item at the front changed list");

        // remove item in the middle
        list.remove("m");
        expected.remove("m");
        checkListEquality(expected, list, "failed to remove item in the middle");

        // remove non-existent item in the middle
        try {
            list.remove("n");
            fail("removed non-existent item in the middle");
        }catch(ULItemNotFoundException e) {/*should throw*/}
        checkListEquality(expected, list, "removing non-existent item in the middle changed the list");

        // remove item at the end
        list.remove("y");
        expected.remove("y");
        checkListEquality(expected, list, "failed to remove item at the front");

        // remove non-existent item at the end
        try {
            list.remove("z");
            fail("removed non-existent item in the middle");
        }catch(ULItemNotFoundException e) {/*should throw*/}
        checkListEquality(expected, list, "removing non-existent item at the end changed the list");

        // remove the remaining items
        for(String item : expected) {
            list.remove(item);
        }
        expected.clear();

        // try to remove one again
        try {
            list.remove("d");
            fail("removed non-existent item from a list that was full but now is empty");
        }catch(ULItemNotFoundException e) {/*should throw*/}

    }

    @Test
    void testClear() {
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder());
        List<Integer> expected = Arrays.asList(5,10,15,20,25,30);

        // clear an empty list
        list.clear();
        assertEquals(0, list.size(), "clearing an empty list set size incorrectly");

        // add a bunch of stuff to the list
        for(int number : expected) {
            list.add(number);
        }

        // clear the list
        list.clear();
        assertEquals(0, list.size(), "clearing a list set size incorrectly");

        // see if the list contains some items
        assertFalse(list.contains(5), "a cleared list should have no items");
    }

    @Test
    void testDoubleCapacity() {
        final int INITIAL_CAPACITY = 10;
        ULSortedList<Integer> list = new ULSortedList<Integer>(Comparator.naturalOrder(), INITIAL_CAPACITY);
        ArrayList<Integer> expected = new ArrayList<>();

        // ensure capacity is correct
        assertEquals(INITIAL_CAPACITY, list.capacity(), "initial capacity is incorrect");

        // add enough items to double capacity
        for(int i = 0; i <= INITIAL_CAPACITY; i++) {
            list.add(i);
            expected.add(i);
        }

        // ensure capacity has doubled
        assertEquals(INITIAL_CAPACITY * 2, list.capacity(), "causing array to grow did not double capacity");
        checkListEquality(expected, list, "doubling capacity didn't break the list");
    }

    @Test
    void testClone() {
        ULSortedList<String> list = new ULSortedList<String>(Comparator.naturalOrder());
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g"));

        // add a bunch of items
        for(String letter : expected) {
            list.add(letter);
        }

        // make a clone
        ULSortedList<String> clone = list.clone();

        // make sure they are equal
        checkListEquality(expected, clone, "clone differs from orig immediately after cloning");

        // modify the original
        list.remove("a");

        // make sure the clone is unaltered
        checkListEquality(expected, clone, "changing original changes clone");

        // modify the clone
        clone.remove("g");

        // make sure the original is unaltered
        expected.remove("a");
        checkListEquality(expected, list, "changing clone changes original");
    }

    @Test
    void testEquals() {
        // make two lists
        ULSortedList<Integer> listA = new ULSortedList<Integer>(Comparator.naturalOrder());
        ULSortedList<Integer> listB = new ULSortedList<Integer>(Comparator.naturalOrder());

        // add the same items to the lists
        for(int i = 100; i <= 110; i++) {
            listA.add(i);
            listB.add(i);
        }

        // check if they are equal
        assertTrue(listA.equals(listB), "add: A is not equal to B");
        assertTrue(listB.equals(listA), "add: B is not equal to A");
        assertTrue(listA.equals(listA), "add: A is not equal to A");
        assertTrue(listB.equals(listB), "add: B is not equal to B");

        // remove the first, last and middle item from A
        listA.remove(100);
        listA.remove(105);
        listA.remove(110);

        // now the lists should not be equal
        assertFalse(listA.equals(listB), "remove from A: A is equal to B");
        assertFalse(listB.equals(listA), "remove fom B: B is equal to A");

        // remove the same items from B
        listB.remove(100);
        listB.remove(105);
        listB.remove(110);

        // check if they are equal
        assertTrue(listA.equals(listB), "after remove from both: A is not equal to B");
        assertTrue(listB.equals(listA), "after remove from both: B is not equal to A");
        assertTrue(listA.equals(listA), "after remove from both: A is not equal to A");
        assertTrue(listB.equals(listB), "after remove from both: B is not equal to B");

        // make sure that the code doesn't fail when the parameter is not a list
        assertFalse(listA.equals(Integer.valueOf(7)), "7 is equal to the list?");
    }

    @Test
    void testNonComparableItems() {
        // call every method once to make sure it will compile
        ULSortedList<NonComparableString> nonCmpList = new ULSortedList<>(new NonCmpStrComparator());

        nonCmpList.add( new NonComparableString("a") );
        nonCmpList.contains( new NonComparableString("a") );
        nonCmpList.remove( new NonComparableString("a") );
    }

    private <E> void checkListEquality(List<E> expected, ULSortedList<E> actual, String message) {
        assertEquals(expected.size(), actual.size(), message + ": size mismatch");
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), message + ": items don't match");
        }
    }
}
