//import org.antlr.runtime.*;
package formulaCompare;

import java.util.regex.*;
import java.util.*;
import java.io.*;

public class Test {
	public boolean strcmp(String input, String com)
	{
		if (input.contains(com))
			return true;
		else 
			return false;
			
	}
	public static void main(String[] args) throws Exception {
		System.out.println("input file path");
		//path:�ļ�����·��
		String path = "D:\\develop\\texdata\\���ֱ�.tex";
		//��ʼ������
		PreMath prm = new PreMath();
		//��Ԥ������ַ���д����ʱ�ļ�$path.tmp
		prm.process(path);
		
		System.out.println("input file path over");
		

		
		

		MathNode mn = new MathNode("=");
		MathNode mnl = new MathNode("x");
		MathNode mnr = new MathNode("1");
		mn.LNode = mnl;
		mn.RNode = mnr;
		mn.pnode();
		Test ts = new Test();
		ts.strcmp("asd","d");
	/*	
	//	Scanner scanner = new Scanner(ipts);
		// create a CharStream that reads from standard input
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		// create a lexer that feeds off of input CharStream
		antlrtLexer lexer = new antlrtLexer(input);
		// create a buffer of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// create a parser that feeds off the tokens buffer
		antlrtParser parser = new antlrtParser(tokens);
		// begin parsing at rule rule
		parser.prog();
		*/
	}
	
}