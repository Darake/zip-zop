package zipzop.util;

/**
 * A min heap data structure implementation.
 */
public class MinHeap<T extends Comparable<T>> {
    
    private T[] heap;
    private int size;
    
    public MinHeap(int maxSize) {
        this.heap = (T[]) new Comparable[maxSize];
        this.size = 0;
    }
    
    public void add(T object) {
        size++;
        heap[size] = object;
        moveAddedUp();
    }
    
    private void moveAddedUp() {
        int i = size;
        while (i > 1) {
            if (heap[i].compareTo(heap[i/2]) > 0) {
                swap(i, i/2);
                i = i/2;
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
    
    public int getSize() {
        return size;
    }
}
