
package formulaCompare;

import java.util.ArrayList;
import java.util.List;

//将连续可交换符号进行特殊处理，排序所有关联到的子树
public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<MathNode> rootlist = new ArrayList<MathNode>();
		List<MathNode> leaflist = new ArrayList<MathNode>();

		MathNode mn = new MathNode(6,0,Mark.LR,Type.OPS,"+","");
		MathNode mnl = new MathNode(4,0,Mark.LR,Type.OPS,"+","");
		MathNode mnr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"c","");
		mn.LNode = mnl;
		mn.RNode = mnr;


		MathNode mnll = new MathNode(6,0,Mark.SINGLE,Type.VAR,"a","");
		MathNode mnlr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"b","");
		mnl.LNode = mnll;
		mnl.RNode = mnlr;

		MathNode mnrl = new MathNode(6,0,Mark.SINGLE,Type.VAR,"c","");
		MathNode mnrr = new MathNode(4,0,Mark.SINGLE,Type.VAR,"b","");
		mnr.LNode = mnrl;
		mnr.RNode = mnrr;

		mn.treeini();
		//	mn.pnode();
		System.out.println("before:");
		mn.texOut();

		sortSameOps(mn);

		rootlist.add(mnll);
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
		}
		//	mn.LNode = rootlist.get(0);
		//	mn.RNode = rootlist.get(1);

		System.out.println("\n finally tree is: ");
		mn.texOut();

	}

	private static MathNode sortSameOps(MathNode tree) {
		// TODO Auto-generated method stub

		List<MathNode> rootlist = new ArrayList<MathNode>();
		List<MathNode> leaflist = new ArrayList<MathNode>();

		if (tree.level > 1 && tree.LNode != null && tree.RNode != null){
			//将相同的OPS符号存入rootlist列表中
			if (tree.type == Type.OPS && tree.data.equals(tree.RNode.data))
			{
				rootlist.add(tree);
				while ((rootlist.get(rootlist.size()).data).equals((rootlist.get(rootlist.size()).RNode.data)))
					rootlist.add(rootlist.get(rootlist.size()));
			}

			int size = rootlist.size();
			//将连续运算符的结点孩子存入leaflist
			for (int i = 0; i < size; i++)
			{
				leaflist.add(rootlist.get(i).LNode);
				if (i == size - 1)
					leaflist.add((rootlist.get(size - 1)).RNode);
			}

			//按字典顺序排列leaflist中的结点
			leaflist = permLeaf(leaflist);

			//整理
			for (int j = 0; j < size; j++)
			{
				rootlist.get(j).LNode = leaflist.get(j);
				if (j == size - 1)
					rootlist.get(j).RNode = leaflist.get(size);
			}



		}
		rootlist.clear();
		leaflist.clear();

		if (tree.LNode != null)
			sortSameOps(tree.LNode);
		if (tree.RNode != null)
			sortSameOps(tree.RNode);
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
					//每次取最小值
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

		//判定条件：a.data大于b.data
		if (a.data.compareTo(b.data) > 0)		
			big = true;		
		if (big == false && a.LNode != null && b.LNode != null)		
			big = compLeaf(a.LNode , b.LNode);
		if (big == false && a.RNode != null && b.RNode != null)			
			big = compLeaf(a.RNode, b.RNode);		
		return big;
	}
}
