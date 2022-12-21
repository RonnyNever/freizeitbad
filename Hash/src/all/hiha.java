// DIRECT CHAINING

package directChaining;

public class BucketDirectEntry<T, T2> {

    private int curSize;
    private Node<T, T2> start;
    private Node<T, T2> cursor;

    /**
     * Sets the cursor based on a index
     * @param k
     */
    public void setCursor(int k)
    {
        cursor = start;
        for(int i=0;i<k;i++)
            cursor = cursor.getNext();
    }

    /**
     * Removes a pair based on the key
     * @param k
     * @throws Exception
     */
    public void remove(T k) throws Exception {

        if(isEmpty())
            throw new Exception("Can not remove any item from empty bucket");

        var curr = searchNode(k);
        var prev = searchPrevNode(k);

        if(curr == null)
            return;

        // if there is just a single item
        if(curr != null && prev == null)
        {
            curr.setKey(null);
            curr.setValue(null);
            curSize--;
            return;
        }


        // if the next node is not null
        if(curr.getNext() == null)
        {
            prev.setNext(null);
            curSize--;
            return;
        }
        else
        {
            if(prev != null)
            {

                if(curr.getNext() == null)
                {
                    prev.setNext(null);
                    curSize--;
                }
                else
                {
                    prev.setNext(curr.getNext());
                    curSize--;
                }
            }
        }
    }

    /**
     * Checks whether the chain is empty
     * @return returns true if the chain is empty
     */
    public boolean isEmpty()
    {
        return this.curSize == 0 || this.start == null;
    }

    /**
     * Adds or updates a pair
     * @param node
     */
    public boolean insert(Node<T,T2> node)
    {
        if(this.isEmpty()) {
            start = node;
            curSize = 1;
            return false;
        }

        var n2 = searchNode(node.getKey());
        if(n2 != null)
        {
            n2.setValue(node.getValue());
            return true;
        }

        setCursor(curSize-1);
        cursor.setNext(node);
        curSize++;
        return false;
    }

    /**
     * Looks the chain for a specific key
     * @param k
     * @return The pair
     */
    public Node<T,T2> searchNode(T k)
    {
        for(int i=0;i<curSize;i++)
        {
            var node = getNode(i);
            if(node.getKey() == k)
                return node;
        }
        return null;
    }

    /**
     * Returns the previous node
     * @param k
     * @return
     */
    public Node<T,T2> searchPrevNode(T k)
    {
        Node<T,T2> prev = null;
        for(int i=0;i<curSize;i++)
        {
            var node = getNode(i);
            if(node.getKey() == k)
                return prev;
            prev = node;
        }
        return null;
    }


    /**
     * Gets a node a specific index
     * @param k the index
     * @return node
     */
    public Node<T,T2> getNode(int k)
    {
        setCursor(k);
        return cursor;
    }

    /**
     * Gets the head
     * @return
     */
    public Node<T,T2>  getStart() {
        return start;
    }

    /**
     * Sets the head
     * @param start
     */
    public void setStart(Node<T,T2>  start) {
        this.start = start;
    }

    /**
     * Gets the cursor
     * @return
     */
    public Node<T,T2> getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor
     * @param cursor
     */
    public void setCursor(Node<T,T2>  cursor) {
        this.cursor = cursor;
    }

    /**
     * Gets the current size in the bucket/chain
     * @return
     */
    public int getCurSize() {
        return curSize;
    }

    /**
     * Sets the current size
     * @param curSize
     */
    @Deprecated
    public void setCurSize(int curSize) {
        this.curSize = curSize;
    }
}

/////////////

package directChaining;


public class directChainMap<T,T2> {

    private BucketDirectEntry<T,T2> dE[];
    private int curSize, maxSize;


    /**
     * Constructor to initialize the buckets
     * @param size
     */
    public directChainMap(int size)
    {
        this.curSize = 0;
        this.maxSize = size;
        this.dE = new BucketDirectEntry[size];
        for(int i=0;i<size;i++)
            this.dE[i] = new BucketDirectEntry();
    }

    /**
     * hashes the key
     * @param x the key
     * @return the hashed key
     */
    public int hash(T x)
    {
        return x.hashCode() % this.maxSize;
    }

