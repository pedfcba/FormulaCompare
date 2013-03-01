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

	//���ƶ���ַ���ı��ļ�תΪ���νṹ
	public MathNode process(String path) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(path));
		String s;
		s = in.readLine();
		in.close();
		return iniTree(preProcess(s));
	}

	//��ȡ��������ʲ��洢��Map����priority��
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

	//�ַ����Ľ�ȡ��Χ
	public class Slice
	{
		//��ʼλ��
		private int start;
		//�ս�λ��
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

	//�ҵ�λ��begin��end֮���һ���ַ��������������յ�λ��,��includeRangeΪ�棬���ط�Χ����begin��end
	public Slice findString(String source, String begin, String end, boolean includeRange)
	{
		Slice range = new Slice(source.indexOf(begin),source.indexOf(end));
		//��������begin��end�ַ���������-1,-1
		if(source.indexOf(begin) == -1 || source.indexOf(end) == -1)
		{
			range.setStart(-1);
			range.setEnd(-1);
			return range;
		}
		//��ȡ��һ���ؼ��ֵ���һ��λ��
		String startsource = source.substring(range.getStart()+1);
		//��ȡ�ڶ����ؼ��ֳ�ʼλ�õ���һ��λ��
		String endsource = source.substring(range.getEnd()+1);
		int count = 0;
		int startpos = range.getStart();
		int endpos = range.getEnd();
		//		System.out.println("s: " + startpos + "��e: " + endpos);
		//		System.out.println();
		//��Ѱ���һ���ؼ��ֳɶԵĵڶ��ؼ���λ��
		while(startpos != -1 && endpos != -1 && startpos < endpos)
		{
			//�Ƿ��ڷ�Χ����������һ�ؼ��֣������ڣ������
			while(startsource.indexOf(begin) != -1)
			{
				//�����ڶ��ؼ�������λ�õģ�ֱ������
				if(startpos + startsource.indexOf(begin)+1 > endpos)
					break;
				startpos += startsource.indexOf(begin)+1;
				startsource = startsource.substring(startsource.indexOf(begin)+1);
				//	System.out.println("s: " + startpos);
				count++;
			}
			//��û�У���ڶ��ؼ���λ�����ҵ�
			if(count == 0)
				break;
			//���У������count���ڶ��ؼ��֣������·�Χ�м���Ѱ��
			while(endsource.indexOf(end) != -1 && count != 0)
			{
				endpos += endsource.indexOf(end)+1;
				endsource = endsource.substring(endsource.indexOf(end)+1);
				//	System.out.println("e: " + endpos);
				count--;
			}
		}

		//��Χ�в���begin��end
		if(includeRange == false)
		{
			range.setStart(range.getStart() + begin.length());
			range.setEnd(endpos);
		}

		//��Χ�а���begin��end
		else
		{
			endpos += end.length();
			range.setEnd(endpos);
		}
		return range;
	}



	//������ѧ���ʽ��
	public MathNode iniTree(String origin) {
		// TODO Auto-generated method stub
		//���ⲿ�����ţ�ȥ������
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
		//��Ϊ����Ԫ�أ�����ΪҶ�ӽ�㷵��
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
		//����latex���
		//		int pos = origin.indexOf("\\");
		int begin = 0;
		int end = origin.length() - 1;
		//���ޣ������������ʽ����
		//		if(pos == -1)
		//		{
		int p = findHeadSingleOp(origin);
		//�ҵ����ȼ���С�������
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
				//Ѱ������������
				mn.LNode = new MathNode(iniTree(origin.substring(begin, p)));
				mn.RNode = new MathNode(iniTree(origin.substring(p+temp.length(), end+1)));
			}		
		}
		//��δ�ҵ����ж��Ƿ�Ϊ�������ַ���
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
			//�������ı����������ַ��м����˺�
			else
			{
				String newOrigin = "";
				//��Ա�������˺�
				for (int i = 0; i < origin.length();)
				{
					//latex����
					if(origin.charAt(i) == '\\')
					{
						newOrigin += findLstring(origin, i);
						i += findLstring(origin, i).length();
						//			System.out.println(newOrigin);
					}
					//��ͨ�ַ�
					else
					{
						newOrigin += origin.charAt(i);
						i++;
					}
					//�������һλ�ģ����ӳ˺�
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

	//�ҵ�origin�ַ�������beginλ�ÿ�ͷ�������������,��δ�ҵ��򷵻�ԭ�ַ���
	private String findOp(String origin, int begin) {
		// TODO Auto-generated method stub
		int end = begin+1;
		while(!op.containsKey(origin.substring(begin, end)) && end < origin.length())
			end++;
		if(!op.containsKey(origin.substring(begin, end)))
			return origin;
		return origin.substring(begin, end);
	}

	//�����ַ���Ϊһ��������Ԫ�أ�������
	private boolean isSingle(String origin) {
		// TODO Auto-generated method stub
		if(origin.length() == 1 || isOp(origin) || isNum(origin) || isVar(origin))
			return true;
		return false;
	}

	//��Ϊlatex��ʽ������������
	private boolean isVar(String origin) {
		// TODO Auto-generated method stub
		if(letter.containsKey(origin))
		{
			return true;
		}
		else
			return false;
	}

	//�ж��ַ����Ƿ�Ϊ����
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

	//��Ϊ������������е�Ԫ�أ�������
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

	//�ҵ����ȼ���͵���ͨ���������������λ��
	private int findHeadSingleOp(String origin) {
		// TODO Auto-generated method stub
		origin = origin.replace("(", "{");
		origin = origin.replace(")", "}");
		origin = origin.replace("[", "{");
		origin = origin.replace("]", "}");
		int pos = -1;
		int rank = 0;
		boolean found = false;
		//û�����ŵ����
		if(origin.indexOf("{") == -1)
		{
			//Ѱ�����ȼ���С���±���С�����������
			for(Map.Entry<String, Priority> m: op.entrySet())
			{
				String temp = m.getKey();
				Priority ptemp = m.getValue();
				if(origin.contains(temp))
				{
					//				System.out.println("���ͳһ����: ");
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
		//�����ŵ����
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


	//Ԥ������ʽ�ַ�������Ϊ����ʽ
	public String preProcess(String formula) {
		// TODO Auto-generated method stub
		formula = formula.replace(" ", "");
		//Ϊ'_'��'^'��Ĺ���Ԫ����������
		//formula = AddBracket(formula, "_");
//		System.out.println(formula);
		//formula = AddBracket(formula, "^");

		//�����ʽתΪ����
		String temp = formula;
		//���ҿ�����Ҫ���򻯵������
		while(temp.contains("\\"))
		{
			int p = temp.indexOf("\\");
			//����λ���Ƿ�Ϊlatex�������
			String op = findOp(temp, p);
			//�����ǣ������÷�������һ��
			if(op.length() == temp.length())
			{
				temp = temp.substring(p+1);
				continue;
			}
			//���ǣ������Ƿ���Ҫ����
			if(needSwap(op))
			{
				Slice slice = new Slice(p,p+op.length());
				//����������ַ����洢���ñ���
				String swapString = "";
					
				System.out.println("op����һ��: " + temp.charAt(p+op.length()));
				
				switch(temp.charAt(p+op.length()))
				{
				//�ҵ��±�Ϊ_{}������

				case '_': 
					slice = findString(temp, "{", "}", true);
					//������ʹ��±�Ĳ��ֻ�����ȥ��'_'����
					swapString = temp.substring(slice.getStart()+1, slice.getEnd()) + op;
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					break;
				
				case '[': 
					slice = findString(temp, "[", "]", true);			
					//����������Ų��ֻ���
					swapString = temp.substring(slice.getStart(), slice.getEnd()) + op;
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					break;
					
				case '{':	
					slice = findString(temp, "{", "}", true);			
					//����������Ų��ֻ���
					swapString = temp.substring(slice.getStart(), slice.getEnd()) + op;
					//		System.out.println("Ҫ��ɣ� " + swapString);
					formula = formula.replace(op + temp.substring(slice.getStart(), slice.getEnd()), swapString);
					//		System.out.println("����ˣ� " + formula);
					break;
				default:
					break;
				}
			}
			temp = temp.substring(p+op.length());
		}

		/*
		//����\LINK���
		if(formula.indexOf('^') != -1)
		{
			temp = formula;
			while(temp.indexOf('^') != -1)
			{
				//�ҵ�Ҫ�滻���ַ�����
				String tmp = temp.substring(findString(temp, "^{", "}").getStart()-2,findString(temp, "^{", "}").getEnd()+1);
				//Ϊ��ĩβ����\LINK���
				formula.replace(tmp, tmp + "\\LINK");
				temp = temp.substring(1);
			}
		}
		 */
		//		System.out.println("formula: " + formula);
//		System.out.println("Ԥ����׶Σ�" + formula);
		return formula;
	}

	//Ϊkey�����Ԫ����������
	private String AddBracket(String formula, String key) {
		// TODO Auto-generated method stub
		if(formula.indexOf(key) != -1)
		{
			//tmp��key��ͷ
			String tmp = formula.substring(formula.indexOf(key));
			while(tmp.indexOf(key) != -1)
			{
				tmp = tmp.substring(tmp.indexOf(key));
				//������������
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
	//��������ַ����Ƿ�Ϊ��Ҫ�����������
	private boolean needSwap(String string) {
		// TODO Auto-generated method stub
		//	System.out.println("�Ƿ���Ҫ������ " + string);
		if(needSwapOp.contains(string))
		{
			//		System.out.println("��");
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
		//		System.out.println(range.getStart() + "��" + range.getEnd());
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
