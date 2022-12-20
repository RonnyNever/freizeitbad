package offeneHash;
// Open Hashing
 
import java.util.ArrayList;  
import java.util.Objects;  
  
// Nodes vom Chain
class HashNode<K, V> {  
    K key;  
    V value;  
    final int hashCode;  
  
    // ein Verweis auf einen nachfolgenden Knoten
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
    // ArrayList zum speichern der Daten 
    private ArrayList<HashNode<K, V> > bucketArray;  
  
    // akuellte Kapazität der ArrayList 
    private int numbucks;  
  
    // size vom ArrayList 
    private int size_of_array;  
  
    // Konstruktor initialisiert capacity, size_of_array und leere chains.  
    public Map()  
    {  
        bucketArray = new ArrayList<>();  
        numbucks = 12;  
        size_of_array = 0;  
  
        // ERzeuge leere chains zur implementierung von Open Hashing.  
        for (int i = 0; i < numbucks; i++)  
            bucketArray.add(null);  
    }  
  
    public int size_of_array() { return size_of_array; }  
    public boolean isEmpty() { return size_of_array() == 0; }  
      
    private final int hashCode (K key) {  
        return Objects.hashCode(key);  
    }  
  
    // suche nach dem index von einem key  
    private int getBucketIndex(K key)  
    {  
        int hashCode = hashCode(key);  
        int index = hashCode % numbucks;  
        // key.hashCode() may result negative.  
        index = index < 0 ? index * -1 : index;  
        return index;  
    }  
  
    // lösche den gegebene key  
    public V remove(K key)  
    {  
        // index vom gegebenen key finden 
        int bucketIndex = getBucketIndex(key);  
        int hashCode = hashCode(key);  
        // Get head of the chain  
        HashNode<K, V> head = bucketArray.get(bucketIndex);  
  
        // key suchen in der chain
        HashNode<K, V> prev = null;  
        while (head != null) {  
            // If Key found  
            if (head.key.equals(key) && hashCode == head.hashCode)  
                break;  
  
            // weiter suchen in der chain  
            prev = head;  
            head = head.next;  
        }  
  
        // wenn der key nicht da ist 
        if (head == null)  
            return null;  
  
        // größe des Arrays verkleinern minus
        size_of_array--;  
  
          
        if (prev != null)  
            prev.next = head.next;  
        else  
            bucketArray.set(bucketIndex, head.next);  
  
        return head.value;  
    }  
  
    // wiedergabe von value eines key
    public V get(K key)  
    {  
        // den kopf finden von dem gegebenen key 
        int bucketIndex = getBucketIndex(key);  
        int hashCode = hashCode(key);  
      
        HashNode<K, V> head = bucketArray.get(bucketIndex);  
  
        // key suchen in der chain 
        while (head != null) {  
            if (head.key.equals(key) && head.hashCode == hashCode)  
                return head.value;  
            head = head.next;  
        }  
  
        // der gesuchte key ist nicht da
        return null;  
    }  
  
    // hash key und value einfügen 
    public void add(K key, V value)  
    {  
        // vom key den kopf der chain finden   
        int bucketIndex = getBucketIndex(key);  
        int hashCode = hashCode(key);  
        HashNode<K, V> head = bucketArray.get(bucketIndex);  
  
        // gucken, ob der key exestiert 
        while (head != null) {  
            if (head.key.equals(key) && head.hashCode == hashCode) {  
                head.value = value;  
                return;  
            }  
            head = head.next;  
        }  
  
        // size vom array ( minus ) dekrementieren   
        size_of_array++;  
        head = bucketArray.get(bucketIndex);  
        HashNode<K, V> newNode  
            = new HashNode<K, V>(key, value, hashCode);  
        newNode.next = head;  
        bucketArray.set(bucketIndex, newNode);  
  
        // Wenn Schwellenwert überschritten dann Hash size verdoppeln
        if ((1.0 * size_of_array) / numbucks >= 0.7) {  
            ArrayList<HashNode<K, V> > temp = bucketArray;  
            bucketArray = new ArrayList<>();  
            numbucks = 2 * numbucks;  
            size_of_array = 0;  
            for (int i = 0; i < numbucks; i++)  
                bucketArray.add(null);  
  
            for (HashNode<K, V> headNode : temp) {  
                while (headNode != null) {  
                    add(headNode.key, headNode.value);  
                    headNode = headNode.next;  
                }  
            }  
        }  
    }  
  
    // main 
    public static void main(String[] args)  
    {  
        Map<String, Integer> map = new Map<>();  
        map.add("Hello", 1);  
        map.add("programmer", 2);  
        map.add("Hello", 4);  
        map.add("Manager", 5);  
        System.out.println(map.size_of_array());  
        System.out.println(map.remove("Hello"));  
        System.out.println(map.remove("Hello"));  
        System.out.println(map.size_of_array());  
        System.out.println(map.isEmpty());  
    }  
}  