/**
 * 
 */
package formulaCompare;


//import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
//import java.util.Vector;

/**
 * @author pedfcba
 * 访问数据库进行表达式的检索和匹配
 *
 */
public class FormulaAccess {

	private static Connection conn;
	private String data_source;
	private final String code_table = "code_formula";
	private final String index_table = "index_formula";
	private final String paper_table = "paper";
	private final String new_code_table = " (FORMULA_ID AUTOINCREMENT PRIMARY KEY, PAPER_ID INT, F_CONTENT VARCHAR(255) NOT NULL, F_CODE VARCHAR(255) NOT NULL, F_FIRST_LEVEL VARCHAR(255) NOT NULL, F_SECOND_LEVEL VARCHAR(255) NOT NULL)";
	private final String new_index_table = " (FORMULA_ID AUTOINCREMENT PRIMARY KEY, PAPER_ID INT, F_CONTENT VARCHAR(255) NOT NULL, F_FIRST_LEVEL VARCHAR(255) NOT NULL, F_SECOND_LEVEL VARCHAR(255) NOT NULL)";
	Set<String> namelist;

	public FormulaAccess(String source)
	{
		this.data_source = source;
		namelist = new HashSet<String>();
	}

	//创建新表，格式为“对应类型表名+后缀”
	private void CreateTable(String suffix, String type) throws SQLException
	{
		String table = "";
		String newtable = "";
		String tablename = "";
		if(type == "Code")
		{
			table = code_table;
			newtable = new_code_table;
			tablename = table + "_" + suffix;
		}
		else if(type == "Index")
		{
			table = index_table;
			newtable = new_index_table;
			tablename = table;
		}
		if(!namelist.contains(tablename))
		{
			Statement sqlstate = accessDb();
			String sql = "create table " + tablename + newtable;
			namelist.add(tablename);
			//	System.out.println(sql);
			sqlstate.execute(sql);
			//	System.out.println("新建了 " + tablename + " 表");
			sqlstate.close();
			conn.close();
		}	
	}

	//获取数据库中的所有表名，并返回
	private Set<String> getTableName() throws SQLException
	{
		Statement sqlstate = accessDb();
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet rs = meta.getTables(null, null, "%", null);
		Set<String> namelist = new HashSet<String>();
		while(rs.next())
			namelist.add(rs.getString(3));
		rs.close();
		sqlstate.close();
		//conn.close();
		return namelist;
	}

	//将文件信息导入数据库的文件表
	private int insertPaperDB(String tablename, String filepath) throws SQLException {
		// TODO Auto-generated method stub

		Statement sqlstate = accessDb();
		String sql = "select PAPER_ID from " + tablename + " where PAPER_NAME = '" + filepath.substring(filepath.lastIndexOf("\\")+1, filepath.lastIndexOf(".")) + 
		"' and PAPER = '" + filepath + "'";
		//System.out.println(sql);
		ResultSet rs = sqlstate.executeQuery(sql);
		int paperid = -1;
		if(rs.next() == true)
		{
			paperid = rs.getInt("PAPER_ID");
			if(paperid != -1)
				return paperid;
		}
		sql = "insert into " + tablename + 
		"(PAPER_NAME,PAPER) values('" 
		+ filepath.substring(filepath.lastIndexOf("\\")+1, filepath.lastIndexOf(".")) + 
		"','" + filepath + "')";
		//System.out.println(sql);
		sqlstate.execute(sql);
		sql = "select PAPER_ID from " + tablename + 
		" where PAPER_NAME = '" + filepath.substring(filepath.lastIndexOf("\\")+1, filepath.lastIndexOf(".")) + 
		"' and PAPER = '" + filepath + "'";
		//System.out.println(sql);
		rs = sqlstate.executeQuery(sql);
		if(rs.next() == true)
		{
			paperid = rs.getInt("PAPER_ID");
		}
		rs.close();
		sqlstate.close();
		//conn.close();
		return paperid;
	}

