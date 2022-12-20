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


