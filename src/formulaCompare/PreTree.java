package formulaCompare;

import java.util.*;

//�������ù�ʽ�������ƶ�
public class PreTree {

	//Set<String> vset = new HashSet<String>();
	//Map<String , Integer> vmap = new HashMap<String , Integer>();

	public PreTree(){}

	public PreTree(MathNode tree)
	{

	}


	//���ô˺���ʵ���������ƶȵıȽ�
	//�Ա������������㲢�������ƶ�
	//��ʽ��sim(A,B) = 2*(len(A',B'))/|A|+|B|
	public double process(MathNode a, MathNode b)
	{
		//���������������
		MathNode tmpa = new MathNode();
		tmpa = a.treeCopy();
		MathNode tmpb = new MathNode();
		tmpb = b.treeCopy();

		//����������δ����ǰ�����ƶ�
		double sim = 0;
		tmpa.treeini();
		tmpb.treeini();
		//tmpa.pnode();
		//	System.out.println("��������ƶ�: ");
		//	System.out.println(nodeSim(tmpa,tmpb));
		//	sim += 2*(nodeSim(tmpa,tmpb))*0.05/(tmpa.nodeCount()+tmpb.nodeCount());
		//	System.out.println(sim);

		/*
		System.out.println("������ͬ���: ");
		System.out.println(nodeSim(a,b));
		System.out.println("���A: ");
		System.out.println(a.nodeCount());
		System.out.println("���B: ");
		System.out.println(b.nodeCount());
		System.out.println("��������ƶ�: ");
		System.out.println(sim);
		 */

		PreTree pt = new PreTree();
		//��������ת��Ϊ�м���ʽ
		tmpa = pt.processTree(tmpa);
		tmpb = pt.processTree(tmpb);
		tmpa.treeini();
		tmpb.treeini();


		//�ṹ��һ�����ٽ��б�����һ��
		tmpa = pt.varChange(tmpa);
		//��Ŀ�깫ʽ�ı��ν��в���
		MathNode tmp = tmpb.treeCopy();

		tmp = pt.varChange(tmp);
		//	System.out.println("������ͬ���: ");
		//	System.out.println(nodeSim(tmpa,tmpb));
		/*
		System.out.println("\n��һ�����Դ��ʽ: ");
		tmpa.texOut();
		System.out.println();
		System.out.println("\n��һ�����Ŀ�깫ʽ: ");
		tmp.texOut();
		System.out.println();
		System.out.println();
		*/
		//	sim += 2*(nodeSim(tmpa,tmpb))*0.95/(tmpa.nodeCount()+tmpb.nodeCount());

		//�����������������������ƶ�
		sim = sim(tmpa,tmp);

		//��������needPro�����Ľ�㣬��ʹ��fidMax�������ƶ�
		if (needPro(tmpa,tmpb) && sim < 1)
		{
			MathNode ntmp = tmpb.treeCopy();
			ntmp = pt.varChange(ntmp);
			System.out.println("\n����������������OPS��㡣�Ա����������к��滻");
			sim = findMax(tmpa,ntmp);
		}

		/*
		System.out.println("������ͬ���: ");
		System.out.println(nodeSim(a,b));
		System.out.println("���A: ");
		System.out.println(a.nodeCount());
		System.out.println("���B: ");
		System.out.println(b.nodeCount());
		System.out.println("�������ƶ�: ");
		System.out.println(sim);
		 */

		return sim;
	}

	//���ƶȹ�ʽ
	private double sim(MathNode a, MathNode b)
	{
		return 2.0*(nodeSim(a,b))/(a.nodeCount()+b.nodeCount());
	}


	//��������a,b�ṹ�롢
	//��Ҷ�ӽ����ͬ��
	//�Ұ������������ĸ߶���ͬ��OPS���ʱ������true
	private boolean needPro(MathNode a, MathNode b) {
		// TODO Auto-generated method stub
		if ( a.regcode.equals(b.regcode) && nodeEqual(a,b) && haveOps(b))
			return true;
		else 
			return false;
	}

