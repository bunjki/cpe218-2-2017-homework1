import java.util.Scanner;
import java.util.Stack;

import javax.swing.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;

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
		//String data = args[0];  //รับค่าใน cmd
		String data = "251-*32*+";
		//System.out.print("Input:");
		//Scanner input = new Scanner(System.in);
		//String Data = input.nextLine();
		//String data = Data;
		for(int i = 0; i < data.length(); i++){

			stack.add(data.charAt(i));

		}

		Tree = new Node(stack.pop());
		System.out.print("Output : ");
		infix(Tree);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.createAndShowGUI();
			}
		});

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
		System.out.print(" = " + calculate(Tree));

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


	class Node {
		Node nodeRight;
		Node nodeLeft;
		Character inData; /// "251-*32+"

		Node(char m) {
			inData = m;
		}

		public String toString(){
			return inData.toString();
		}

	}




	class GUI extends JPanel
		implements TreeSelectionListener {
	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;
	private static boolean DEBUG = false;

	//Optionally play with line styles.  Possible values are
	//"Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";

	//Optionally set the look and feel.
	private static boolean useSystemLookAndFeel = false;





	public GUI() {
		super(new GridLayout(1,0));

		//Create the nodes.
		DefaultMutableTreeNode top =
				new DefaultMutableTreeNode(Homework1.Tree); //Homework1 to Node Tree***
		createNodes(top,Homework1.Tree); // create Node

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}

		//Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		//Create the HTML viewing pane.
		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		initHelp();
		JScrollPane htmlView = new JScrollPane(htmlPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));


	ImageIcon Icon = createImageIcon("middle.gif"); //// MAKE ICON NODE****
		if (Icon != null) {
			DefaultTreeCellRenderer renderer =
					new DefaultTreeCellRenderer();

			renderer.setOpenIcon(Icon);
			renderer.setClosedIcon(Icon);

			tree.setCellRenderer(renderer);
		}

		//Add the split pane to this panel.
		add(splitPane);
	}


	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		htmlPane.setText(node.toString());
	}

	private class BookInfo {
		public String bookName;
		public URL bookURL;

		public BookInfo(String book, String filename) {
			bookName = book;
			bookURL = getClass().getResource(filename);
			if (bookURL == null) {
				System.err.println("Couldn't find file: "
						+ filename);
			}
		}

		public String toString() {
			return bookName;
		}
	}

	private void initHelp() {
		String s = "TreeDemoHelp.html";
		helpURL = getClass().getResource(s);
		if (helpURL == null) {
			System.err.println("Couldn't open help file: " + s);
		} else if (DEBUG) {
			System.out.println("Help URL is " + helpURL);
		}

		displayURL(helpURL);
	}

	private void displayURL(URL url) {
		try {
			if (url != null) {
				htmlPane.setPage(url);
			} else { //null url
				htmlPane.setText("File Not Found");
				if (DEBUG) {
					System.out.println("Attempted to display a null URL.");
				}
			}
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + url);
		}
	}

	private void createNodes(DefaultMutableTreeNode top, Node Tree) { // create Node Tree****

        if (Tree.nodeLeft != null) {
            DefaultMutableTreeNode left = new DefaultMutableTreeNode(Tree.nodeLeft);
            top.add(left);
            createNodes(left, Tree.nodeLeft);
        }

        if (Tree.nodeRight != null) {
            DefaultMutableTreeNode right = new DefaultMutableTreeNode(Tree.nodeRight);
            top.add(right);
            createNodes(right, Tree.nodeRight);
        }

    }


	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	public static void createAndShowGUI() {
		if (useSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.println("Couldn't use system look and feel.");
			}
		}

		//Create and set up the window.
		JFrame frame = new JFrame("TreeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add content to the window.
		frame.add(new GUI());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}


}


