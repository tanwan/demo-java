package com.lzy.demo.design.pattern.iterator;

public class ConcreteAggregate<T> implements Aggregate<T> {

    private T[] elements;

    public ConcreteAggregate(T[] elements) {
        this.elements = elements;
    }

    @Override
    public Iterator<T> getIterator() {
        return new ConcreteIterator();
    }

    public class ConcreteIterator implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < elements.length;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                return elements[index++];
            }
            return null;
        }
    }
}
