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
        dE[hashedIndex].insert(newNode);
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
}
