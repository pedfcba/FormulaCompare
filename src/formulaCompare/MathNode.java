package formulaCompare;

import java.util.*;

//符号结合方式
enum Mark 
{
	LR,UD,RULD,SINGLE;
}

//符号类型
enum Type
{
	VAR,NUM,OPS,OPU;
}

//符号结点
public class MathNode 
{
	public MathNode()
	{
		//符号优先级
		this.rank = -1;
		this.mark = Mark.LR ;
		this.type = Type.VAR ;
		//结点数据
		this.data = "";
		LNode = null;
		RNode = null;
	}

	public MathNode(int rank,int level,Mark mark,Type type,String data,String regcode)
	{
		this.rank = rank;
		this.mark = mark;
		this.type = type;
		this.data = data;
		this.level = level;
		this.regcode = regcode;
		LNode = null;
		RNode = null;
	}

	public MathNode(String data)
	{
		//符号优先级
		this.rank = Integer.MAX_VALUE;
		this.mark = Mark.LR ;
		this.type = Type.VAR ;
		//结点数据
		this.data = data;
		LNode = null;
		RNode = null;
	}


	public MathNode(String data, Mark mark, Type type)
	{
		//符号优先级
		this.rank = Integer.MAX_VALUE;
		this.mark = mark ;
		this.type = type ;
		//结点数据
		this.data = data;
		this.regcode = "";
		this.level = 0;
		LNode = null;
		RNode = null;
	}

	public MathNode(MathNode mn)
	{
		this.rank = mn.rank;
		this.mark = mn.mark;
		this.type = mn.type;
		this.data = mn.data;
		this.level = mn.level;
		this.regcode = mn.regcode;
		this.LNode = mn.LNode;
		this.RNode = mn.RNode;
	}

	//rank: 优先级；level: 子树层数；mark：符号结合方式；
	//type：符号类型；data：结点数据；regcode：识别码
	int rank;
	int level;
	Mark mark;
	Type type;
	String data;
	String regcode;
	MathNode LNode;
	MathNode RNode;

	/*
	public MathNode iniLevel(MathNode tree)
	{
		int count = tree.countLevel(tree);
		System.out.println("count:");
		System.out.println(count);
		tree = tree.proLevel(count);
		return tree;
	}

	public MathNode proLevel(int count)
	{
		this.level = count;
		if (this.LNode != null)
			this.LNode = this.LNode.proLevel(--count);
		if (this.RNode != null)
			this.RNode = this.RNode.proLevel(--count);
		return this;
	}
	 */

	public void treeini()
	{
		this.iniLevel();
		this.iniCode();
	}
	
	private void iniCode()
	{
		if (this.level == 1)
			this.regcode = "1";
		if(this.level > 1)
		{
			if (this.LNode != null)
				this.LNode.iniCode();
			if (this.RNode != null)
				this.RNode.iniCode();
		//	System.out.println("" + this.level + this.LNode.regcode + this.RNode.regcode);
			this.regcode = "" + this.LNode.regcode + this.level + this.RNode.regcode;
		}
	}
	
	private int iniLevel()
	{
		if (this.LNode == null && this.RNode == null)
		{
			this.level = 1;
			return 1;
		}
		int i = 0 ,j = 0;
		if (this.LNode != null)		
			i = this.LNode.iniLevel() + 1;		
		if(this.RNode != null)		
			j = this.RNode.iniLevel() + 1;		
		if(i > j)
		{
			this.level = i;
			return i;
		}
		else 
		{
			this.level = j;
			return j;
		}
	}
	
	
	
	public MathNode nswap(MathNode node)
	{
		MathNode tmp = new MathNode(node.LNode);
		node.LNode = node.RNode;
		node.RNode = tmp;
		return node;
	}
	public void pnode()
	{

		if (this.LNode != null)
			this.LNode.pnode();

		int i = this.level;
		while(i != 0)
		{
			System.out.print(">");
			i--;
		}

		System.out.print(this.data);
		System.out.println();
		System.out.println("regcode:");
		System.out.println(this.regcode);
		System.out.print(" rank: ");
		System.out.print(this.rank);
		System.out.print(" type: ");
		System.out.print(this.type);
		System.out.print(" mark: ");
		System.out.print(this.mark);
		System.out.println();
		 
		//System.out.println();

		if (this.RNode != null)
			this.RNode.pnode();
	}

	public int nodeCount()
	{
		int count = 0;
		if (this != null)
		{
			if (this.LNode != null)
				count += this.LNode.nodeCount();
			if (this.RNode != null)
				count += this.RNode.nodeCount();
			count++;
		}
		return count;
	}


