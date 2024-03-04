//@author: Prashant Karki
// CS241

package songs;

import java.util.Arrays;
import java.util.Comparator;

public class ULSortedList<E> implements Cloneable {
    private final Comparator<E> cmp;
    private E[] sortedList;
    private int size;

    // Constructs a ULSortedList with the given comparator and an initial capacity of 16.
    public ULSortedList(Comparator<E> cmp) {
        this.cmp = cmp;
        this.sortedList = (E[]) new Object[16];
        size = 0;
    }

    // Constructs a ULSortedList with the given comparator and initial capacity.
    public ULSortedList(Comparator<E> cmp, int initialCapacity) {
        this.cmp = cmp;
        this.sortedList = (E[]) new Object[initialCapacity];
        size = 0;
    }

    // Add the given item to the list, preserving the sorted order of the list.
    public void add(E item) {
        if(item != null){
            // double the size of list on reaching the capacity
            if(size == sortedList.length) {
                sortedList = Arrays.copyOf(sortedList, sortedList.length * 2);
            }

            // compare songs
            int index = size;
            boolean rightPlace = false; // flag used to eliminate 'break'
            for(int i = 0; i < size; i++) {
                if(cmp.compare((E) sortedList[i], item) > 0 && !rightPlace) {
                index = i;
                rightPlace = true;
                }
            }

            // make sure you push the items after that
            for (int i = size - 1; i >= index; i--) {
                sortedList[i + 1] = sortedList[i];
            }

            // now make sure that the item is added in the right order of the list
            sortedList[index] = item;
            size++;
        }
    }
    // Returns the current capacity of the underlying array.
    public int capacity() { return sortedList.length; }

    // Removes all the items from the list.
    public void clear()
    {
        if(size == 0) { return; }
        for(int i = 0; i < size; i++)
        {
            sortedList[i] = null;
        }
        size = 0;
    }

    @Override
    public ULSortedList<E> clone() {
        try {
            return (ULSortedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Returns true if the given item is in the list.
    public boolean contains(E item)
    {
        int i = 0;
        while(i < size && !sortedList[i].equals(item))
        {
            i++;
        }
        return i < size;
    }

    // Returns true iff the two lists have the equivalent items in the same order.
    public boolean equals(Object otherObject)
    {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;

        // this means as long as otherObject is an instance of ULSortedList no matter what type, we are good
        ULSortedList<?> other = (ULSortedList<?>) otherObject;

        if(size() != other.size()) return false;

        for(int i = 0; i < this.size; i++)
        {
            if(!sortedList[i].equals(other.sortedList[i])) return false;
        }
        return true;
    }

    // Returns the item stored at the given index
    public E get(int index) throws ULIndexOutOfBoundsException
    {
        if(index < 0 || index >= size)
            throw new ULIndexOutOfBoundsException(String.format("%d should be within 0 and %d.", index, size));
        return sortedList[index];
    }

    // Removes the given item from the list.
    public void remove(E item) throws ULItemNotFoundException {
        if(!this.contains(item)) throw new ULItemNotFoundException("EXCEPTION: ITEM NOT IN THE LIST!!!");
        else {
            for (int i = 0; i < size; i++) {
                if (sortedList[i].equals(item)) {
                    for (int j = i; j < size - 1; j++) {    // start shifting from the index removed
                        sortedList[j] = sortedList[j + 1];  // on removal shift elements to the left
                    }
                    size--;     // size of the array should decrease on removal
                    i--;        // this is so that we don't leave the index that just moved to the removed index
                }
            }
        }
    }

    // Returns the number of items in the list.
    public int size() { return size; }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(E item : sortedList){
            if(item != null)
            {
                str.append(item.toString()).append(" ");
            }
        }
        return str.toString();
    }
}