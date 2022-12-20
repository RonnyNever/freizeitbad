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
    public void insert(Node<T,T2> node)
    {
        if(this.isEmpty()) {
            start = node;
            curSize = 1;
            return;
        }

        var n2 = searchNode(node.getKey());
        if(n2 != null)
        {
            n2.setValue(node.getValue());
            return;
        }

        setCursor(curSize-1);
        cursor.setNext(node);
        curSize++;
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
