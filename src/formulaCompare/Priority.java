/*
my codes generated at 2012-10-23
Administrator
*/

package formulaCompare;


public class Priority {

	Mark mark;
	int rank;
	Type type;
	
	public void setMark(String mark)
	{
		if(mark.equals("LR"))
			this.mark = Mark.LR;
		if(mark.equals("UD"))
			this.mark = Mark.UD;
		if(mark.equals("RULD"))
			this.mark = Mark.RULD;
		if(mark.equals("SINGLE"))
			this.mark = Mark.SINGLE;
	}
	
	public void setType(String type)
	{
		if(type.equals("NUM"))
			this.type = Type.NUM;
		if(type.equals("VAR"))
			this.type = Type.VAR;
		if(type.equals("OPS"))
			this.type = Type.OPS;
		if(type.equals("OPU"))
			this.type = Type.OPU;
	}
	
	public boolean setRank(String rank)
	{
		if(rank.matches("\\d*"))
		{
			this.rank = Integer.parseInt(rank);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathNode mn  = new MathNode();

	}

}
