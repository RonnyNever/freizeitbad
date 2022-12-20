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
