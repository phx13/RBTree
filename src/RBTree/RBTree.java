package RBTree;
//RBTree，一个红黑树的类

//1.创建RBTree，定义颜色
//2.创建RENode
//3.定义辅助方法
//	getParentNode(node)获取父亲节点
//	isRed(node)是否红色
//	setRed(node)设置红色
//	isBlack(node)是否黑色
//	setBlack(node)设置黑色
//

public class RBTree<K extends Comparable<K>, V> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	// 根节点
	private RBNode root;

	// 获取当前节点的父节点
	private RBNode getParentNode(RBNode node) {
		if (node != null) {
			return node.parent;
		}
		return null;
	}

	// 节点是否为红色
	private boolean isRed(RBNode node) {
		if (node != null) {
			return node.color == RED;
		}
		return false;
	}

	// 设置节点为红色
	private void setRed(RBNode node) {
		if (node != null) {
			node.color = RED;
		}
	}

	// 节点是否为黑色
	private boolean isBlack(RBNode node) {
		if (node != null) {
			return node.color == BLACK;
		}
		return false;
	}

	// 设置节点为黑色
	private void setBlack(RBNode node) {
		if (node != null) {
			node.color = BLACK;
		}
	}

	// 中序打印二叉树
	public void inorderPrint() {
		inorderPrint(this.root);
	}

	private void inorderPrint(RBNode node) {
		if (node != null) {
			inorderPrint(node.left);
			System.out.println("key:" + node.key + " value:" + node.value);
			inorderPrint(node.right);
		}
	}

	// 左旋
	//     4
	//    / \
	//   2   7
	//  / \ / \
	// 1  3 6  9
	// 1.

	static class RBNode<K extends Comparable<K>, V> {
		private RBNode parent;
		private RBNode left;
		private RBNode right;
		private boolean color;
		private K key;
		private V value;

		public RBNode() {
			super();
		}

		public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
			super();
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.color = color;
			this.key = key;
			this.value = value;
		}

		public RBNode getParent() {
			return parent;
		}

		public void setParent(RBNode parent) {
			this.parent = parent;
		}

		public RBNode getLeft() {
			return left;
		}

		public void setLeft(RBNode left) {
			this.left = left;
		}

		public RBNode getRight() {
			return right;
		}

		public void setRight(RBNode right) {
			this.right = right;
		}

		public boolean isColor() {
			return color;
		}

		public void setColor(boolean color) {
			this.color = color;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

	}
}
