import java.util.Arrays; 
class Main{ 
    public static void main(String args[]){ 
        //define an array 
        //int intArray[] = {10,20,30,40,50,60,70,80,90}; 
        int intArray[] = {10,20,30,40,50,60,70,80,90}; 
        System.out.println("The input Array : " + Arrays.toString(intArray)); 
        //define the key to be searched 
        int key = 50; 
        System.out.println( "The key to be searched:" + key); 
        //call binarySearch method on the given array with key to be searched
        int result = Arrays.binarySearch(intArray,key); 
        //print the return result 
        if (result <0){
            System.out.println(" Key is not found in the array!"); 
        }else{
            System.out.println( "Key is found at index: " +result +  " in the array.");
        }
    } 
 }