    /**
     * Inserts or updates a pair of key and value
     * @param key the key, identical to the dictionary key in python
     * @param val the value, similar to the dictionary value in python
     */
    public void insert(T key, T2 val)
    {
        var hashedIndex = hash(key);
        var newNode = new Node<T,T2>(key, val, null);
        if(!dE[hashedIndex].insert(newNode))
            curSize++;
        return;
    }

    /**
     * Removes a entry by the key
     * @param key The key
     * @throws Exception
     */
    public void remove(T key) throws Exception {
        var hashedIndex = hash(key);
        dE[hashedIndex].remove(key);
        curSize--;
    }

    /**
     * Checks if the direct chain map is empty
     * @return whether it is empty or not
     */
    public boolean isEmpty()
    {
        return this.curSize == 0;
    }

    /**
     * Prints the buckets for debugging purposes
     * @throws Exception
     */
    public void printBuckets() throws Exception {
        if (isEmpty())
            throw new Exception("No buckets available");

        // go through all nodes
        for (int i = 0; i < maxSize; i++) {

            var node = dE[i];
            if (node == null || node.getCurSize() == 0)
                continue;

            System.out.printf("Bucket %d: ", i);

            // check the chain for the node
            var nextNode = node;

            for (int k = 0; k < node.getCurSize(); k++) {
                var n = node.getNode(k);
                if(n != null) {
                    if(n.getKey() instanceof Integer)
                        System.out.printf("-> %d:%d", (int)n.getKey(), (int)n.getValue());
                    if(n.getKey() instanceof String)
                        System.out.printf("-> %s:%s", (String)n.getKey(), (String)n.getValue());
                }
            }

            System.out.printf("\n");
        }
    }

    /**
     * Gets the bucket entries
     * @return
     */
    public BucketDirectEntry<T, T2>[] getdE() {
        return dE;
    }

    /**
     * Sets the bucket entries
     * @param dE
     */
    public void setdE(BucketDirectEntry<T, T2>[] dE) {
        this.dE = dE;
    }

    /**
     * Returns the max size
     * @return
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the max size
     * @param maxSize
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets the current size
     * @return
     */
    public int getCurSize() {
        return curSize;
    }

    /**
     * Sets the current size
     * @param curSize
     */
    public void setCurSize(int curSize) {
        this.curSize = curSize;
    }
}


/////////

package directChaining;

public class Main {
	

	    public static void main(String[] args) throws Exception {
	       // System.out.println("Hello world!");

	       directChainMap dchainMap = new directChainMap<Integer, Integer>(1024);

	        dchainMap.insert(1,1336);
	        dchainMap.insert(2,1337);
	        dchainMap.insert(3,1339);   
	        dchainMap.insert(3,13329);

	        dchainMap.printBuckets();

	        System.out.println("After : -----");

	        dchainMap.remove(1);

	        dchainMap.printBuckets();
	    }
	}



/////////////

package directChaining;

public class Node <T, T2>
{
    private T key;
    private T2 value;
    private Node next; // <T,T2> mitte

