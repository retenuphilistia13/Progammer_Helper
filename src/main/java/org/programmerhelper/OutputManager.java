
package org.programmerhelper;

        import java.util.Stack;

public class OutputManager <T>{
    public T getUndoStack() {

        return undoStack.peek();

    }
    public void getSize(){
        System.out.println("\n\nundoStack size\n\n"+undoStack.size());
    }
    private Stack<T> undoStack;
    private Stack<T> redoStack;
    private   int undoCapacity = 100;
    private   int redoCapacity = 100;
    public OutputManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public OutputManager(int undoCapacity,int redoCapacity) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        this.redoCapacity =redoCapacity;
        this.undoCapacity =undoCapacity;

    }

    public void addToUndoStack(T action) {
        if (!undoStack.isEmpty()&& undoStack.peek().equals(action)) {
            // Ignore duplicate output
            return;
        } else if (undoStack.isEmpty()) {//first action
            undoStack.push(action);
        }
        if (undoStack.size() >= undoCapacity) {
            undoStack.remove(0); // Remove the oldest action
        }
        undoStack.push(action);
        redoStack.clear();
        System.out.println("Action added: " + action);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            T action = undoStack.pop();
            if (redoStack.size() >= redoCapacity) {
                redoStack.remove(0); // Remove the oldest action
            }
            redoStack.push(action);
            System.out.println("Undo: " + action);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            T action = redoStack.pop();
            if (undoStack.size() >= undoCapacity) {
                undoStack.remove(0); // Remove the oldest action
            }
            undoStack.push(action);
            System.out.println("Redo: " + action);
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}
