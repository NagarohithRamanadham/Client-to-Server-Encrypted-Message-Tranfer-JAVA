//This programs starts the Client
//To send Data to Server
import java.io.DataOutputStream;

//To create connection with Server
import java.net.Socket;

//To encode the SecretKey
import java.util.Base64;

//To take input from client
import java.util.Scanner;

//Used for Encryption
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class ClientMachine {

    public static void main(String[] args) throws Exception
    {


        KeyGenerator key = KeyGenerator.getInstance("AES");//generates key based on DES algorithm
		
		key.init(128);// keysize must be equal to 112 or 168 for this DES Algo(javax.crypto.KeyGenerator@6aa8ceb6)

		SecretKey secretKey = key.generateKey();//generate a secret key(com.sun.crypto.provider.DESedeKey@b069ba51)
        
	/*
	client and server are in the same machine so in place of 
	IP address localhost is passed. Can also pass "127.0.0.1" as local host.
	*/
        

	System.out.println("-----------------------------------------");
	System.out.println("Connecting Server");
	Thread.sleep(5000);//------------------------------------------------- //
	Socket socket = new Socket("localhost",1234);
	System.out.println("Connection Successfull");
	System.out.println("-----------------------------------------");
	
	
	//To input data in client
	Scanner clientInput = new Scanner(System.in);
	

	
	//To receive data from server
	DataOutputStream serverDataTransfer = new DataOutputStream(socket.getOutputStream());
	
    //DataOutputStream encBytes = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

	
	System.out.println("Enter Text to be sent to server");
	String str = clientInput.nextLine();
                


	System.out.println("Converting the message into bytes");
	Thread.sleep(5000);//-------------------------------------------
    byte[] plainTextByte = str.getBytes("UTF8");//store the ascii values of the message

	

    //ENCRYPTION
	System.out.println("-----------------------------------------");
	System.out.println("Encrypting the bytes of the original message");
	Thread.sleep(5000);//--------------------------------------------
    byte[] encryptedBytes = encrypt(plainTextByte, secretKey);//encrypts the ascii value
	System.out.println("Encrypting the bytes of the original message Successfull");
	System.out.println("-----------------------------------------");


	System.out.println("-----------------------------------------");
	System.out.println("Converting the encrypted bytes into string to dsiplay how it looks like");
	Thread.sleep(5000);//---------------------------------------------
    String encryptedMsg = new String(encryptedBytes, "UTF8");
	System.out.println("Conversion Successfull");
	System.out.println("-----------------------------------------");
    



	System.out.println("-------------------------------------------");
	System.out.println("Sending the encrypted message which of type string to Server");
	Thread.sleep(5000);//---------------------------------------
    serverDataTransfer.writeUTF(encryptedMsg);//send encrypted msg to server
	System.out.println("Sent Successfully");
	System.out.println("-------------------------------------------");
	
	

	serverDataTransfer.writeInt(encryptedBytes.length);//send encrypted msg length
	
	
    //encBytes.writeInt(encryptedBytes.length);  

	System.out.println("--------------------------------------------");
	System.out.println("Sending the encrypted bytes to Server one by one");
    for(int i=0;i<encryptedBytes.length;i++)
    {
		Thread.sleep(2000);
        serverDataTransfer.writeByte(encryptedBytes[i]);//Sending encryprted bytes to server to process the decryption
    }
	System.out.println("Encrypted Bytes sent successfully");
	System.out.println("-----------------------------------------------");
     

	//Encoding the Secret Key
	System.out.println("------------------------------------------------");
	System.out.println("Encoding the Secret key");
	Thread.sleep(5000);//-----------------
	String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
	System.out.println("Encoding the Secret key Successfull");
	System.out.println("------------------------------------------------");
	

	System.out.println("------------------------------------------------");
	System.out.println("Sending encoded Secret key to server");
	Thread.sleep(5000);//------------------------
	serverDataTransfer.writeUTF(encodedKey);//Send encoded secret key to server
	System.out.println("Key sent Successfully");
	System.out.println("------------------------------------------------");
	

	socket.close();
	clientInput.close();
    
}
    
	//AES algorithm used to encrypt the message
    static byte[] encrypt(byte[] plainTextByte, SecretKey secretKey)throws Exception {

		Cipher encrypt=Cipher.getInstance("AES");//cipher that uses Data Encryption Standards algorithm which conists of 168 bit size secret key
        encrypt.init(Cipher.ENCRYPT_MODE, secretKey);//initialising to encryption mode
		byte[] encryptedBytes = encrypt.doFinal(plainTextByte);
		System.out.println("Encryption Successfull");
		return encryptedBytes;
	}


    
}
