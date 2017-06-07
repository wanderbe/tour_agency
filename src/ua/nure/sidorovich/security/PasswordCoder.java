package ua.nure.sidorovich.security;

import java.io.UnsupportedEncodingException;
import java.security.*;
 
public class PasswordCoder {
	
public static final String SHA_256 = "SHA-256";
     
    public static String hash(String input, String algorithm){
    	 	
    	MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String result = "";
        try {
			digest.update(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println("Can't encoding file");
		}
        byte[] hash = digest.digest();
                
        String[] tempResult = PasswordCoder.byteArrayToBinaryArray(hash);
        tempResult = PasswordCoder.binaryArrayToHex(tempResult);
        
        StringBuilder tempResultBuilder = new StringBuilder();
        for(String s : tempResult){
        	tempResultBuilder = tempResultBuilder.append(s);
        }
        result = tempResultBuilder.toString();
        return result;
    }
    
	private static String[] byteArrayToBinaryArray(byte[] input) {
    	String tempResult = "";
    	String[] result = new String[input.length];
    	int i = 0;
        for(byte item: input){
        	if(item<0){
        		tempResult = Integer.toString(Math.abs(item)-1, 2);
        		int lengthZerroAdds = 8 - tempResult.length();
        		for(int y = 0; y <lengthZerroAdds; y++){
        			tempResult = "0" + tempResult;
        		}
        		result[i] = tempResult.replaceAll("1", "t")
        				.replaceAll("0", "1")
        				.replaceAll("t", "0");
        	}else {
        		tempResult = Integer.toString(item, 2);
        		int lengthZerroAdds = 8 - tempResult.length();
        		for(int y = 0; y <lengthZerroAdds; y++){
        			tempResult = "0" + tempResult;
        		}
        		result[i] = tempResult;
			}
        	i++;
        }
    	return result;
    }
	
	private static String[] binaryArrayToHex(String[] input) {
		String tempResult1 = "";
		String tempResult2 = "";
    	String[] result = new String[input.length];
    	int i = 0;
        for(String item: input){
           		tempResult1 = Integer.toHexString(Integer.parseInt(item.substring(0, 4), 2))
           				.toUpperCase();
           		tempResult2 = Integer.toHexString(Integer.parseInt(item.substring(4, 8), 2))
           				.toUpperCase();
        		result[i] = tempResult1 + tempResult2;
        	    i++;
        }
    	return result;

	}
     
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(hash("qwerty1@", SHA_256).length());
        System.out.println(hash("q", SHA_256));
        }
     
}
