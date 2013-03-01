
package formulaCompare;

import java.io.*;
import java.util.regex.*;
//import static net.mindview.util.Print.*;


public class Iput {
	
public String fpath;

public Iput()
{
	this.fpath = "";
}

//��ȡָ���ļ������ݲ����������ַ���
public String read(String filepath)
 throws IOException
{
	BufferedReader in = new BufferedReader(new FileReader(filepath));
	String s;
	StringBuilder sb = new StringBuilder();
	while((s = in.readLine()) != null)
		sb.append(s);
	in.close();
	this.fpath = filepath;
	return sb.toString();
}

//��ȡ��ƥ���ַ��������������ַ���д���м��ļ����������м��ļ��ĵ�ַ
public String splitstr(String fstr)
 throws IOException
{
//	String fpath;
	//��\\begin{displaymath}.*\\end{displaymath}Ϊƥ��������
	Pattern p = Pattern.compile("\\begin\\{displaymath\\}.*\\\\end\\{displaymath\\}",Pattern.COMMENTS);
	Matcher m = p.matcher(fstr);
	
	String tmpstr = "";
	String pth = this.fpath + ".tmp";
	//��ÿ����ʽд����ʱ�ļ�
	while(m.find())
	{
		tmpstr = m.group();
		
		System.out.println(tmpstr);
		
		tmpstr += "\n";
		fwriter(tmpstr,pth);
	}
	System.out.println(pth);
	return pth;
}

//��ָ��·�����ļ�д���ַ���
public void fwriter(String sent, String path)
{
	try{
		PrintWriter out = new PrintWriter(new File(path).getAbsoluteFile());
		try
		{
			out.print(sent);
		}
		finally
		{
		out.close();
		}
	}
	catch(IOException e){
		throw new RuntimeException(e);
	}
}

public static void main(String [] args)
throws IOException{
	Iput ipt = new Iput();
	String str = "\\begin{displaymath}D:\\develop\\texdata\\���ֱ�.tex";
	str = ipt.read(str);
	System.out.println(str);
}
}