	//��������b�а���OPS������Ǹ��������������߶���ͬʱ������true
	private boolean haveOps(MathNode node) {
		// TODO Auto-generated method stub
		boolean  have = false;
		if (node.level >1)
		{
			if (node.type == Type.OPS && node.LNode.level == node.RNode.level)
				return true;
			if (have == false)
				have = haveOps(node.LNode);
			if (have == false)
				have = haveOps(node.RNode);
		}

		return have;
	}

	//����������a,b��Ҷ�ӽ�㶼��ͬ������true
	private boolean nodeEqual(MathNode a, MathNode b) {
		// TODO Auto-generated method stub
		boolean equal = true;
		if (a.level > 1 && b.level > 1)
		{
			if (!a.data.equals(b.data))
				return false;
			if (equal == true)
				equal = nodeEqual(a.LNode, b.LNode);
			if (equal == true)
				equal = nodeEqual(a.RNode, b.RNode);
		}
		return equal;
	}

	//���������ҵ�b�����������߶���ͬ��OPS��㣬��¼����µ����б�����
	//�Լ�¼�е��������н����滻
	//ÿ���滻�������ƶȣ������������ƶ�
	private double findMax(MathNode a, MathNode b) {
		// TODO Auto-generated method stub
		double sim = sim(a,b);

		//	System.out.println(vset);
		if (sim == 1)
			return sim;
		else
		{
			Set<String> vset = new HashSet<String>();
			vset = balanceOps(b);
			sim = swapVar(a, b, vset);
			return sim;
		}
	}

	//����������set�е�Ԫ�����н�����������뽻��
	//���������������˳���滻��tree����Ӧ������
	//����tree���������ƶȣ����շ�������
	//sListΪԭ��ʽ���а����ı��������У�tarListΪ�仯��Ҫ�滻��Ϊ�ı���������
	private double swapVar(MathNode stree, MathNode tree, Set<String> set) {
		// TODO Auto-generated method stub
		//��������ƶ�
		double sim = sim(stree, tree);
		double tsim = sim;

		if (sim == 1)
			return sim;

		//Ϊ������ҵı��������򣬱�֤��Ϊ����˳��
		stree.sortVar();

		List<String> sList = new ArrayList<String>();
		List<String> tarList = new ArrayList<String>();

		//��һ���б�洢�������������ı�����
		sList = getList(set);
		tarList.addAll(sList);

		//		System.out.println(sList);

		//��allList�������п��ܵ�����˳��
		List<List<String>> allList = new ArrayList<List<String>>();
		allList = permList(allList, sList, 0, sList.size() - 1);


		//		System.out.println(allList);

		//�������б�����ϲ���˳����н������������ƶȲ������������ƶ�
		for (int i = 0; i < allList.size(); i++)
		{
			if (sim == 1)
				return sim;

			//��ȫ��������ȡ���µ�����
			tarList = allList.get(i);

			//������Ӧλ�õı������źý�������Ҷ��
			MathNode ntree = tree.replaceVar(sList, tarList);
			ntree.sortVar();

			//����������Դ��ʽ�������ƶ�
			tsim = sim(stree, ntree);

			/*
			System.out.println("���ڵĹ�ʽA��");
			stree.texOut();
			System.out.println("\n���ڵĹ�ʽB��");
			ntree.texOut();
			System.out.println();
			System.out.println("�������ƶȣ�");
			System.out.println(tsim);
			 */		
			if (tsim > sim)
				sim = tsim;
		}
		return sim;
	}

	//����sList�е�Ԫ�أ�����һ�����к��list
	private List<List<String>> permList(List<List<String>> rt, List<String> sList, int start, int end) {
		// TODO Auto-generated method stub
		if (start == end)
		{
			List<String> x = new ArrayList<String>();
			x.addAll(sList);
			//		System.out.println("here");
			//		System.out.println(sList);
			//		System.out.println("we go");
			//		System.out.println(rt);
			if (!rt.contains(x));
			rt.add(x);
		}
		else 
		{
			for (int i = start; i <= end; i++)
			{
				String temp = sList.get(start);
				sList.set(start, sList.get(i));
				sList.set(i, temp);

				permList(rt, sList, start + 1, end);

				temp = sList.get(start);
				sList.set(start, sList.get(i));
				sList.set(i, temp);
			}
			//		System.out.println("then:");
			//		System.out.println(rt);
		}

		return rt;
	}