    public Node(T key, T2 value, Node next)
    {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public T2 getValue() {
        return value;
    }

    public void setValue(T2 value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////




//DOUBLY

package Doubly;
//Java Hashtabelle LinkedList mit DoublyLinkedList

class DoublyLinked {
	// Nodes deklarieren
	DoublyLinked next, prev;
	int data;

	// Konstruktor
	DoublyLinked(int data)
	{
		this.data = data;
		next = null;
		prev = null;
	}
}
class HashTableChainingDoublyLinkedList {
	// Hash Table deklarieren
	DoublyLinked[] hashTable;

	// einstellung der size der Hashtabelle
	int size;

	// Konstruktor
	HashTableChainingDoublyLinkedList(int hashTableSize)
	{
		// Leere Hashtabelle erzeugen
		hashTable = new DoublyLinked[hashTableSize];
		size = 0;
	}

	// Gucken, ob Hash leer ist
	public boolean isEmpty() { return size == 0; }

	// funktion zum löschen im Hash
	public void clear()
	{
		// Kapazität der Hashtabelle
		int len = hashTable.length;

		// Erstellen einer neuen leeren Hash-Tabelle
		// mit gleicher Anfangskapazität
		hashTable = new DoublyLinked[len];
		size = 0;
	}

	// return size of Hashtabelle
	public int getSize() { return size; }

	// value element hinzufügen
	public void insert(int value)
	{
		size++;

		// get position wo der value ist
		int position = hash(value);

		// value lagern
		DoublyLinked node
			= new DoublyLinked(value);

		DoublyLinked start = hashTable[position];

		if (hashTable[position] == null)
			hashTable[position] = node;
		else {
			node.next = start;
			start.prev = node;
			hashTable[position] = node;
		}
	}

	// löschen
	public void remove(int value)
	{
		try {
			// position der zu löschenden Value
			int position = hash(value);

			DoublyLinked start
				= hashTable[position];

			DoublyLinked end = start;

			// wenn die value am start gefunden wurde
			if (start.data == value) {
				size--;
				if (start.next == null) {
					// entferne den Value
					hashTable[position] = null;
					return;
				}

				start = start.next;
				start.prev = null;
				hashTable[position] = start;

				return;
			}

			// traversing the list
			// until the value is found
			while (end.next != null
				&& end.next.data != value)
				end = end.next;

			// die Liste durchlaufen bis der Wert gefunden ist
			if (end.next == null) {
				System.out.println("\nElement not found\n");
				return;
			}

			size--;

			if (end.next.next == null) {
				end.next = null;
				return;
			}

			end.next.next.prev = end;
			end.next = end.next.next;

			hashTable[position] = start;
		}
		catch (Exception e) {
			System.out.println("\nElement not found\n");
		}
	}

	// Definition der Hashfunktion
	private int hash(Integer x)
	{
		int hashValue = x.hashCode();

		hashValue %= hashTable.length;

		if (hashValue < 0)
			hashValue += hashTable.length;

		return hashValue;
	}

	// print funktion für Hash
	public void printHashTable()
	{
		System.out.println();
		for (int i = 0; i < hashTable.length; i++) {
			System.out.print("At " + i + ": ");

			DoublyLinked start = hashTable[i];

			while (start != null) {
				System.out.print(start.data + " ");
				start = start.next;
			}

			System.out.println();
		}
	}

	// main brudi
	public static void main(String[] args)
	{
		HashTableChainingDoublyLinkedList hashTab
			= new HashTableChainingDoublyLinkedList(5);

		hashTab.insert(99);
		hashTab.insert(23);
		hashTab.insert(36);
		hashTab.insert(47);
		hashTab.insert(80);

		hashTab.printHashTable();

		hashTab.insert(92);
		hashTab.insert(49);

		hashTab.printHashTable();

		hashTab.remove(99);

		hashTab.printHashTable();

		hashTab.clear();

		hashTab.printHashTable();

		System.out.println(hashTab.isEmpty());
	}
}






////// OFFENE HASH


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




///// QUADRATISCHE SONDIEREN



package quadratischesSondieren;

//Quadratisches Sondieren

class GFG {

	// print array
	static void printArray(int arr[])
	{

	
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	// funktion von quadratisches sondieren
	static void hashing(int table[], int tsize, int arr[],
						int N)
	{

		// durch array itterien
		for (int i = 0; i < N; i++) {

			// einstellung der hash value 

			int hv = arr[i] % tsize;

			// Wenn keine Kolission dann einfügen in die Tabelle
			if (table[hv] == -1)
				table[hv] = arr[i];
			else {

				// Wenn es eine Kollision gibt
				// quadratische Werte iterieren
				for (int j = 0; j < tsize; j++) {

					// erstelle neue hash value
					int t = (hv + j * j) % tsize;
					if (table[t] == -1) {

						// schleife nach einfügen abbrechen
						table[t] = arr[i];
						break;
					}
				}
			}
		}

		printArray(table);
	}

	// main
	public static void main(String args[])
	{
		int arr[] = { 50, 700, 76, 85, 92, 73, 101 };
		int N = 7;

		// size von hash table
		int L = 7;
		int hash_table[] = new int[L];

		// inistailisiere hash table
		for (int i = 0; i < L; i++) {
			hash_table[i] = -1;
		}

		// funktion aufrufen
		hashing(hash_table, L, arr, N);
	}
}





///// SEPERATE CHAINING


package seperate;

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
        map.add("h2i", 5);
        System.out.println(map.size());
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println (map.getBucketIndex("coder"));
    }
}