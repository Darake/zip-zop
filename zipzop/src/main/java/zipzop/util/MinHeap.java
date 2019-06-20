package zipzop.util;

/**
 * A min heap data structure implementation. Uses an array to represent a tree. If the index of an
 * object is i then the parent of that object can be found at index i/2.
 */
public class MinHeap<T extends Comparable<T>> {

  private T[] heap;
  private int size;

  public MinHeap(int maxSize) {
    this.heap = (T[]) new Comparable[maxSize + 1];
    this.size = 0;
  }

  /**
   * Adds an object to the heap. Increases the heap's size counter by one and adds the object to
   * that index. Then it moves the newly added object to its right place by comparing it and its
   * parent. When newly added object is smaller than its parent, the two swap places. This loops
   * until parent is no longer smaller.
   *
   * @param object The object to be added into the heap.
   */
  public void add(T object) {
    size++;
    heap[size] = object;
    moveNewObjectToRightPosition();
  }

  /**
   * Returns the first object in min heap, i.e the smallest object, without removing it.
   *
   * @return First object in heap array
   */
  public T peek() {
    return heap[1];
  }

  /**
   * Returns the first object in heap and and removes it. Puts the last value in heap as root and
   * moves it down along the tree until it is in the right place.
   *
   * @return First object in heap array
   */
  public T poll() {
    final T smallestObject = heap[1];

    heap[1] = heap[size];
    heap[size] = null;
    size--;

    moveNewRootToRightPosition();

    return smallestObject;
  }

  private void moveNewRootToRightPosition() {

    if (size == 2 && heap[1].compareTo(heap[2]) > 0) {
      swap(1, 2);
    }

    int i = 1;
    while (2 * i + 1 <= size) {
      int minChildIndex = 0;
      if (heap[2 * i].compareTo(heap[2 * i + 1]) < 0) {
        minChildIndex = 2 * i;
      } else {
        minChildIndex = 2 * i + 1;
      }

      if (heap[i].compareTo(heap[minChildIndex]) > 0) {
        swap(i, minChildIndex);
        i = minChildIndex;
      } else {
        break;
      }
    }
  }

  private void moveNewObjectToRightPosition() {
    int i = size;
    while (i > 1) {
      if (heap[i].compareTo(heap[i / 2]) < 0) {
        swap(i, i / 2);
        i = i / 2;
      } else {
        break;
      }
    }
  }

  private void swap(int a, int b) {
    T temp = heap[b];
    heap[b] = heap[a];
    heap[a] = temp;
  }

  public Comparable[] getHeap() {
    return heap;
  }

  /**
   * Size of the heap. Not the size of the array itself used in heap.
   *
   * @return Size of the heap.
   */
  public int size() {
    return size;
  }
}
