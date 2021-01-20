package RBTree;

import java.util.Scanner;

public class RBTreeTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		RBTree<String, Object> rbTree = new RBTree();
		while (true) {
			System.out.println("Input key:");
			String key = scanner.next();
			System.out.println();
			rbTree.insert(key, null);
			
		}
	}
}
