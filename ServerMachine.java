//import InputOutput Streams
import java.io.DataInputStream;


//Used for Decryption
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


//importing Socket classes from 'net' Superclass to connect to client
import java.net.Socket;
import java.net.ServerSocket;//interface that enables passing of information from node to node



//to encode the secret Key
import java.util.Base64;


public class ServerMachine
{
    public static void main(String[] args) throws Exception
    {

        //creating a server that accepts request from client
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server Started!!");
        System.out.println("waiting for Client Request...");



        //create a socket and accept the request from client
        Socket socket = server.accept();
        System.out.println("Client connected successfully");


        //creating scanner object to manipulate requests that are made by clients
        DataInputStream getClientInput = new DataInputStream(socket.getInputStream());


        //DataInputStream encBytes = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

/*
based on type of server the below code changes
The server i created just accepts and prints the data
*/


 
        String encryptedMsg = getClientInput.readUTF();//Store the encrypted message sent from client


        System.out.println("Encrypted Message Successfully Receieved from Client");
        System.out.println("Encrypted Message is:"+encryptedMsg);


    
        //Decryption Starts from here
        int length= getClientInput.readInt();

        
        //receving the length of byte values of enc message
        byte[] encryptedBytes=new byte[length];



        System.out.println("------------------------------");           //16
        for(int i=0;i<length;i++)
        {
            encryptedBytes[i]=getClientInput.readByte();
            System.out.println(encryptedBytes[i]);//reading encrypted bytes that are being sent from client to prepare for decryption
        }
        System.out.println("------------------------------");


        String encodedKey = getClientInput.readUTF();//receiving the encoded secret key which is the MAIN KEY to decrypt the message

        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");//Decode the key using AES algorithm(Data Encryption Standard)


        System.out.println("Secret key is:"+secretKey);


        //Decrypt the encrypted message that is sent from client using the decoded secretKey
        byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);
		String decryptedText = new String(decryptedBytes, "UTF8");


		System.out.println("Decrypted Message is:" + decryptedText);

        //close Connections
        server.close();
        

    }

    //AES algorithm is used to decrypt the Message
    static byte[] decrypt(byte[] encryptedBytes, SecretKey secretKey)throws Exception {
        
        Cipher decrypt = Cipher.getInstance("AES");
		decrypt.init(Cipher.DECRYPT_MODE, secretKey);//initialising to decryption mode
		byte[] decryptedBytes = decrypt.doFinal(encryptedBytes);
		return decryptedBytes;
	}
}

