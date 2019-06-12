package zipzop.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stack tests")
public class StackTest {
    
    private Stack<Integer> stack;
    
    @BeforeEach
    public void setUp() {
        stack = new Stack<>();
        
        stack.push(2);
        stack.push(3);
        stack.push(1);
    }
    
    @Test
    @DisplayName("pushing objects adds to stack")
    public void pushAddsToStack() {
        stack.push(1337);
        
        assertEquals(1337, stack.getStack()[3]);
    }
    
    @Test
    @DisplayName("stack's array increases in size when full")
    public void pushIncreasesArraySizeWhenFull() {
        for (int i = 0; i < 1065; i++) {
            stack.push(i);
        }
        
        assertEquals(2128, stack.getStack().length);
    }
    
    @Test
    @DisplayName("peek returns the value on top of stack")
    public void peekReturnsRightValue() {
        assertEquals(1, stack.peek());
    }
    
    @Test
    @DisplayName("pop returns the value on top of stack")
    public void popReturnsRightValue() {
        assertEquals(1, stack.pop());
    }
    
    @Test
    @DisplayName("pop moves the pointer by one")
    public void popMovesPointerByOne() {
        stack.pop();
        
        assertEquals(3, stack.peek());
    }
    
    @Test
    @DisplayName("size returns the size of the stack")
    public void sizeReturnsStackSize() {
        assertEquals(3, stack.size());
    }
    
}
