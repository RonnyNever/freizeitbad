package haupt;

// Hash Tabelle mit Separate Chaining ( Collisionserkennung )

import java.util.ArrayList;
import java.util.Objects;
  
// Node von Chain
class HashNode<K, V> {
    K key;
    V value;
      final int hashCode;
  
    // Referenz zum nächsten Node
    HashNode<K, V> next;
  
    // Konstruktor
    public HashNode(K key, V value, int hashCode)
    {
        this.key = key;
        this.value = value;
          this.hashCode = hashCode;
    }
}
  
// Hash
class Map<K, V> {
    // bucketArray speichert die Werte
    private ArrayList<HashNode<K, V> > bucketArray;
  
    // aktuelle Kapazität des Arrays
    private int numBuckets;
  
    // aktuelle Größe des Arrays
    private int size;
  
    // Konstruktor (Initialisiert capacity, size und leere chains
    
    public Map()
    {
        bucketArray = new ArrayList<>();
        numBuckets = 10;
        size = 0;
  
        // Erschaffe leeres Chain
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }
  
    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }
      
      private final int hashCode (K key) {
        return Objects.hashCode(key);
    }
    
    // get
    private int getBucketIndex(K key)
    {
        int hashCode = hashCode(key);
        int index = hashCode % numBuckets;
        // key.hashCode() could be negative.
        index = index < 0 ? index * -1 : index;
        return index;
    }
  
    // remove
    public V remove(K key)
    {
        
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        // Kopf des Hash-Nodes
        HashNode<K, V> head = bucketArray.get(bucketIndex);
  
        // Suche nach dem Key
        HashNode<K, V> prev = null;
        while (head != null) {
            // Wenn der Key gefunden wurde
            if (head.key.equals(key) && hashCode == head.hashCode)
                break;
  
            // weiter suchen in der Chain
            prev = head;
            head = head.next;
        }
  
        // Wenn nichts gefunden wurde
        if (head == null)
            return null;
  
        // reduziere die Size
        size--;
  
        // entferne den Key
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);
  
        return head.value;
    }
  
    // Get
    public V get(K key)
    {
        // Head der Chain
        int bucketIndex = getBucketIndex(key);
          int hashCode = hashCode(key);
        
        HashNode<K, V> head = bucketArray.get(bucketIndex);
  
        // Suche nach dem Key im Chain
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode)
                return head.value;
            head = head.next;
        }
  
        // Wenn nicht gefunden also der Key
        return null;
    }
  
    // Key & Value eintragen
    public void add(K key, V value)
    {
        // Finde den Kopf der aktuellen Chain
        int bucketIndex = getBucketIndex(key);
          int hashCode = hashCode(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);
  
        // Gucken, ob der Key vorhanden ist
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode) {
                head.value = value;
                return;
            }
            head = head.next;
        }
  
        // Füge Key in Chain hinzu
        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode
            = new HashNode<K, V>(key, value, hashCode);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);
  
        // Wenn der Lastfaktor den Schwellenwert überschreitet, dann
        // verdoppel die Hash-Tabellengröße
        if ((1.0 * size) / numBuckets >= 0.7) {
            ArrayList<HashNode<K, V> > temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);
  
            for (HashNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }
  
    // main brudi
    public static void main(String[] args)
    {
        Map<String, Integer> map = new Map<>();
        map.add("this", 1);
        map.add("coder", 2);
        map.add("this", 4);
        map.add("hi", 5);
        System.out.println(map.size());
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
    }
}