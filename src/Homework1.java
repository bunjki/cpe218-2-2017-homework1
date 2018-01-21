import java.util.Stack;

public class Homework1 {

	public static String Screen="";
	public static Node Tree;
	public static Stack<Character> stack = new Stack<Character>();

	public static void main(String[] args) {
		// Begin of arguments input sample
		/*if (args.length > 0) {
			String input = args[0];
			if (input.equalsIgnoreCase("251-*32*+")) {
				System.out.println("(2*(5-1))+(3*2)=14");
			}
		}*/
		// End of arguments input sample
		
		// TODO: Implement your project here
		String data = args[0];
		//String data = "251-*32*+";
		for(int i = 0; i < data.length(); i++){

			stack.add(data.charAt(i));

		}

		Tree = new Node(stack.pop());
		infix(Tree);

	}

	public static class Node
	{
		Node nodeRight;
		Node nodeLeft;
		Character inData; /// "251-*32+"

		Node(char m)
		{
			inData = m;
		}
	}

	public static void infix(Node n)
	{
//        if(isOperate(n)){
//            infix(n.nodeLeft);
//            System.out.print(n.inData);
//            infix(n.nodeRight);
//        }else{
//            System.out.print(n.inData);
//        }
		inorder(n);
		System.out.print(new StringBuffer(Screen).reverse().toString());
		System.out.print("=" + calculate(Tree));

	}

	public static void inorder(Node n)
	{

		if(isOperate(n))
		{
			if(n!=Tree)Screen+=")";

			n.nodeRight = new Node(stack.pop());
			inorder(n.nodeRight);
			Screen+=n.inData;
			n.nodeLeft = new Node(stack.pop());
			inorder(n.nodeLeft);

			if(n!=Tree) Screen+="(";
		}else{

			Screen+=n.inData;

		}
	}

	public static int calculate(Node n)
	{
		if(isOperate(n)){
			switch(n.inData)
			{
				case '+':
					return calculate(n.nodeLeft) + calculate(n.nodeRight);
				case '-':
					return calculate(n.nodeLeft) - calculate(n.nodeRight);
				case '*':
					return calculate(n.nodeLeft) * calculate(n.nodeRight);
				case '/':
					return calculate(n.nodeLeft) / calculate(n.nodeRight);
			}
		}else{
			return Integer.valueOf(n.inData.toString());
		}
		return 0;
	}



	public static boolean isOperate(Node n)
	{
		return ( n.inData == '+' || n.inData == '-' || n.inData == '*' || n.inData == '/' );
	}

}


