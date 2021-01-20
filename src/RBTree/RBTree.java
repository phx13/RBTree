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
	private RBNode getParent(RBNode node) {
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

	// 插入
	public void insert(K key, V value) {
		RBNode node = new RBNode();
		node.setKey(key);
		node.setValue(value);
		node.setColor(RED);
	}

	private void insert(RBNode node) {
		RBNode parent = null;
		RBNode root = this.root;

		while (root != null) {
			parent = root;

			// 比较node.key和root.key
			// 如果相等说明key存在，更新value值
			// 如果cmp>0说明node>root，node在root右侧。cmp<0反之。
			Integer cmp = node.key.compareTo(root.key);
			if (cmp == 0) {
				root.setValue(node.value);
				return;
			} else if (cmp > 0) {
				root = root.right;
			} else {
				root = root.left;
			}
		}

		// 设置node.parent为parent
		node.parent = root;
		if (parent != null) {
			// 比较node.key和parent.key，确定node在parent的左或右
			Integer cmp = node.key.compareTo(parent.key);
			if (cmp > 0) {
				parent.right = node;
			} else {
				parent.left = node;
			}
		} else {
			this.root = node;
			this.root.parent = null;
		}

		// 插入之后可能破坏RBTree平衡，需要制衡
		insertFixup(node);
	}

	// 情景1：空树-将根节点设置为黑色
	// 情景2：key已存在-不需要处理
	// 情景3：父节点是黑色-不需要处理
	// 情景4：父节点是红色
	// 4.1：U存在且为红色-P和U改为黑色，PP改为红色，设置当前节点为PP节点
	// 4.2：U不存在或为黑色，且P是PP的左子节点
	// 4.2.1：插入到P的左子节点（LL双红）-P改黑色，PP改红色，以PP为节点右旋
	// 4.2.2：插入到P的右子节点（LR双红）-以P为节点左旋，设置当前节点为P，重复4.2.1
	// 4.3：U不存在或为黑色，且P是PP的右子节点
	// 4.3.1：插入到P的右子节点（RR双红）-P改黑色，PP改红色，以PP为节点左旋
	// 4.3.2：插入到P的左子节点（RL双红）-以P为节点右旋，设置当前节点为P，重复4.3.1
	private void insertFixup(RBNode node) {
		// 情景1：空树-将根节点设置为黑色
		this.root.setColor(BLACK);

		RBNode parent = getParent(node);
		RBNode pParent = getParent(parent);

		// 情景4：父节点是红色
		if (parent != null && isRed(parent)) {
			RBNode uncle = null;
			// 如果父节点是左节点
			if (parent == pParent.left) {
				uncle = pParent.right;
				// 4.1：U存在且为红色-P和U改为黑色，PP改为红色，设置当前节点为PP节点
				if (uncle != null && isRed(uncle)) {
					parent.setColor(BLACK);
					uncle.setColor(BLACK);
					pParent.setColor(RED);
					insertFixup(pParent);
					return;
				}
				// 4.2：U不存在或为黑色，且P是PP的左子节点
				if (uncle == null || isBlack(uncle)) {
					// 4.2.1：插入到P的左子节点（LL双红）-P改黑色，PP改红色，以PP为节点右旋
					if (node == parent.left) {
						parent.setColor(BLACK);
						pParent.setColor(RED);
						rightRotate(pParent);
						return;
					}
					// 4.2.2：插入到P的右子节点（LR双红）-以P为节点左旋，设置当前节点为P，重复4.2.1
					if (node == parent.right) {
						leftRotate(parent);
						insertFixup(parent);
						return;
					}
				}
			} else {
				uncle = pParent.left;
				// 4.1：U存在且为红色-P和U改为黑色，PP改为红色，设置当前节点为PP节点
				if (uncle != null && isRed(uncle)) {
					parent.setColor(BLACK);
					uncle.setColor(BLACK);
					pParent.setColor(RED);
					insertFixup(pParent);
					return;
				}
				// 4.3：U不存在或为黑色，且P是PP的右子节点
				if (uncle == null || isBlack(uncle)) {
					// 4.3.1：插入到P的右子节点（RR双红）-P改黑色，PP改红色，以PP为节点左旋
					if (node == parent.right) {
						parent.setColor(BLACK);
						pParent.setColor(RED);
						leftRotate(pParent);
						return;
					}
					// 4.3.2：插入到P的左子节点（RL双红）-以P为节点右旋，设置当前节点为P，重复4.3.1
					if (node == parent.left) {
						rightRotate(parent);
						insertFixup(parent);
						return;
					}
				}
			}
		}
	}

	// 左旋
	// p
	// |
	// x
	// / \
	// lx y
	// / \
	// ly ry
	private void leftRotate(RBNode x) {
		RBNode y = x.right;

		// 1。当ly不为null时，将x的右子节点设置成ly，将ly的父节点设置成x
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}

		// 2。当p不为null时，将y的父节点设置为p，将p的子树设置为y
		if (x.parent != null) {
			y.parent = x.parent;
			if (x.parent.left == x) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		}
		// 否则x为根节点，需要更新y为root
		else {
			this.root = y;
			this.root.parent = null;
		}

		// 3。将x的父节点设置为y，将y的左子节点设置为x
		x.parent = y;
		y.left = x;
	}

	// 右旋
	// p
	// |
	// x
	// / \
	// y rx
	// / \
	// ly ry
	private void rightRotate(RBNode x) {
		RBNode y = x.right;

		// 1。当ry不为null时，将x的左子节点设置成ry，将ry的父节点设置成x
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}

		// 2。当p不为null时，将y的父节点设置为p，将p的子树设置为y
		if (x.parent != null) {
			y.parent = x.parent;
			if (x.parent.left == x) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		}
		// 否则x为根节点，需要更新y为root
		else {
			this.root = y;
			this.root.parent = null;
		}

		// 3。将x的父节点设置为y，将y的右子节点设置为x
		x.parent = y;
		y.right = x;
	}

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