	//�õ�set�е�����Ԫ�أ�����List
	private List<String> getList(Set<String> set) {
		// TODO Auto-generated method stub
		List<String> tmp = new ArrayList<String>();
		tmp.addAll(set);

		System.out.println("\n��Ҫ���������뽻���ı���Ϊ��");
		System.out.println(tmp);

		return tmp;
	}

	//��������ͳ�����������߶���ȵ�OPS����µ����б�����������
	private Set<String> balanceOps(MathNode b) {
		// TODO Auto-generated method stub
		Set<String> vset = new HashSet<String>();

		if(b.level == 1)
			return vset;
		else
		{
			if (b.type == Type.OPS && b.LNode.level == b.RNode.level)
			{
				vset.addAll(findVar(b, vset));
			}
			if (b.LNode != null)
				vset.addAll(balanceOps(b.LNode));
			if (b.RNode != null)
				vset.addAll(balanceOps(b.RNode));
		}

		return vset;
	}

	//���������ҵ�node����µ�����VAR�������֣���ӵ�vset�в�����
	private Set<String> findVar(MathNode node,Set<String> set) {
		// TODO Auto-generated method stub

		if(node.level == 1 && node.type == Type.VAR)
			set.add(node.data);
		if(node.LNode != null)
			findVar(node.LNode, set);
		if(node.RNode != null)
			findVar(node.RNode, set);
		return set;
	}

	//���ô˺���ʵ�ֶԹ�ʽ����Ԥ����
	public MathNode processTree(MathNode tree)
	{
		MathNode ntree = new MathNode();
		ntree = tree.treeCopy();
		PreTree pt = new PreTree();
		ntree.treeini();
		//����������OPS�����
		ntree = pt.sortContiOps(tree);
		//�ṹ��һ��
		ntree = pt.treeProc(ntree);
		return ntree;
	}

	//��һ�����ṹ��һ����Ԥ������ѧ��ʽ��������ṹת��Ϊ�м���ʽ
	public MathNode treeProc(MathNode tree)
	{
		if(tree.level > 1 && tree.type == Type.OPS)
		{			
			//	System.out.print(tree.LNode.data + " �� " +tree.RNode.data);
			//	System.out.println(" �Ա�");
			if(tree.LNode != null && tree.RNode != null)
			{

				if(tree.LNode.level > tree.RNode.level)
				{
					tree = tree.nswap(tree);
					//			System.out.print(tree.LNode.data + " �� " +tree.RNode.data);
					//			System.out.println(" ������");
				}
				else if (tree.LNode.level == tree.RNode.level)
				{
					//�������������������ͬ��������
					if(compTree(tree.LNode, tree.RNode))
					{
						tree.nswap(tree);
					}						

				}
			}
			else 
				if (tree.RNode != null)
					tree = tree.nswap(tree);

		}
		if(tree.LNode != null)
			treeProc(tree.LNode);
		if(tree.RNode != null)
			treeProc(tree.RNode);
		return tree;
	}

	//Ԥ����������OPS������������������հ��ֵ�˳������
/*	private MathNode sortSameOps(MathNode tree) {
		// TODO Auto-generated method stub
		//��������ɽ����������������ȶ���Щ������µ���������ʹ�䰴�ֵ�˳������

		List<String> sList = new LinkedList<String>();
		
		if (tree.level > 1 && tree.LNode != null && tree.RNode != null)
		{
			if(tree.type == Type.OPS && tree.data.equals(tree.RNode.data))
			{
				MathNode temp = new MathNode(tree);
				if (tree.LNode.mark == Mark.SINGLE)
					return tree;
				else 
					if (tree.RNode.data.equals(tree.data) && tree.type == Type.OPS)
						return tree;
			}
		}
		return tree;

	}*/

