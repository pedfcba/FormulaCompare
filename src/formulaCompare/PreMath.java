/*
 * 预处理完整的tex文件，提取
 */
package formulaCompare;

import java.io.*;
//import java.util.LinkedList;
//import java.util.List;

public class PreMath {
	
	//文件地址
	protected String filepath;
	private String outputpath;
	//private LinkedList<String> mathlist;

	public PreMath()
	{
		this.filepath = "";
	}

	public String getfilepath()
	{
		return this.filepath;
	}

	public String getOutputpath()
	{
		return outputpath;
	}
	public void setfilepath(String path){
		this.filepath = path;
	}
	
	//从input路径读取文件，并返回文本内容字符串
	private String rfile(String input) throws IOException{
		this.setfilepath(input);
		BufferedReader in = new BufferedReader(new FileReader(input));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine()) != null)
			sb.append(s);
		in.close();
		return sb.toString();
	}

	//按字读取字符串，以自动机截取公式段,并输出到tmp文件中
	private void prostr(String input)
	{
		//		System.out.println(input);

//		String path = this.getfilepath() + ".tmp";
		
		while(input.contains("$$"))
		{
			String tmp = input.replaceFirst("\\$\\$", "\\begin{displaymath}");
			String temp = tmp.replaceFirst("\\$\\$", "\\end{displaymath}");
			input = temp;
		}
		if(input.contains("\\[") && input.contains("\\]"))
		{
			String tmp = input.replace("\\[", "\\begin{displaymath}");
			String temp = tmp.replace("\\]", "\\end{displaymath}");
			input = temp;
		}
		
		
		String tmp = "";
		String transtmp = "";
		//自动机初始状态为0
		int status = 0;
		StringReader in = new StringReader(input);
		try {
			int s = in.read();
			char stmp = (char)s;
			while (s != -1)
			{
				//				System.out.print((char)s);
				//				System.out.println();
				stmp = (char)s;
				switch(stmp)
				{
				case '\\':
					//状态1为开始读取公式字符串的状态
					if (status >= 0 && status <= 18)
					{
						status = 1;
						tmp = String.valueOf(stmp);

					//	System.out.println(tmp);
						
					}
					//状态19为读取公式字符串的状态，遇到‘\’转为状态20，准备结束
					else 
					{
						status = 20;
						tmp += String.valueOf(stmp);

			//			System.out.println(tmp);
					} 
					break;
				case 'b':
					if (status == 1)
					{
						tmp += String.valueOf(stmp);
						status = 2;
		//				System.out.println(tmp);
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'e':
					if (status == 2)
					{
						tmp += String.valueOf(stmp);
						
						status = 3;

			//			System.out.println(stmp);
			//			System.out.println(tmp);
			//			this.moutput(tmp,this.getfilepath());
					}
					else if (status == 20)
					{
						tmp += String.valueOf(stmp);
						status = 21;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status > 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'g':
					if (status == 3)
					{
						tmp += String.valueOf(stmp);
						status = 4;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'i':
					if (status == 4)
					{
						tmp += String.valueOf(stmp);
						status = 5;
					}
					else if (status == 8)
					{
						tmp += String.valueOf(stmp);
						status = 9;
					}
					else if (status == 25)
					{
						tmp += String.valueOf(stmp);
						status = 26;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'n':
					if (status == 5)
					{
						tmp += String.valueOf(stmp);
						status = 6;
					}
					else if (status == 21)
					{
						tmp += String.valueOf(stmp);
						status = 22;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case '{':
					if (status == 6)
					{
						tmp += String.valueOf(stmp);
						status = 7;
					}
					else if (status == 23)
					{
						tmp += String.valueOf(stmp);
						status = 24;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'd':
					if (status == 7)
					{
						tmp += String.valueOf(stmp);
						status = 8;
					}
					else if (status == 22)
					{
						tmp += String.valueOf(stmp);
						status = 23;
					}
					else if (status == 24)
					{
						tmp += String.valueOf(stmp);
						status = 25;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 's':
					if (status == 9)
					{
						tmp += String.valueOf(stmp);
						status = 10;
					}
					else if (status == 26)
					{
						tmp += String.valueOf(stmp);
						status = 27;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'p':
					if (status == 10)
					{
						tmp += String.valueOf(stmp);
						status = 11;
					}
					else if (status == 27)
					{
						tmp += String.valueOf(stmp);
						status = 28;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'l':
					if (status == 11)
					{
						tmp += String.valueOf(stmp);
						status = 12;
					}
					else if (status == 28)
					{
						tmp += String.valueOf(stmp);
						status = 29;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'a':
					if (status == 12)
					{
						tmp += String.valueOf(stmp);
						status = 13;
					}
					else if (status == 15)
					{
						tmp += String.valueOf(stmp);
						status = 16;
					}
					else if (status == 29)
					{
						tmp += String.valueOf(stmp);
						status = 30;
					}
					else if (status == 32)
					{
						tmp += String.valueOf(stmp);
						status = 33;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'y':
					if (status == 13)
					{
						tmp += String.valueOf(stmp);
						status = 14;
					}
					else if (status == 30)
					{
						tmp += String.valueOf(stmp);
						status = 31;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'm':
					if (status == 14)
					{
						tmp += String.valueOf(stmp);
						status = 15;
					}
					else if (status == 31)
					{
						tmp += String.valueOf(stmp);
						status = 32;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 't':
					if (status == 16)
					{
						tmp += String.valueOf(stmp);
						status = 17;}

					else if (status == 33)
					{
						tmp += String.valueOf(stmp);
						status = 34;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case 'h':
					if (status == 17)
					{
						tmp += String.valueOf(stmp);
						status = 18;
					}
					else if (status == 34)
					{
						tmp += String.valueOf(stmp);
						status = 35;
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;
				case '}':
					if (status == 18)
					{
						tmp += String.valueOf(stmp);
						status = 19;
					}
					//状态35为结束状态，获取'}'后代表一段公式已经成功读取
					else if (status == 35)
					{
						status = 0;
						tmp += String.valueOf(stmp);

	//					System.out.println(tmp);
						transtmp += tmp;
						transtmp += "\n";
					}
					else if (status <= 18)
					{
						status = 0;
					}
					else if (status == 19)
						tmp += String.valueOf(stmp);	
					else if (status >= 20)
					{
						tmp += String.valueOf(stmp);	
						status = 19;
					}
					break;

				default:
					if (status >= 19)
					{
						tmp += String.valueOf(stmp);		
						status = 19;
			//			System.out.println(tmp);				
					}
						
					break;

				}
				s = in.read();
	//			System.out.println((char)s);
			}

			this.moutput(transtmp,this.getfilepath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//向指定路径的临时文件写入字符串
	private void moutput(String math,String filepath){
		String path = filepath + ".tmp";
		outputpath = path;
		try{
			PrintWriter out = new PrintWriter(new File(path).getAbsoluteFile());
			try
			{
				out.print(math);
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

	//完成对输入文件的预处理
	public void process(String path)
	{
		try {
			this.prostr(this.rfile(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		String path = "D:\\develop\\antlrTest\\src\\test.tex";
		PreMath pm = new PreMath();
		pm.process(path);
	//	System.out.println(s);

	}
}
