
//1 ANDROID TO JAVA SERVER
void sendMetaAndData(final String filename){
	new AsyncTask<Void, Void, Void>() {
		@Override
		protected Void doInBackground(Void... params) {
			try 
			{
				Socket socket = new Socket("192.168.2.113", 4444);
				File myFile = new File(filename);
				byte[] mybytearray = new byte[(int) myFile.length()];

				FileInputStream fis = new FileInputStream(myFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				DataInputStream dis = new DataInputStream(bis);   
				dis.readFully(mybytearray, 0, mybytearray.length);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   

				//Sending file name and file size to the server
				dos.writeUTF(parseFilename(filename));   
				dos.writeLong(mybytearray.length);   
				dos.write(mybytearray, 0, mybytearray.length);   
				dos.flush();

				dis.close();
				dos.close();
				socket.close();
			} catch (Exception e) {		}
			return null;
		}
		@Override
		protected void onPostExecute(Void result){
			blink("file sent "+filename);
		}
	}.execute();
}
private static class ReceiveFileAndMeta extends Thread{
	Socket socket;
	public ReceiveFileAndMeta(Socket socket){
		this.socket=socket;
	}
	public void run(){
		try{
			int bytesRead;
			DataInputStream dis = new DataInputStream(socket.getInputStream()); 
			String filename = dis.readUTF();
			OutputStream os = new FileOutputStream(filename);
			long size = dis.readLong();  
			byte[] buffer = new byte[1024];   

			while (size > 0 && (bytesRead = dis.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)   
			{   
				os.write(buffer, 0, bytesRead);   
				size -= bytesRead;   
			}
			System.out.println("downloaded "+filename+" complete ");
			dis.close();
			os.close();
			socket.close();
		}
		catch(Exception e ){}
	}
}



// JAVA SERVER TO ANDROID
private static class SendFileWithFileName extends Thread
{
	private Socket socket;
	String filename;


	public SendFileWithFileName(Socket socket, String filename) {
		this.socket=socket;
		this.filename=filename;
	}

	public void run()
	{
		try {

			//Send file
			File myFile = new File(filename);
			byte[] mybytearray = new byte[(int) myFile.length()];

			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);   
			dis.readFully(mybytearray, 0, mybytearray.length);

			OutputStream os = socket.getOutputStream();

			//Sending file name and file size to the server
			DataOutputStream dos = new DataOutputStream(os);   
			dos.writeUTF(myFile.getName());   
			dos.writeLong(mybytearray.length);   
			dos.write(mybytearray, 0, mybytearray.length);   
			dos.flush();

			dis.close();
			dos.close();
			socket.close();

			System.out.println("file transfered "+filename);
		} catch (Exception e) {}

	}
}
public void download(View arg0) {
	new Thread(
			new Runnable() 
			{
				@Override
				public void run() 
				{
					try {
						Socket socket = new Socket("192.168.2.113", 4444);
						int bytesRead;
						DataInputStream dis = new DataInputStream(socket.getInputStream()); 
						String filename = dis.readUTF();   //<-
						OutputStream os = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+filename);//<-   
						long size = dis.readLong();   
						byte[] buffer = new byte[1024];   

						while (size > 0 && (bytesRead = dis.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)   
						{   
							os.write(buffer, 0, bytesRead);   
							size -= bytesRead;   
						}
						threadBlink("download "+filename+" complete ");
						dis.close();
						os.close();
						socket.close();

					} catch (Exception e) {}
				}
			}
			).start();
}









