package zpark.ext.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
        System.out.println(circle);
        System.out.println(circle.firstKey());
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public T getServer(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int keyhash = hashFunction.hash(key.toString());
        System.out.println("key's hash: " + keyhash);
        if (!circle.containsKey(keyhash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(keyhash);
            System.out.println(tailMap);
            keyhash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(keyhash);
    }

    public static void main(String[] args) {
        List<String> nodes = new ArrayList<String>();
        nodes.add("192.168.0.1");
        nodes.add("192.168.0.2");
        nodes.add("192.168.0.3");
        nodes.add("192.168.0.4");
        ConsistentHash<String> hash = new ConsistentHash<>(new HashFunction(), 3, nodes);
        System.out.println(hash.getServer("test"));
    }

}