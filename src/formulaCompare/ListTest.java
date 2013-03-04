
package formulaCompare;

import java.util.ArrayList;
import java.util.List;

//�������ɽ������Ž������⴦���������й�����������
//�������������ͬ�ķ��ţ�����Щ���ŵ���������ͳһ����
//������������������ͬ�򰴷�������

public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<MathNode> rootlist = new ArrayList<MathNode>();
		List<MathNode> leaflist = new ArrayList<MathNode>();

		MathNode mn = new MathNode(6,0,Mark.LR,Type.OPS,"*","");
		MathNode mnl = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		MathNode mnr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"c","");
		mn.LNode = mnl;
		mn.RNode = mnr;

		MathNode mnll = new MathNode(6,0,Mark.SINGLE,Type.VAR,"a","");
		MathNode mnlr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"b","");
		mnl.LNode = mnll;
		mnl.RNode = mnlr;

	/*	MathNode mnrl = new MathNode(6,0,Mark.SINGLE,Type.VAR,"c","");
		MathNode mnrr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"b","");
		mnr.LNode = mnrl;
		mnr.RNode = mnrr;*/

		mn.treeini();
		mn = mn.nswap(mn);
		mn.texOut();
		System.out.println("be");
		//	mn.pnode();
		System.out.println("before:");
		mn.texOut();

		sortSameOps(mn);

/*		rootlist.add(mnll);
		rootlist.add(mnl);
		rootlist.add(mn);
		for (int i = 0; i < rootlist.size(); i++)
		{
			System.out.println("\nNode in List: ");
			System.out.println(rootlist.get(i).data);
			System.out.println("\ntree is: ");
			System.out.println();
			rootlist.get(i).texOut();
			System.out.println();
			//		rootlist.remove(i);
			System.out.println("size: ");
			System.out.println(rootlist.size());
			System.out.println("i:");
			System.out.println(i);
		}
		rootlist.remove(0);
		for (int i = 0; i < rootlist.size(); i++)
		{
			System.out.println("\nNode in Listx: ");
			System.out.println(rootlist.get(i).data);
			System.out.println("\ntree isx: ");
			System.out.println();
			rootlist.get(i).texOut();
			System.out.println("i:");
			System.out.println(i);
		}
		//		rootlist.set(1, mnl);
		//		rootlist.set(0, mnr);
		for (int i = 0; i < rootlist.size(); i++)
		{
			System.out.println("\nNode in changed List: ");
			System.out.println(rootlist.get(i).data);
			System.out.println("\ntree is: ");
			System.out.println();
			rootlist.get(i).texOut();
			rootlist.remove(i);
			System.out.println();
		}*/
		//	mn.LNode = rootlist.get(0);
		//	mn.RNode = rootlist.get(1);

		System.out.println("\n finally tree is: ");
		mn.texOut();

	}

	private static MathNode sortSameOps(MathNode tree) {
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
			tree.LNode = sortSameOps(tree.LNode);
		if (tree.RNode != null && tree.RNode.level > 2)
			tree.RNode = sortSameOps(tree.RNode);
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

	private static List<MathNode> permLeaf(List<MathNode> leaflist) {
		// TODO Auto-generated method stub
		List<MathNode> slist = new ArrayList<MathNode>();
		MathNode temp = new MathNode();
		int tmp = 0;

		if (leaflist.size() > 0)
		{
			while(leaflist.size() != 0)
			{
				temp = leaflist.get(tmp);
				for (int j = 0; j < leaflist.size(); j++)
				{
					//ÿ��ȡ��Сֵ
					//////////////////////////////////////////////////////////
					if (compLeaf(temp, leaflist.get(j)))
						tmp = j;
				}
				leaflist.remove(tmp);
				slist.add(temp);
			}


		}
		return slist;
	}

	public static boolean compLeaf(MathNode a, MathNode b)
	{
		boolean big = false;

		//�ж�������a.data����b.data
		if (a.data.compareTo(b.data) > 0)		
			big = true;		
		if (big == false && a.LNode != null && b.LNode != null)		
			big = compLeaf(a.LNode , b.LNode);
		if (big == false && a.RNode != null && b.RNode != null)			
			big = compLeaf(a.RNode, b.RNode);		
		return big;
	}
}
