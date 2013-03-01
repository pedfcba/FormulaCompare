/*
my codes generated at 2012-10-22
Administrator
 */

package formulaCompare;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NodeInit {

	private static final String needSwapOp = "\\sqrt\\int\\oint\\prod\\coprod\\sum\\frac";
	private String priorityfile = "D:\\develop\\antlrTest\\priority.txt";
	private String letterfile = "D:\\develop\\antlrTest\\letter.txt";
	private Map<String, Priority> op;
	private Map<String, Priority> letter;
	public NodeInit()
	{
		op = new HashMap<String ,Priority>();
		letter = new HashMap<String, Priority>();
		try {
			op = readPriority(op, priorityfile);
			letter = readPriority(letter, letterfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public NodeInit(String priorityfilepath, String letterfilepath)
	{
		priorityfile = priorityfilepath;
		letterfile = letterfilepath;
		op = new HashMap<String ,Priority>();
		try {
			op = readPriority(op, priorityfile);
			letter = readPriority(letter, letterfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//将制定地址的文本文件转为树形结构
	public MathNode process(String path) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(path));
		String s;
		s = in.readLine();
		in.close();
		return iniTree(preProcess(s));
	}

	//读取运算符性质并存储到Map容器priority中
	private Map<String, Priority> readPriority(Map<String, Priority> priority, String prioritypath) 	throws IOException{
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader(prioritypath));
		String s;
		while((s = in.readLine()) != null)
		{
			if(s.equals(""))
				continue;
			String array[] = s.split("\\t");
			Priority pr = new Priority();
			pr.setMark(array[1]);
			pr.setRank(array[2]);
			pr.setType(array[3]);
			priority.put(array[0], pr);
		}
		in.close();
		return priority;
	}

	//字符串的截取范围
	public class Slice
	{
		//起始位置
		private int start;
		//终结位置
		private int end;
		public Slice(int i, int j)
		{
			setStart(i);
			setEnd(j);
		}
		/**
		 * @param start the start to set
		 */
		public void setStart(int start) {
			this.start = start;
		}
		/**
		 * @return the start
		 */
		public int getStart() {
			return start;
		}
		/**
		 * @param end the end to set
		 */
		public void setEnd(int end) {
			this.end = end;
		}
		/**
		 * @return the end
		 */
		public int getEnd() {
			return end;
		}
	}

	//找到位于begin和end之间的一段字符串，返回起点和终点位置,若includeRange为真，返回范围包括begin和end
	public Slice findString(String source, String begin, String end, boolean includeRange)
	{
		Slice range = new Slice(source.indexOf(begin),source.indexOf(end));
		//若不存在begin或end字符串，返回-1,-1
		if(source.indexOf(begin) == -1 || source.indexOf(end) == -1)
		{
			range.setStart(-1);
			range.setEnd(-1);
			return range;
		}
		//获取第一个关键字的下一个位置
		String startsource = source.substring(range.getStart()+1);
		//获取第二个关键字初始位置的下一个位置
		String endsource = source.substring(range.getEnd()+1);
		int count = 0;
		int startpos = range.getStart();
		int endpos = range.getEnd();
		//		System.out.println("s: " + startpos + "、e: " + endpos);
		//		System.out.println();
		//找寻与第一个关键字成对的第二关键字位置
		while(startpos != -1 && endpos != -1 && startpos < endpos)
		{
			//是否在范围内有其他第一关键字，若存在，则计数
			while(startsource.indexOf(begin) != -1)
			{
				//超出第二关键字所在位置的，直接跳出
				if(startpos + startsource.indexOf(begin)+1 > endpos)
					break;
				startpos += startsource.indexOf(begin)+1;
				startsource = startsource.substring(startsource.indexOf(begin)+1);
				//	System.out.println("s: " + startpos);
				count++;
			}
			//若没有，则第二关键字位置已找到
			if(count == 0)
				break;
			//若有，则后推count个第二关键字，再在新范围中继续寻找
			while(endsource.indexOf(end) != -1 && count != 0)
			{
				endpos += endsource.indexOf(end)+1;
				endsource = endsource.substring(endsource.indexOf(end)+1);
				//	System.out.println("e: " + endpos);
				count--;
			}
		}

		//范围中不含begin和end
		if(includeRange == false)
		{
			range.setStart(range.getStart() + begin.length());
			range.setEnd(endpos);
		}

		//范围中包含begin和end
		else
		{
			endpos += end.length();
			range.setEnd(endpos);
		}
		return range;
	}



	//构造数学表达式树
	public MathNode iniTree(String origin) {
		// TODO Auto-generated method stub
		//若外部有括号，去除括号
		//		System.out.println(origin);
		if(origin.startsWith("{") && origin.endsWith("}") || origin.startsWith("(") && origin.endsWith(")") || origin.startsWith("[") && origin.endsWith("]"))
		{
			Slice sl = new Slice(-1, -1);
			sl = findString(origin, "{", "}", true);
			if(sl.getStart() == 0 && sl.getEnd() == origin.length())
				origin = origin.substring(1, origin.length()-1);
			sl = findString(origin, "(", ")", true);
			if(sl.getStart() == 0 && sl.getEnd() == origin.length())
				origin = origin.substring(1, origin.length()-1);
			sl = findString(origin, "[", "]", true);
			if(sl.getStart() == 0 && sl.getEnd() == origin.length())
				origin = origin.substring(1, origin.length()-1);
		}
		//若为单个元素，则作为叶子结点返回
		if(isSingle(origin))
		{
			MathNode single = new MathNode(origin);
			if(op.containsKey(origin))
			{
				single.mark = op.get(origin).mark;
				single.type = op.get(origin).type;
				single.rank = op.get(origin).rank;
			}
			else if(isNum(origin))
			{
				single.data = origin;
				single.mark = Mark.SINGLE;
				single.type = Type.NUM;
				single.rank = Integer.MAX_VALUE;
			}
			else
			{
				single.data = origin;
				single.mark = Mark.SINGLE;
				single.type = Type.VAR;
				single.rank = Integer.MAX_VALUE;				
			}
			return single;
		}
		MathNode mn = new MathNode(origin);
		//查找latex语句
		//		int pos = origin.indexOf("\\");
		int begin = 0;
		int end = origin.length() - 1;
		//若无，则按照正常表达式构造
		//		if(pos == -1)
		//		{
		int p = findHeadSingleOp(origin);
		//找到优先级最小的运算符
		if(p != -1)
		{
			String temp;
			//	System.out.println("p: " + p);
			temp = findOp(origin, p);
			//	System.out.println("temp: " + temp);
			if(temp.length() != origin.length())
			{
				mn.data = temp;
				mn.mark = op.get(mn.data).mark;
				mn.type = op.get(mn.data).type;
				mn.rank = op.get(mn.data).rank;
				//寻找左右子树的
				mn.LNode = new MathNode(iniTree(origin.substring(begin, p)));
				mn.RNode = new MathNode(iniTree(origin.substring(p+temp.length(), end+1)));
			}		
		}
		//若未找到，判断是否为连续的字符串
		else
		{
			if(isNum(origin))
			{
				mn.data = origin;
				mn.mark = Mark.SINGLE;
				mn.type = Type.NUM;
				mn.rank = Integer.MAX_VALUE;
			}
			else if(origin.length() == 1)
			{
				mn.data = origin;
				mn.mark = Mark.SINGLE;
				mn.type = Type.VAR;
				mn.rank = Integer.MAX_VALUE;
			}
			//是连续的变量，则在字符中间插入乘号
			else
			{
				String newOrigin = "";
				//针对变量插入乘号
				for (int i = 0; i < origin.length();)
				{
					//latex符号
					if(origin.charAt(i) == '\\')
					{
						newOrigin += findLstring(origin, i);
						i += findLstring(origin, i).length();
						//			System.out.println(newOrigin);
					}
					//普通字符
					else
					{
						newOrigin += origin.charAt(i);
						i++;
					}
					//不是最后一位的，增加乘号
					if(i != origin.length())
						newOrigin += "*";
				}
				mn = iniTree(newOrigin);
			}
		}
		return mn;
	}

	private String findLstring(String origin, int begin) {
		// TODO Auto-generated method stub
		int end = begin+1;
		while(!op.containsKey(origin.substring(begin, end)) && end < origin.length())
			end++;
		if(!op.containsKey(origin.substring(begin, end)))
			return origin;
		return origin.substring(begin, end);
	}

	//找到origin字符串中以begin位置开头的运算符并返回,若未找到则返回原字符串
	private String findOp(String origin, int begin) {
		// TODO Auto-generated method stub
		int end = begin+1;
		while(!op.containsKey(origin.substring(begin, end)) && end < origin.length())
			end++;
		if(!op.containsKey(origin.substring(begin, end)))
			return origin;
		return origin.substring(begin, end);
	}

	//若该字符串为一个独立的元素，返回真
	private boolean isSingle(String origin) {
		// TODO Auto-generated method stub
		if(origin.length() == 1 || isOp(origin) || isNum(origin) || isVar(origin))
			return true;
		return false;
	}

	//若为latex格式变量，返回真
	private boolean isVar(String origin) {
		// TODO Auto-generated method stub
		if(letter.containsKey(origin))
		{
			return true;
		}
		else
			return false;
	}

	//判断字符串是否为数字
	private boolean isNum(String origin) {
		// TODO Auto-generated method stub
		int i = 0;
		boolean mark = false;
		for(; i < origin.length(); i++)
		{
			if(origin.charAt(i) >= 48 && origin.charAt(i) <= 58)
				continue;
			if (origin.charAt(i) == '.' && mark == false)
			{
				mark = true;
				continue;
			}
			if(i == 0 && origin.charAt(i) == '-' || i == origin.length()-1 && origin.charAt(i) == '%')
				continue;
			break;
		}
		if(i == origin.length())
			return true;
		return false;
	}

	//若为包含在算符集中的元素，返回真
	private boolean isOp(String origin) {
		// TODO Auto-generated method stub
		//		System.out.println(origin);
		if(op.containsKey(origin))
		{
			return true;
		}
		else
			return false;
	}

	//找到优先级最低的普通类型运算符并返回位置
	private int findHeadSingleOp(String origin) {
		// TODO Auto-generated method stub
		origin = origin.replace("(", "{");
		origin = origin.replace(")", "}");
		origin = origin.replace("[", "{");
		origin = origin.replace("]", "}");
		int pos = -1;
		int rank = 0;
		boolean found = false;
		//没有括号的情况
		if(origin.indexOf("{") == -1)
		{
			//寻找优先级最小且下标最小的运算符坐标
			for(Map.Entry<String, Priority> m: op.entrySet())
			{
				String temp = m.getKey();
				Priority ptemp = m.getValue();
				if(origin.contains(temp))
				{
					//				System.out.println("这次统一的是: ");
					//				System.out.println(node.data);
					if(ptemp.rank > rank && found == false)
					{
						pos = origin.indexOf(temp);
						rank = ptemp.rank;
						found = true;
					}
					else 
					{
						if(ptemp.rank == rank)
						{
							if(pos > origin.indexOf(temp))
								pos = origin.indexOf(temp);
							rank = ptemp.rank;
						}
						else if(ptemp.rank < rank)
						{
							rank = ptemp.rank;
							pos =origin.indexOf(temp);
						}
					}
					//				System.out.println(": ");
					//				System.out.println(node.data);
				}
			}
		}
		//有括号的情况
		else
		{
			Slice slice = new Slice(-1,-1);
			while(origin.contains("{"))
			{
				slice = findString(origin, "{", "}", true);
				String temp = "@@";
				for(int i = 0; i < slice.getEnd()-slice.getStart()-2; i++)
					temp += "@";
				origin = origin.replace(origin.substring(slice.getStart(), slice.getEnd()), temp);
				//				System.out.println(temp);
				//				System.out.println(origin);
			}
			pos = findHeadSingleOp(origin);
		}
		return pos;
	}


	//预处理表达式字符串，变为中序式
	public String preProcess(String formula) {
		// TODO Auto-generated method stub
		formula = formula.replace(" ", "");
		//为'_'、'^'后的孤立元素增加括号
		//formula = AddBracket(formula, "_");
//		System.out.println(formula);
		//formula = AddBracket(formula, "^");

		//将表达式转为中序
		String temp = formula;
		//查找可能需要中序化的运算符
		while(temp.contains("\\"))
		{
			int p = temp.indexOf("\\");
			//检查该位置是否为latex运算符号
			String op = findOp(temp, p);
			//若不是，跳过该符号找下一个
			if(op.length() == temp.length())
			{
				temp = temp.substring(p+1);
				continue;
			}
			//若是，则检查是否需要中序化
			if(needSwap(op))
			{
				Slice slice = new Slice(p,p+op.length());
				//将交换后的字符串存储到该变量
				String swapString = "";
					
				System.out.println("op的下一个: " + temp.charAt(p+op.length()));
				
				switch(temp.charAt(p+op.length()))
				{
				//找到下标为_{}的坐标

				case '_': 
					slice = findString(temp, "{", "}", true);
					//将算符和带下标的部分互换，去除'_'符号
					swapString = temp.substring(slice.getStart()+1, slice.getEnd()) + op;
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					break;
				
				case '[': 
					slice = findString(temp, "[", "]", true);			
					//将算符和括号部分互换
					swapString = temp.substring(slice.getStart(), slice.getEnd()) + op;
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					break;
					
				case '{':	
					slice = findString(temp, "{", "}", true);			
					//将算符和括号部分互换
					swapString = temp.substring(slice.getStart(), slice.getEnd()) + op;
					//		System.out.println("要变成： " + swapString);
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					//		System.out.println("变成了： " + formula);
					break;
				default:
					break;
				}
			}
			temp = temp.substring(p+op.length());
		}

		/*
		//增加\LINK算符
		if(formula.indexOf('^') != -1)
		{
			temp = formula;
			while(temp.indexOf('^') != -1)
			{
				//找到要替换的字符串段
				String tmp = temp.substring(findString(temp, "^{", "}").getStart()-2,findString(temp, "^{", "}").getEnd()+1);
				//为其末尾增加\LINK算符
				formula.replace(tmp, tmp + "\\LINK");
				temp = temp.substring(1);
			}
		}
		 */
		//		System.out.println("formula: " + formula);
//		System.out.println("预处理阶段：" + formula);
		return formula;
	}

	//为key后面的元素增加括号
	private String AddBracket(String formula, String key) {
		// TODO Auto-generated method stub
		if(formula.indexOf(key) != -1)
		{
			//tmp以key开头
			String tmp = formula.substring(formula.indexOf(key));
			while(tmp.indexOf(key) != -1)
			{
				tmp = tmp.substring(tmp.indexOf(key));
				//若不存在括号
				if(tmp.charAt(1) != '{' && tmp.charAt(1) != '[' && tmp.charAt(1) != '(')
				{
					String tmps = "";
					if(tmp.charAt(1) == '\\')
					{
						int begin = 1;
						int end = begin+1;
						while(!letter.containsKey(tmp.substring(begin, end)) && end < tmp.length())
							end++;
				//		System.out.println(tmp.substring(begin, end));
						if(letter.containsKey(tmp.substring(begin, end)))	
							tmps = tmp.replaceFirst("\\" + tmp.substring(begin, end), "{\\"+tmp.substring(begin, end)+"}");
						formula = formula.replace(tmp, tmps);
					}
					else if(tmp.charAt(1) >= '0' && tmp.charAt(1) <= '9' || tmp.charAt(1) == '-')
					{
						int begin = 1;
						int end = begin;
						while(end < tmp.length() && (tmp.charAt(end) >= '0' && tmp.charAt(end) <= '9' || tmp.charAt(end) == '.'))
							end++;
						tmps = tmp.replaceFirst(tmp.substring(begin, end), "{"+tmp.substring(begin, end)+"}");
					}
					else
					{
						tmps = tmp.replaceFirst("" + tmp.charAt(1), "{"+tmp.charAt(1)+"}");
					}
					formula = formula.replace(tmp, tmps);
				}
				tmp = tmp.substring(1);
			}
		}
		return formula;
	}
	//检查输入字符串是否为需要交换的运算符
	private boolean needSwap(String string) {
		// TODO Auto-generated method stub
		//	System.out.println("是否需要交换： " + string);
		if(needSwapOp.contains(string))
		{
			//		System.out.println("是");
			return true;
		}
		return false;
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub

		NodeInit ni = new NodeInit();
		String test = "astart{x{y}{n}{x}start{x}}endstart{x}}enddenddsstartdend";
		Slice range = ni.findString(test, "start", "end", false);
		//		System.out.println(range.getStart() + "、" + range.getEnd());
		/*
		int i = 0;
		while (i != test.length())
		{
			System.out.println(i + ": " + test.charAt(i));
			i++;
		}
		System.out.println(test.substring(range.getStart(), range.getEnd()));
		 */
		//		String tmp = "\\sqrt{2}\\cdot\\sqrt[4]{2}=\\sqrt[4]{8}";
		String tmp = "90 < ( \\varsigma \\times ( 36 ^ ( \\alpha * 91 ) ) )";
		MathNode mn = ni.iniTree(ni.preProcess(tmp));
				mn.treeini();
				mn.pnode();
				mn.texOut();
		System.out.println(ni.isNum("-314%"));
	}
}
