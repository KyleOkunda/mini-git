import java.security.MessageDigest;
public class Hash {
    public String shaHash(String input) {
        byte[] hashBytes;
        StringBuilder hexString = null;

        try{
            //Create an instance of the md
        MessageDigest md = MessageDigest.getInstance("SHA-256");
            //provide the hash in bytes
        hashBytes = md.digest(input.getBytes());

        //convert to hex string
        hexString = new StringBuilder();
        for(byte b : hashBytes){

            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1){
                hexString.append("0");
            }
            hexString.append(hex);
            

        }

        System.out.println("Length: " + hexString.toString().length());
        System.out.println("Message Digest: " + hexString.toString());

        

        } catch(Exception e){
            e.printStackTrace();
        }

        String hashValue = hexString.toString().substring(54);
        
        return hashValue;
        

    }
}