	//分析表达式，并导入到数据库中
	public boolean insertCodeDB(String formula, String tablename, int paperid, String type) throws SQLException
	{			
		boolean success = false;
		//构造表达式树
		//ni对象读取算法支持的所有LaTeX格式运算符，并生成表达式中的所有结点
		//		System.out.println("当前表达式：" + formula);
		if(formula.contains("\\begin{displaymath}"))
		{
			String temp = formula.substring("\\begin{displaymath}".length(), formula.lastIndexOf("\\"));
			formula = temp;
			formula = formula.replace(" ", "");
		}
			
		NodeInit ni = new NodeInit();
		MathNode mn = ni.iniTree(ni.preProcess(formula));
		mn.treeini();
		//若为结构码形式，对公式树做进一步处理
		if(type.equals("Code"))
		{
			//归一化表达式树
			PreTree pt = new PreTree();
			//将两棵树转换为中间形式
			//结构归一化后再进行变量归一化
			mn = pt.processTree(mn);
			mn = pt.varChange(mn);
			//得到层数与结构码
			mn.treeini();
		}
		String fcode = mn.regcode;
		String firstlevel = mn.data;
		String secondlevel = getIndex(mn,2);

		String sql = "";
		String inserttarget = "";
		//也向主数据库导入信息
		String mainsql = "";
		if(type.equals("Code"))
		{
			CreateTable(fcode, "Code");
			inserttarget = tablename + "_" + fcode;
			sql = "insert into " + inserttarget + 
			"(F_CONTENT,F_CODE,F_FIRST_LEVEL,F_SECOND_LEVEL,PAPER_ID) values('" 
			+ formula + "','" + fcode + "','" + firstlevel
			+ "','" + secondlevel + "'," + paperid + ")";
			mainsql = "insert into " + tablename + 
			"(F_CONTENT,F_CODE,F_FIRST_LEVEL,F_SECOND_LEVEL,PAPER_ID) values('" 
			+ formula + "','" + fcode + "','" + firstlevel
			+ "','" + secondlevel + "'," + paperid + ")";
		}
		else if(type.equals("Index"))
		{
			//CreateTable(firstlevel, "Index");
			mainsql = "insert into " + tablename + 
			"(F_CONTENT,F_FIRST_LEVEL,F_SECOND_LEVEL,PAPER_ID) values('" 
			+ formula + "','" + firstlevel
			+ "','" + secondlevel + "'," + paperid + ")";
		}
		//	System.out.println(sql);
		Statement sqlstate = accessDb();
		//		System.out.println(sql);

		//检查是否在数据库中存在该表达式
		String query_have = "select F_CONTENT from " + tablename + " where F_CONTENT = '" + formula + "'";
		//System.out.println(sql);
		ResultSet rs = sqlstate.executeQuery(query_have);
		String content = "";
		if(rs.next() == true)
		{
			content = rs.getString("F_CONTENT");
			//存在的不再插入
			if(content.length() != 0)
				success = false;
			rs.close();
		}
		else 
		{ 
			rs.close();
			if(type.equals("Code"))
				sqlstate.execute(sql);
			sqlstate.execute(mainsql);			
			success = true;
		}
		sqlstate.close();
		//添加后处理时间增加
		//conn.close();
		return success;
	}

