package formulaCompare;
/*
 * ������������
 * */




import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainProgram {

	//private String priorityfilepath = "D:\\develop\\antlrTest\\priority.txt";
	
	public void process(String originfilepath, String compairfilepath) throws IOException
	{
		//Ԥ����Դ�ļ�����ȡ��ѧ���ʽ,��Դ�ļ��е����б��ʽ�����tmp�ļ�
		PreMath oripm = new PreMath();
		PreMath compm = new PreMath();
		oripm.process(originfilepath);
		compm.process(originfilepath);
		
		//Ԥ����tmp�ļ��е���ѧ���ʽ��Ϊÿ�����ʽ���ɶ������ļ�
		PreFormula origin = new PreFormula();
		PreFormula compair = new PreFormula();
		origin.process(oripm.getOutputpath());
		compair.process(compm.getOutputpath());
		
		//ni�����ȡ�㷨֧�ֵ�����LaTeX��ʽ������������ɱ��ʽ�е����н��
		NodeInit ni = new NodeInit();
		//pt�������ڱȽϹ�ʽ�����������ƶ�
		PreTree pt = new PreTree();
		if(!origin.getOutputfilepath().isEmpty())
		{
			for(int i = 0; i < origin.getOutputfilepath().size(); i++)
			{
				MathNode oriNode = new MathNode();
				oriNode = ni.process(origin.getOutputfilepath().get(i));
				System.out.println();
				System.out.println("Դ���ʽ��");
				oriNode.texOut();
				System.out.println();
				for(int j = 0; j < compair.getOutputfilepath().size(); j++)
				{
					MathNode compNode = new MathNode();
					compNode = ni.process(compair.getOutputfilepath().get(j));
					System.out.println();
					System.out.println("Ŀ����ʽ��");
					compNode.texOut();
					System.out.println();
					System.out.println("���ʽ�ļ�" + origin.getOutputfilepath().get(i) + 
							"\n�빫ʽ�ļ�" + compair.getOutputfilepath().get(j) + 
							"\n�����ƶ�Ϊ��");
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