	//�ṹ��һ������������Ա�����������Ӧλ��Ԫ�صĴ�С����Ϊtrue���ʾ��Ҫ��������
	public boolean compTree(MathNode a, MathNode b)
	{
		boolean big = false;

		//�ж�������a.data����b.data��a.level����b.level
		if (a.data.compareTo(b.data) > 0 || a.level > b.level)		
			big = true;		
		if (big == false && a.LNode != null && b.LNode != null)		
			big = compTree(a.LNode , b.LNode);
		if (big == false && a.RNode != null && b.RNode != null)			
			big = compTree(a.RNode, b.RNode);		
		return big;
	}

	//ͳ�ƹ�ʽ���еı���������������-��š��Է��ص�vmap
	public Map<String , Integer> varProc(MathNode mnode, Set<String> set, Map<String , Integer> map)
	{
		if(mnode.level == 1 && mnode.type == Type.VAR)
		{
			set.add(mnode.data);
			if(!map.containsKey(mnode.data))
				map.put("a" + set.size(), set.size());
		}
		if(mnode.LNode != null)
			varProc(mnode.LNode, set, map);
		if(mnode.RNode != null)
			varProc(mnode.RNode, set, map);
		//this.vset.clear();
		/*
		System.out.println("Ԥ����: ");

		System.out.println(this.vset);
		System.out.println("ʱset������: ");
		System.out.println("MAP������: ");
		System.out.println(this.vmap);
		System.out.println("����������: ");
		 */

		return map;
	}

	//ͳһ������
	public MathNode nodeProc(MathNode node, Set<String> set, Map<String, Integer> map, int count)
	{

		if(node.level == 1 && node.type == Type.VAR && !map.containsKey(node.data))
		{
			set.add(node.data);
			if (set.size() > count )
				set.remove(node.data);
			else
				if (set.size() == count)
				{
					for(Map.Entry<String, Integer> m: map.entrySet())
					{

						if(m.getValue() == count)
						{
							//				System.out.println("���ͳһ����: ");
							//				System.out.println(node.data);
							node.data = m.getKey();
							//				System.out.println("����: ");
							//				System.out.println(node.data);
						}
					}
				}
		}
		if(node.LNode != null)
			nodeProc(node.LNode, set, map, count);

		if(node.RNode != null)
			nodeProc(node.RNode, set, map, count);
		return node;
	}

	//��һ����������һ��������ʽ��תΪ�м���ʽ��ͳһ������
	public MathNode varChange(MathNode mnode)
	{
		//����һ����Ľṹȷ������
		//	mnode = this.treeProc(mnode);

		//	System.out.println("\n��ʽĿǰ������: ");
		//	mnode.texOut();

		Set<String> vset = new HashSet<String>();
		Map<String , Integer> vmap = new HashMap<String , Integer>();
		vmap = this.varProc(mnode, vset, vmap);
		//this.vset.clear();

		//	System.out.println("MAP����������: ");
		//	System.out.print(this.vmap);

		//	System.out.println("OVER ");
		//MathNode tmp = new MathNode(mnode);


		//		System.out.println("OVER ");
		//		System.out.println(vset);
		vset.clear();
		for(int i = 1; i <= vmap.size(); i++)
		{

			//		System.out.println("------------ѭ��:--------------- ");
			//		System.out.println(i);
			//		System.out.println("------------����:--------------- ");
			//		System.out.println(this.vmap.size());
			this.nodeProc(mnode, vset, vmap, i);
			/*
			if(mnode.level == 1 && mnode.type == Type.VAR)
			{
				this.vset.add(mnode.data);
				if (this.vset.size() > i)
					this.vset.remove(mnode.data);
				else
					if (this.vset.size() == i)
					{
						String key = this.vmap.keySet().iterator().next();
						while(this.vmap.get(key) != i)
							key = this.vmap.keySet().iterator().next();
						if(this.vmap.get(key) == i)
							mnode.data = key;
					}
			}
			if(mnode.LNode != null)
				varProc(mnode.LNode);
			if(mnode.RNode != null)
				varProc(mnode.RNode);
			 */
		}


		//	mnode.texOut();
		return mnode;
	}

