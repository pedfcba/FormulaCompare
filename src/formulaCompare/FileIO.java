package formulaCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileIO {
	BufferedReader instream;
	PrintWriter outputstream;


	public FileIO()
	{

	}


	//从filename路径读取文件，并返回输入流
	public BufferedReader ReadStringStream(String filename) throws FileNotFoundException
	{
		instream = new BufferedReader(new FileReader(filename));
		return instream;
	}

	public PrintWriter StringStreamOutput(StringBuilder input, String filename) throws FileNotFoundException
	{
		outputstream = new PrintWriter(new File(filename).getAbsoluteFile());
		return outputstream;
	}

	public void Close() throws IOException
	{
		if(instream != null)
			instream.close();
		if(outputstream != null)
		{
			outputstream.flush();
			outputstream.close();
		}
	}

	public String getLine() throws IOException {
		// TODO Auto-generated method stub
		String temp;
		temp = instream.readLine();
		return temp;
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileIO fio = new FileIO();
		fio.ReadStringStream("D:\\generated.txt");
		String temp;
		while((temp = fio.getLine()) != null)
			System.out.println(temp);
		fio.Close();
	}



}
