package zipzop.util;

/**
 * A stack data structure implementation.
 *
 * @param <T> The type used for stack
 */
public class Stack<T> {

  private int pointer;
  private T[] stack;

  /**
   * Constructor creates an array with initial size of 1064 for array and sets the pointer to index
   * 0.
   */
  public Stack() {
    stack = (T[]) new Object[1064];
    pointer = -1;
  }

  /**
   * Adds an object to the top of stack. If array used in stack is full then a new array with double
   * the size is created and values from old array are copied to new.
   *
   * @param object The object added to stack
   */
  public void push(T object) {
    if (pointer == stack.length - 1) {
      doubleArraySize();
    }

    pointer++;
    stack[pointer] = object;
  }

  /**
   * Peeks at the object on top of stack without removing it.
   *
   * @return The object on top of stack
   */
  public T peek() {
    return stack[pointer];
  }

  /**
   * Pops the object on top of the stack and decreases pointer by one, effectively removing it.
   *
   * @return The object on top of stack
   */
  public T pop() {
    pointer--;
    return stack[pointer + 1];
  }

  /**
   * Gets the size of the stack by adding one to the pointer.
   *
   * @return The size of the stack
   */
  public int size() {
    return pointer + 1;
  }

  private void doubleArraySize() {
    var tempStack = (T[]) new Object[stack.length * 2];
    for (int i = 0; i < stack.length; i++) {
      tempStack[i] = stack[i];
    }
    stack = tempStack;
  }

  public Object[] getStack() {
    return stack;
  }
}
