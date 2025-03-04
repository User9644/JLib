package de.f.utils;

import java.util.List;

public class DoubleMap<K1, K2, V> {
    public static class Entry<K1, K2, V> {
        protected final K1 k1;
        protected final K2 k2;
        protected V v;

        private Entry(K1 k1, K2 k2, V v) {
            this.k1 = k1;
            this.k2 = k2;
            this.v = v;
        }

        public K1 getKey1() {
            return this.k1;
        }
        public K2 getKey2() {
            return this.k2;
        }
        public V getValue() {
            return this.v;
        }

        public void setValue(V value) {
            this.v = value;
        }
    }

    private static class Node<K1, K2, V> extends Entry<K1, K2, V> {
        private Node(K1 k1, K2 k2, V v) {
            super(k1, k2, v);
        }

        public Node<K1, K2, V> nextNode = null;

        public void put(K1 k1, K2 k2, V v) {
            if(this.k1.equals(k1) && this.k2.equals(k2))
                this.v = v;
            else if(this.nextNode == null)
                this.nextNode = new Node<>(k1, k2, v);
            else
                this.nextNode.put(k1, k2, v);
        }

        public V get(K1 k1, K2 k2) {
            if(this.k1.equals(k1) && this.k2.equals(k2))
                return this.v;
            else if(this.nextNode == null)
                return null;
            else
                return this.nextNode.get(k1, k2);
        }

        public void entryList(List<Entry<K1, K2, V>> list) {
            list.add(this);
            if(this.nextNode != null)
                this.nextNode.entryList(list);
        }

        public void remove(K1 k1, K2 k2) {
            if(this.nextNode != null && this.nextNode.k1.equals(k1) && this.nextNode.k2.equals(k2))
                this.nextNode = this.nextNode.nextNode;
        }
    }

    private Node<K1, K2, V> firstNode = null;

    // TODO: ☺☺☻☺☺•
}