	//�ҵ�������ͬ�Ľ��
	public MathNode findlevel(MathNode a, MathNode b)
	{					
		Queue<MathNode> qu = new LinkedList<MathNode>();

		while (a.level > b.level)
		{
			if(a.LNode.level >= b.level)
				qu.offer(a.LNode);
			if(a.RNode.level >= b.level)
				qu.offer(a.RNode);
			while(qu.peek() != null)
			{
				a = qu.poll();
				if (a.level == b.level && a.data.equals(b.data))
					break;
			}
		}

		return a;
	}


	//������ʽͳ�Ʋ�����������������ͬ����������
	//len(A,B)
	public int nodeSim(MathNode a, MathNode b)
	{
		int count = 0;
		if(a.data.equals(b.data))
			count = 1;
		else
			return 0;
		if (a.LNode != null && b.LNode != null)
			count += nodeSim(a.LNode, b.LNode);
		if (a.RNode != null && b.RNode != null)
		{
			count += nodeSim(a.RNode, b.RNode);
		}
		return count;
		/*				
				if(a.level > b.level && a.level >= 2)
				{
					a = findlevel(a,b);
				}
				else if(a.level < b.level && b.level >= 2)
				{
					b = findlevel(b,a);
				}
		 */
		/*
				if(a.level > b.level)
				{
					count += nodeSim(a.LNode, b);
				}
				else if (b.level > a.level)
				{
					count += nodeSim(a, b.LNode);
				}
				/*
				System.out.println("��ͬ�ĵط�: ");
				a.pnode();
				b.pnode();
		 */
	}

	/*
	//������ʽͳ�Ʋ�����������������ͬ����������
	//len(A,B)
	public int nodeSim(MathNode a, MathNode b)
	{
		int count = 0;
		if (a != null && b != null)
			count = strcmp(a.regcode, b.regcode);

		System.out.println("count: ");
		System.out.println(count);
		return count;
	}

	 */

	//�Ա������ַ������������ǵ�������ִ����ȡ�����������ṹ���ƶ�
	public int strcmp(String a, String b)
	{
		int count = 0;
		if(a == "" || b == "")
			return -1;
		if (a.length() < b.length())
			return strcmp(b,a);
		else 
		{
			int index = 0;
			for(; index <= a.length() - b.length(); index++)
			{
				if (a.charAt(index) == b.charAt(0))
				{
					int tmp = 1;
					for (int j = index + 1; j < b.length(); j++)
					{
						if (a.indexOf(b.substring(index, j)) != -1)
							tmp++;
						else
							break;
					}
					if(tmp > count)
						count = tmp;
					index += count - 1;
				}
			}
			return count;
		}
	}

	private static MathNode sortContiOps(MathNode tree) {
		// TODO Auto-generated method stub

		List<MathNode> rootlist = new ArrayList<MathNode>();
		//		List<MathNode> leaflist = new ArrayList<MathNode>();

		if (tree.type == Type.OPS && tree.level > 2 && tree.LNode != null && tree.RNode != null){
			//����ͬ��OPS���Ŵ���rootlist�б���
			MathNode mn = new MathNode();
			mn = tree;
			MathNode tmpl = new MathNode();
			tmpl = mn.LNode;
			//����������
			rootlist.add(tmpl);
			//Ѱ����������ͬ���Ž��
			while(mn.RNode.data == tree.data)
			{
				System.out.println(mn.level);
				mn = mn.RNode;
				System.out.println(mn.level);
				MathNode temp = new MathNode();
				temp = mn.LNode;
				rootlist.add(temp);
			}
			//���һ����ͬ���ŵ��ҽ��
			MathNode temp = new MathNode();
			temp = mn.RNode;
			rootlist.add(temp);
		}
		if(rootlist.size() > 0)
		{
			MathNode mn = new MathNode();
			mn = tree;
			int total = rootlist.size();
			for(int i = 0; i < total-1; i++)
			{
				String data = rootlist.get(0).data;
				int index = 0;
				//�ҵ���С�����Ľ��
				for(int j = 0; j < rootlist.size(); j++)
				{
					if(data.compareTo(rootlist.get(j).data) > 0)
					{
						index = j;
						data = rootlist.get(j).data;
					}
				}
				//ȷ������
				mn.LNode = rootlist.get(index);
				tree = setChild(tree,mn,i);
				rootlist.remove(index);
				//�ƶ�����һ������,��total-2�������һ����ͬ�ķ��Ž�㣬����
				if(i < total-2)
					mn = mn.RNode;
			}
			mn.RNode = rootlist.get(0);
			tree = setChild(tree,mn,total-2);
			tree.treeini();
			rootlist.clear();
		}
		//	leaflist.clear();
		if (tree.LNode != null && tree.LNode.level > 2)
			tree.LNode = sortContiOps(tree.LNode);
		if (tree.RNode != null && tree.RNode.level > 2)
			tree.RNode = sortContiOps(tree.RNode);
		return tree;
	}

