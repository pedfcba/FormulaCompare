/*
my codes generated at 2012-10-22
Administrator
*/

package formulaCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class PreFormula {

	String filepath;
	LinkedList<String> outputfilepath;
	public PreFormula()
	{
		outputfilepath = new LinkedList<String>();
	}
	
	public LinkedList<String> getOutputfilepath() {
		return outputfilepath;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "D:\\develop\\antlrTest\\src\\test.tex.tmp";
		PreFormula pf = new PreFormula();
		pf.process(path);
	}


	public void process(String path) {
		// TODO Auto-generated method stub
		List<String> s = new LinkedList<String>();
		try {
			s = rfile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proFormula(s);
	}


	public void proFormula(List<String> formula)
	{
		// TODO Auto-generated method stub
		if(filepath.isEmpty() || formula.isEmpty())
		{
			System.out.println("Ŀ�����ʽ�б�Ϊ��");
			return;
		}
			String newFolder = filepath.substring(0, filepath.lastIndexOf("\\")) + filepath.substring(filepath.lastIndexOf("\\"),filepath.indexOf("."));
		System.out.println(newFolder);
		File file = new File(newFolder);
		//δ���Ƿ���ڸ��ļ��н����ж�
		file.mkdirs();
			
		while(!formula.isEmpty())
		{
			System.out.println(formula.get(0));
			String temp = formula.get(0);

			//��ÿ�����ʽ�洢Ϊtmp�ļ����Ա��ʽ�ַ�����MD5������
			putFile(newFolder, temp);
			
			formula.remove(0);
		}
	}


	//���ļ��еı��ʽ�ֱ�洢���Ը��ļ��������ļ�����
	private void putFile(String newFolder, String content) {
		// TODO Auto-generated method stub
		String temp = content;
		//temp = content.substring("\\begin{displaymath}".length(), content.lastIndexOf("\\"));
		temp = temp.replace(" ", "");
		String path = newFolder + "\\" + encrypt(temp) + ".tmp";
		outputfilepath.add(path);
		try
		{
			PrintWriter out = new PrintWriter(new File(path).getAbsoluteFile());
			try
			{
				out.print(temp);
			}
			finally
			{
				out.close();
			}
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}


	//���ɸ��ִ���MD5��
	private String encrypt(String info) {
		// TODO Auto-generated method stub
		
		byte[] digest = null;
		try
		{
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(info.getBytes());
			digest = alg.digest();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		String temp = byt2hex(digest);
		return temp;
	}


	//ת��Ϊʮ��������
	private String byt2hex(byte[] digest) {
		// TODO Auto-generated method stub
		String hs = "";
		String stmp = "";
		for (int n = 0; n < digest.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(digest[n] & 0XFF));
			if(stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else 
			{
				hs = hs + stmp;
			}
		}
		return hs.toLowerCase();
	}


	//��ȡ�ļ����������ʽ���浽һ��list�У�����list
	public List<String> rfile(String path)  throws IOException{
		filepath = path;
		BufferedReader in = new BufferedReader(new FileReader(path));
		String s;
		List<String> list = new LinkedList<String>();
		while((s = in.readLine()) != null)
			list.add(s);
		in.close();
		return list;
	}

}