	/**
	 * @param args
	 */
	public Statement accessDb()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("未找到类: " + e);
		}

		try
		{
			if(conn == null || conn.isClosed())
				conn = DriverManager.getConnection(data_source, "", "");
			//rs = sql.executeQuery(sqlname);
			return conn.createStatement();
		}
		catch (SQLException e)
		{
			System.out.println("数据库错误:" + e);
		}
		return null;

	}

	private void ReadFormulaFile(String table, String filepath, String type) throws IOException, SQLException {
		// TODO Auto-generated method stub
		//accessDb();
		namelist = getTableName();
		FileIO file = new FileIO();
		file.ReadStringStream(filepath);
		String temp;
		int paperid = insertPaperDB(paper_table, filepath);
		//	System.out.println(paperid);
		int count = 0;
		while((temp = file.getLine()) != null)
		{
			//		System.out.println(count);
			if(insertCodeDB(temp, table, paperid, type) == true)
				count++;
		}
		System.out.println("本次共插入 " + count + " 条表达式数据");
		namelist = getTableName();
		//注释掉后减速速度加快
		//conn.close();
		file.Close();
	}



	private long Find(String formula, String findtype) throws SQLException {
		// TODO Auto-generated method stub		
		//构造表达式树
		NodeInit ni = new NodeInit();
		MathNode mn = ni.iniTree(ni.preProcess(formula));
		mn.treeini();
		String query = "";

		if(findtype == "Code")
		{
			//归一化表达式树
			PreTree pt = new PreTree();
			//将两棵树转换为中间形式
			//结构归一化后再进行变量归一化
			mn = pt.processTree(mn);
			mn = pt.varChange(mn);
			//得到层数与结构码
			mn.treeini();
			String targettable = code_table + "_" + mn.regcode;
			if(namelist.contains(targettable))
			{
				query = "select F_CONTENT from " + targettable + 
				" where F_FIRST_LEVEL = '" + getIndex(mn, 1) + 
				"' and F_SECOND_LEVEL = '" + getIndex(mn, 2) + "'";
			}
			else 
			{
				query = "select F_CONTENT from " + code_table + 
				" where F_FIRST_LEVEL = '" + getIndex(mn, 1) + 
				"' and F_SECOND_LEVEL = '" + getIndex(mn, 2) + "'";
			}
		}
		else if(findtype == "Index")
		{
			String targettable = index_table + "_" + getIndex(mn, 1);
			if(namelist.contains(targettable))
			{
				query = "select F_CONTENT from " + targettable + 
				" where F_FIRST_LEVEL = '" + getIndex(mn, 1) + 
				"' and F_SECOND_LEVEL = '" + getIndex(mn, 2) + "'";	
			}
			else 
			{
				query = "select F_CONTENT from " + index_table + 
				" where F_FIRST_LEVEL = '" + getIndex(mn, 1) + 
				"' and F_SECOND_LEVEL = '" + getIndex(mn, 2) + "'";
			}
		}
		Statement sql = accessDb();
		long count = 0;
		//	System.out.println(query);
		try {
			ResultSet rs = sql.executeQuery(query);
			int target_line = rs.findColumn("F_CONTENT");
			while(rs.next())
			{
				String output = rs.getString(target_line);
				System.out.println(output);
				count++;
			}
			rs.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("找到 " + count + " 个与输入匹配的结果");
		return count;
	}

	private String getIndex(MathNode root, int i) {
		// TODO Auto-generated method stub
		String index = getIndexChar(root, i);
		if(index.contains(","))
			return index.replace(index.substring(0, index.lastIndexOf(",")+1), 
					"("+index.substring(0, index.lastIndexOf(","))+")");
		else 
			return index;
	}

	private String getIndexChar(MathNode root, int i)
	{
		int level = root.level;
		String index = "";
		if(i == 1)
		{
			if(root.type == Type.OPS || root.type == Type.OPU)
				index += root.data;
			else 
				index += root.type;
		}
		else if(i > 1)
		{
			if(root.LNode != null)
			{
				MathNode LNode = root.LNode;
				index += getIndex(LNode, i-1) + ",";
			}
			if(root.RNode != null)
			{
				MathNode RNode = root.RNode;
				index += getIndex(RNode, i-1) + ",";
			}
		}
		return index;
	}

	//处理指定路径的文件，并按照输入的存储类型将提取好的数学表达式插入到数据库
	public void InsertFormula(String table, String filepath, String type) throws IOException, SQLException
	{
		PreMath pm = new PreMath();
		//处理该文件，提取所有可处理的数学表达式，并保存到一个以tmp结尾的同名文件
		pm.process(filepath);
		
		//PreFormula pf = new PreFormula();
		//提取表达式列表
		//List<String> formula = new LinkedList<String>();
		//formula = pf.rfile(pm.getOutputpath());

		//将列表插入数据库
		FormulaAccess facode = new FormulaAccess("jdbc:odbc:formula");
		facode.ReadFormulaFile(table, pm.getOutputpath(), type);
		
	}

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		String keyx = "( ( \\lambda + ( \\Psi * W ) ) ^ \\phi ) \\approx \\Upsilon";
		String key = "( ( Q + \\phi ) * \\tau ) \\approx Z";
		long begintime;
		long endtime;
		double costtime;

		FormulaAccess facode = new FormulaAccess("jdbc:odbc:formula");

		begintime = System.currentTimeMillis();
		facode.InsertFormula("code_formula", "D:\\generated.txt", "Code");
		//facode.ReadFormulaFile("code_formula", "D:\\generated.txt", "Code");
		endtime = System.currentTimeMillis();
		costtime = (endtime-begintime)/1000.0;
		System.out.println("结构码+两层索引方法插入数据库用时：" + costtime + " 秒");


		costtime = 0;
		for(int countindex = 0; countindex < 10; countindex++)
		{
			begintime = System.nanoTime();
			facode.Find(key, "Code");
			endtime = System.nanoTime();
			costtime += (endtime-begintime)/1000000.0;
		}
		
		System.out.println("两层索引方法查询数据库用时：" + costtime/10 + " 毫秒");

		FormulaAccess faindex = new FormulaAccess("jdbc:odbc:formula");;
		begintime = System.currentTimeMillis();
		//faindex.ReadFormulaFile("index_formula", "D:\\generated.txt", "Index");
		endtime = System.currentTimeMillis();
		costtime = (endtime-begintime)/1000.0;
		System.out.println("两层索引方法插入数据库用时：" + costtime + " 秒");

		costtime = 0;
		for(int countindex = 0; countindex < 10; countindex++)
		{
			begintime = System.nanoTime();
			faindex.Find(key, "Index");
			endtime = System.nanoTime();
			costtime += (endtime-begintime)/1000000.0;
		}
		System.out.println("两层索引方法查询数据库用时：" + costtime/10 + " 毫秒");



	}
}