	public void texOut()
	{
		//System.out.print("[; ");
		if(this.mark == Mark.LR)
		{
			output("(");
			if(this.LNode != null)
				this.LNode.texOut();
			if(!this.data.equals("link"))
				output(" " + this.data + " ");
			if(this.RNode != null)
				this.RNode.texOut();
			output(")");
		}
		if(this.mark == Mark.UD)
		{
			output(" " + this.data + " ");
			output("\\limits_{ ");
			this.LNode.texOut();
			output("}");
			output("^{");
			this.RNode.texOut();
			output("}");
		}
		if(this.mark == Mark.RULD)
		{
			output(" " + this.data + " ");
			output("_{ ");
			this.LNode.texOut();
			output("}");
			output("^{ ");
			this.RNode.texOut();
			output("}");			
		}
		if(this.mark == Mark.SINGLE)
		{
			output(this.data);
		}
		//System.out.print(" ;]");
		//System.out.print(" ");
	}

	public void output(String text)
	{
		System.out.print(text);
	}

	public MathNode treeCopy()
	{
		MathNode ntree = new MathNode(this);
		if (this.LNode != null)
		{
			MathNode ntreel = new MathNode(this.LNode);
			ntree.LNode = ntreel.treeCopy();
		}
		if (this.RNode != null)
		{
			MathNode ntreer = new MathNode(this.RNode);
			ntree.RNode = ntreer.treeCopy();
		}
		return ntree;	
	}
	
	//按对应位置以b中的值替换等于a中的值的公式元素,返回替换好后的树
	//原树未动
	public MathNode replaceVar(List<String> a, List<String> b)
	{
		MathNode ntree = new MathNode(this);
		ntree = this.treeCopy();
	//	ntree.output("\n");
	//	ntree.texOut();
		String temp = "";
		List<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < a.size(); i++)
		{
			tmpList.add(i, "tmp" + i);
		}	
		
		ntree.changeVar(ntree, a, tmpList);	
		ntree.changeVar(ntree, tmpList, b);
		
	//	ntree.output("\nafter\n");
	//	ntree.texOut();
		
