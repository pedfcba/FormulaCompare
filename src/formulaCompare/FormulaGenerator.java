package formulaCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

public class FormulaGenerator {
	private final int ArrayMaxSize = 200;
	private Vector<String> oplist = new Vector<String>(ArrayMaxSize);
	private Vector<String> charlist = new Vector<String>(ArrayMaxSize);
	private Vector<String> uniqueop = new Vector<String>(ArrayMaxSize);
	private String opAdress = "D:\\develop\\antlrTest\\priority_test.txt";
	private String charAdress = "D:\\develop\\antlrTest\\letter.txt";
	private static boolean haveroot = false;

	public FormulaGenerator()
	{
		try 
		{
			ReadElement(opAdress, oplist);
			ReadElement(charAdress, charlist);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Gene(String path, int count)
	{
		try
		{
			PrintWriter out = new PrintWriter(new File(path).getAbsoluteFile());
			String formula = "";
			for(int lines = 0; lines < count; lines++)
			{
				formula += "\\begin{displaymath}";
				Random rand = new Random();
				haveroot = true;
				String left = GeneElement();
				left += " ";
				haveroot = false;
				String op = GeneOp();
				while(haveroot != true)
					op = GeneOp();
				op += " ";
				String right = GeneElement();
				formula += left + op + right;
				formula += "\\end{displaymath}";
				if(formula.length() >= 25 && formula.length() <= 60)
					out.println(formula);
				else
					lines--;
				haveroot = false;
				formula = "";
			}
			out.close();
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	private String GeneOp() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int num = rand.nextInt(oplist.size());
		//Èô°üº¬¶¥²ãÔËËã·û£¬ÅÐ¶ÏÊÇ·ñÎ¨Ò»
		if(uniqueop.contains(oplist.get(num)))
		{
			if(haveroot == true)
				return GeneOp();
			else
				haveroot = true;
		}
		return oplist.get(num);
	}

	private String GeneElement() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int haveBracket = rand.nextInt(100);
		if(haveBracket >= 70)
		{
			//			System.out.println(haveBracket);
			//			System.out.println("À¨ºÅ");
			String bracket = "( ";
			bracket += GeneElement() + " ";
			bracket += GeneOp();
			bracket += " " + GeneElement();
			bracket += " )";
			//			System.out.println("×îÖÕ£º" + bracket);
			return bracket;
		}
		else
			return GeneString();
	}

	private String GeneString() {
		// TODO Auto-generated method stub
		String letter;
		Random rand = new Random();
		boolean lt = rand.nextBoolean();
		if(lt == true)
		{
			letter = charlist.get(rand.nextInt(charlist.size()));
		}
		else
		{
			char a = 'A';
			a = (char) (a + rand.nextInt(26));
			if(rand.nextBoolean())
			{
				letter = String.valueOf(rand.nextInt(100));
			}
			else
				letter = String.valueOf(a);
			//			System.out.println("Ëæ»ú×Ö·û: " + letter);
		}
		return letter;
	}

	private Vector<String> ReadElement(String Adress, Vector<String> list) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(Adress));
		String line;
		int count = 0;
		while((line = in.readLine()) != null)
		{
			if(line.equals(""))
				continue;
			String larray[] = line.split("\t");
			list.add(larray[0]); 
			if(larray[2].equals("1"))
			{
				uniqueop.add(larray[0]);
			}
			count++;
		}
		return list;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FormulaGenerator fg = new FormulaGenerator();
		fg.Gene("D:\\generated.txt", 10);
	}

}
