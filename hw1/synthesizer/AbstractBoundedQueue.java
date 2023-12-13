package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public boolean isEmpty() {
        return fillCount() == 0;
    }

    public boolean isFull(){
        return fillCount() == capacity();
    }
}