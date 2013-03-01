package formulaCompare;
/*
 * 试验用主程序
 * */




import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainProgram {

	//private String priorityfilepath = "D:\\develop\\antlrTest\\priority.txt";
	
	public void process(String originfilepath, String compairfilepath) throws IOException
	{
		//预处理源文件，提取数学表达式,将源文件中的所有表达式输出到tmp文件
		PreMath oripm = new PreMath();
		PreMath compm = new PreMath();
		oripm.process(originfilepath);
		compm.process(originfilepath);
		
		//预处理tmp文件中的数学表达式，为每个表达式生成独立的文件
		PreFormula origin = new PreFormula();
		PreFormula compair = new PreFormula();
		origin.process(oripm.getOutputpath());
		compair.process(compm.getOutputpath());
		
		//ni对象读取算法支持的所有LaTeX格式运算符，并生成表达式中的所有结点
		NodeInit ni = new NodeInit();
		//pt对象用于比较公式树，计算相似度
		PreTree pt = new PreTree();
		if(!origin.getOutputfilepath().isEmpty())
		{
			for(int i = 0; i < origin.getOutputfilepath().size(); i++)
			{
				MathNode oriNode = new MathNode();
				oriNode = ni.process(origin.getOutputfilepath().get(i));
				System.out.println();
				System.out.println("源表达式：");
				oriNode.texOut();
				System.out.println();
				for(int j = 0; j < compair.getOutputfilepath().size(); j++)
				{
					MathNode compNode = new MathNode();
					compNode = ni.process(compair.getOutputfilepath().get(j));
					System.out.println();
					System.out.println("目标表达式：");
					compNode.texOut();
					System.out.println();
					System.out.println("表达式文件" + origin.getOutputfilepath().get(i) + 
							"\n与公式文件" + compair.getOutputfilepath().get(j) + 
							"\n的相似度为：");
					System.out.println(pt.process(oriNode, compNode));
				}
			}
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MainProgram mp = new MainProgram();
		String filepath = "D:\\develop\\antlrTest\\src\\test.tex";
		String compairfilepath = "D:\\develop\\antlrTest\\src\\test.tex";
		mp.process(filepath, compairfilepath);
		
	}

}
