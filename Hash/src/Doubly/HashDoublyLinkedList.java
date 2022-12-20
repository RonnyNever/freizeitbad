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