	private static MathNode setChild(MathNode tree, MathNode mn, int depth) {
		// TODO Auto-generated method stub
		if(depth > 0)
		{
			tree.RNode = setChild(tree.RNode, mn, depth-1);
		}
		else if(depth == 0)
		{
			tree = mn;
		}
		return tree;
	}


	public static void main(String [] args)
	{		
		/*
		PreTree pt = new PreTree();
		int count = pt.cmp("fdsfew","sa");
		System.out.println(count);
		 */


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

		/*
		//��һ����ʽ��
		MathNode xmn = new MathNode(30,6,Mark.LR,Type.OPS,"+","");
		MathNode xmnl = new MathNode(60,5,Mark.LR,Type.OPS,"\\times","");
		MathNode xmnr = new MathNode(60,2,Mark.LR,Type.OPS,"\\times","");
		xmn.LNode = xmnr;
		xmn.RNode = xmnl;

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
		 */

		/*
		System.out.println("before: ");
		xmn.pnode();
		//System.out.print("hash: ");
		//System.out.print(mn.RNode.hashCode());
		//System.out.println();



		xmn = pt.varChange(xmn);
		System.out.println();
		System.out.println("after: ");
		xmn.pnode();
		System.out.println();
		 */
		/*
		mn.output("mn�����νṹ�� \n");
		mn.pnode();
		mn.output("\n");
		mn.output("mn��Latex���ʽ�� \n");
		mn.texOut();
		mn.output("\n\n");
		mn.output("xmn��Latex���ʽ�� \n");
		xmn.texOut();
		mn.output("\n\n");
		 */


		MathNode mn = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		MathNode mnl = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		MathNode mnr = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		mn.LNode = mnl;
		mn.RNode = mnr;

		MathNode mnll = new MathNode(3,0,Mark.LR,Type.OPU,"link","");
		MathNode mnlr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		mnl.LNode = mnll;
		mnl.RNode = mnlr;


		MathNode mnrl = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		MathNode mnrr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		mnr.LNode = mnrl;
		mnr.RNode = mnrr;


		MathNode mnlll = new MathNode(3,0,Mark.UD,Type.OPU,"\\sum","");
		MathNode mnllr = new MathNode(7,0,Mark.LR,Type.OPS,"^","");
		mnll.LNode = mnlll;
		mnll.RNode = mnllr;

		MathNode mnlrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode mnlrr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		mnlr.LNode = mnlrl;
		mnlr.RNode = mnlrr;


		MathNode mnlrrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"y","");
		MathNode mnlrrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"z","");
		mnlrr.LNode = mnlrrl;
		mnlrr.RNode = mnlrrr;

		MathNode mnrll = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode mnrlr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"y","");
		mnrl.LNode = mnrll;
		mnrl.RNode = mnrlr;

		MathNode mnrrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode mnrrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		mnrr.LNode = mnrrl;
		mnrr.RNode = mnrrr;



		MathNode mnllll = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.NUM,"10","");
		MathNode mnlllr = new MathNode(1,0,Mark.LR,Type.OPS,"=","");
		mnlll.LNode = mnllll;
		mnlll.RNode = mnlllr;

		MathNode mnllrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"a","");
		MathNode mnllrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"i","");
		mnllr.LNode = mnllrl;
		mnllr.RNode = mnllrr;

		MathNode mnlllrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"i","");
		MathNode mnlllrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.NUM,"1","");
		mnlllr.LNode = mnlllrl;
		mnlllr.RNode = mnlllrr;


		mn.treeini();
		//	mn.pnode();
		System.out.println("Դ��ʽ��TeX��ʾ: ");
		mn.texOut();

		MathNode xmn = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		MathNode xmnl = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		MathNode xmnr = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		xmn.LNode = xmnl;
		xmn.RNode = xmnr;

		MathNode xmnll = new MathNode(3,0,Mark.LR,Type.OPU,"link","");
		MathNode xmnlr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		xmnl.LNode = xmnll;
		xmnl.RNode = xmnlr;


		MathNode xmnrl = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		MathNode xmnrr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		xmnr.LNode = xmnrl;
		xmnr.RNode = xmnrr;


		MathNode xmnlll = new MathNode(3,0,Mark.UD,Type.OPU,"\\sum","");
		MathNode xmnllr = new MathNode(7,0,Mark.LR,Type.OPS,"^","");
		xmnll.LNode = xmnlll;
		xmnll.RNode = xmnllr;

		MathNode xmnlrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode xmnlrr = new MathNode(6,0,Mark.LR,Type.OPS,"\\times","");
		xmnlr.LNode = xmnlrl;
		xmnlr.RNode = xmnlrr;

		MathNode xmnrll = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode xmnrlr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"d","");
		xmnrl.LNode = xmnrll;
		xmnrl.RNode = xmnrlr;

		MathNode xmnrrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		MathNode xmnrrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"x","");
		xmnrr.LNode = xmnrrl;
		xmnrr.RNode = xmnrrr;



		MathNode xmnllll = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.NUM,"10","");
		MathNode xmnlllr = new MathNode(1,0,Mark.LR,Type.OPS,"=","");
		xmnlll.LNode = xmnllll;
		xmnlll.RNode = xmnlllr;



		MathNode xmnllrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"a","");
		MathNode xmnllrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"i","");
		xmnllr.LNode = xmnllrl;
		xmnllr.RNode = xmnllrr;




		MathNode xmnlrrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"d","");
		MathNode xmnlrrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"z","");
		xmnlrr.LNode = xmnlrrl;
		xmnlrr.RNode = xmnlrrr;


		MathNode xmnlllrl = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.VAR,"i","");
		MathNode xmnlllrr = new MathNode(Integer.MAX_VALUE,0,Mark.SINGLE,Type.NUM,"2","");
		xmnlllr.LNode = xmnlllrl;
		xmnlllr.RNode = xmnlllrr;

		xmn.treeini();
		System.out.println("\n");
		System.out.println("Ŀ�깫ʽ��TeX��ʾ: ");
		xmn.texOut();
		System.out.println("\n");


		PreTree pt = new PreTree();


		System.out.println("��������ʽ�����ƶ�: ");
		System.out.println(pt.process(mn, xmn));




		//	System.out.println(pt.haveOps(xmn));

		//	mn.pnode();
		/*
		PreTree pt = new PreTree();
		//System.out.println(pt.nodeComp(mn,xmn));
		mn.texOut();
		System.out.println();
		xmn.texOut();
		System.out.println("�����������ƶ�: ");
		System.out.println(pt.nodeComp(mn, xmn));
		/*
		mn.output("\n���������mn�����νṹ��\n");
		pt.process(mn).pnode();
		mn.output("\n");


		mn.output("���������mn��Latex���ʽ��\n");
		pt.process(mn).texOut();


		mn.output("\n���������xmn��Latex���ʽ��\n");
		pt.process(xmn).texOut();

		String a = "\\c";
		String b = "\\m";


		mn.output("\n");
		System.out.println(a.compareTo(b));

		 */


	}
}