		return ntree;
	}
	
	//将列表a对应位置的值变成b中对应位置的
	public MathNode changeVar(MathNode node, List<String> a, List<String> b)
	{
		if (node.type == Type.VAR && a.contains(node.data))
		{
			int i = a.indexOf(node.data);
			node.data = b.get(i);
		}
		if (node.level > 1 && node.LNode != null)
			changeVar(node.LNode, a, b);
		if (node.level > 1 && node.RNode != null)
			changeVar(node.RNode, a, b);
		return node;
	}

	//为结点左右的变量名排序，保证其为升序顺序
	public void sortVar() {
		// TODO Auto-generated method stub
		if (this.level == 2 && this.type == Type.OPS)
		{
			if (this.LNode.data.compareTo(this.RNode.data) > 0)
				nswap(this);
		}
		if (this.level > 2)
		{
			this.LNode.sortVar();
			this.RNode.sortVar();
			//对OPS结点也要保证升序
			if (this.LNode.level == this.RNode.level && this.type == Type.OPS)
			{
				PreTree pt = new PreTree();
				pt.treeProc(this);
			}
		}
	}

	public static void main(String [] args)
	{
		MathNode mn = new MathNode(30,6,Mark.LR,Type.OPS,"+","");
		MathNode mnl = new MathNode(60,5,Mark.LR,Type.OPS,"\\times","");
		MathNode mnr = new MathNode(60,2,Mark.LR,Type.OPS,"\\times","");
		mn.LNode = mnl;
		mn.RNode = mnr;

		MathNode mnll = new MathNode(40,4,Mark.LR,Type.OPU,"link","");
		MathNode mnlr = new MathNode(40,4,Mark.LR,Type.OPU,"link","");
		mnl.LNode = mnll;
		mnl.RNode = mnlr;

		MathNode mnrl = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"a","");
		MathNode mnrr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"b","");
		mnr.LNode = mnrl;
		mnr.RNode = mnrr;

		MathNode mnlll = new MathNode(50,3,Mark.UD,Type.OPU,"\\sum","");
		MathNode mnllr = new MathNode(70,2,Mark.LR,Type.OPU,"^","");
		mnll.LNode = mnlll;
		mnll.RNode = mnllr;

		MathNode mnlrl = new MathNode(50,3,Mark.UD,Type.OPU,"\\sum","");
		MathNode mnlrr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"j","");
		mnlr.LNode = mnlrl;
		mnlr.RNode = mnlrr;

		MathNode mnlrll = new MathNode(20,2,Mark.LR,Type.OPS,"=","");
		MathNode mnlrlr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"n","");
		mnlrl.LNode = mnlrll;
		mnlrl.RNode = mnlrlr;

		MathNode mnlrlll = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"j","");
		MathNode mnlrllr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"1","");
		mnlrll.LNode = mnlrlll;
		mnlrll.RNode = mnlrllr;

		MathNode mnllll = new MathNode(20,2,Mark.LR,Type.OPS,"=","");
		MathNode mnlllr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"n","");
		mnlll.LNode = mnllll;
		mnlll.RNode = mnlllr;

		MathNode mnllrl = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"i","");
		MathNode mnllrr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"2","");
		mnllr.LNode = mnllrl;
		mnllr.RNode = mnllrr;

		MathNode mnlllll = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"i","");
		MathNode mnllllr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"1","");
		mnllll.LNode = mnlllll;
		mnllll.RNode = mnllllr;

		

		
		/*
		System.out.println("before: ");
		mn.pnode();
		//System.out.print("hash: ");
		//System.out.print(mn.RNode.hashCode());
		//System.out.println();
	//	mn = mn.treeProc(mn);
		System.out.println();
		System.out.println("after: ");
		mn.pnode();
		//System.out.print("hash: ");
		//System.out.print(mn.RNode.hashCode());
		//System.out.println();
		System.out.println();
		 */


		//另一个公式树
		MathNode xmn = new MathNode(30,6,Mark.LR,Type.OPS,"+","");
		MathNode xmnl = new MathNode(60,5,Mark.LR,Type.OPS,"\\times","");
		MathNode xmnr = new MathNode(60,2,Mark.LR,Type.OPS,"\\times","");
		xmn.LNode = xmnl;
		xmn.RNode = xmnr;

		MathNode xmnll = new MathNode(40,4,Mark.LR,Type.OPU,"link","");
		MathNode xmnlr = new MathNode(40,4,Mark.LR,Type.OPU,"link","");
		xmnl.LNode = xmnll;
		xmnl.RNode = xmnlr;

		MathNode xmnrl = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"a","");
		MathNode xmnrr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"b","");
		xmnr.LNode = xmnrl;
		xmnr.RNode = xmnrr;

		MathNode xmnlll = new MathNode(50,3,Mark.UD,Type.OPU,"\\sum","");
		MathNode xmnllr = new MathNode(70,2,Mark.LR,Type.OPU,"^","");
		xmnll.LNode = xmnlll;
		xmnll.RNode = xmnllr;

		MathNode xmnlrl = new MathNode(50,3,Mark.UD,Type.OPU,"\\sum","");
		MathNode xmnlrr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"j","");
		xmnlr.LNode = xmnlrl;
		xmnlr.RNode = xmnlrr;

		MathNode xmnlrll = new MathNode(20,2,Mark.LR,Type.OPS,"=","");
		MathNode xmnlrlr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"n","");
		xmnlrl.LNode = xmnlrll;
		xmnlrl.RNode = xmnlrlr;

		MathNode xmnlrlll = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"j","");
		MathNode xmnlrllr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"1","");
		xmnlrll.LNode = xmnlrlll;
		xmnlrll.RNode = xmnlrllr;

		MathNode xmnllll = new MathNode(20,2,Mark.LR,Type.OPS,"=","");
		MathNode xmnlllr = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"n","");
		xmnlll.LNode = xmnllll;
		xmnlll.RNode = xmnlllr;

		MathNode xmnllrl = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"i","");
		MathNode xmnllrr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"2","");
		xmnllr.LNode = xmnllrl;
		xmnllr.RNode = xmnllrr;

		MathNode xmnlllll = new MathNode(-1,1,Mark.SINGLE,Type.VAR,"i","");
		MathNode xmnllllr = new MathNode(-1,1,Mark.SINGLE,Type.NUM,"1","");
		xmnllll.LNode = xmnlllll;
		xmnllll.RNode = xmnllllr;


/*
		MathNode a = new MathNode("+",Mark.LR,Type.OPS);
		MathNode al = new MathNode("\\times",Mark.LR,Type.OPS);
		MathNode ar = new MathNode("\\times",Mark.LR,Type.OPS);
		a.pnode();
		a.output("\nafter: \n");
		a.LNode = al;
		a.RNode = ar;
		a.iniLevel();
		a.pnode();
		a.output("\nbefore: \n");
		a.pnode();
		a.output("\nafter: \n");
		a.iniLevel();
		a.iniCode();
		a.pnode();
		*/

		List<String> a = new ArrayList<String>();

		List<String> b = new ArrayList<String>();
		a = Arrays.asList("a b i".split(" "));
		b = Arrays.asList("b i a".split(" "));


		mn.output("\n以b、i、a的次序替换a、b、i");
		mn.texOut();
		mn = mn.replaceVar(a, b);
		mn.output("\n");
		mn.texOut();
		
		System.out.println("OPS".equals(mn.type));
		
		//	nodeComp(xmn,mn);
	}
}
