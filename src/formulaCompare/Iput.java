
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

//读取指定文件的内容并返回所有字符串
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

//读取并匹配字符串，将处理后的字符串写入中间文件，并返回中间文件的地址
public String splitstr(String fstr)
 throws IOException
{
//	String fpath;
	//以\\begin{displaymath}.*\\end{displaymath}为匹配规则查找
	Pattern p = Pattern.compile("\\begin\\{displaymath\\}.*\\\\end\\{displaymath\\}",Pattern.COMMENTS);
	Matcher m = p.matcher(fstr);
	
	String tmpstr = "";
	String pth = this.fpath + ".tmp";
	//将每个公式写入临时文件
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

//向指定路径的文件写入字符串
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
	String str = "\\begin{displaymath}D:\\develop\\texdata\\积分表.tex";
	str = ipt.read(str);
	System.out.println(str);
}
}
