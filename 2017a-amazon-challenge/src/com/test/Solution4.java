package com.test;

import java.util.ArrayList;
import java.util.List;

class Node{
	int key;
	int distanceFromRoot;
	Node left, right;
}

class BinarySearchTree{
	Node root;
	
	Node insert(Node node, int key, int distance)
	{
		if (node == null)
		{
			node = new Node();
			node.key = key;
			node.distanceFromRoot = distance;
			return node;
		}
		if (node.key == key)
		{
			return node;
		}
		else if (node.key > key)
		{
			node.left = insert(node.left, key, distance+1);
			return node;
		} else
		{
			node.right = insert(node.right, key, distance+1);
			return node;
		}
	}

	void inorder(Node node, List<Integer> list)
	{
		if (node == null)
		{
			return;
		}
		inorder(node.left, list);
		list.add(node.key);
		inorder(node.right, list);
	}
	
	void inorder2(Node node, boolean check, int start, int finish, List<Integer> list)
	{
		if (node == null)
		{
			return;
		}
		inorder2(node.left, check, start, finish, list);
		if (node.key == start){
			check = true;
		}
		list.add(node.key);
		inorder2(node.right, check, start, finish, list);
	}
	
	Node search(Node node, int key, List<Integer> list)
	{
		if (node == null)
		{
			return null;
		}
		if (node.key == key)
		{
			list.add(key);
			return node;
		}
		list.add(node.key);
		if (key < node.key)
		{			
			return search(node.left, key, list);
		} else
		{
			return search(node.right, key, list);
		}
	}
}

public class Solution4 {

	public static int bstDistance(int[] values, int n, int node1, int node2)
	{
		int distance = 0;
		
		BinarySearchTree bst = new BinarySearchTree();
		List<Integer> l1 = new ArrayList<>();
		List<Integer> l2 = new ArrayList<>();
		for (int i: values)
		{
			bst.root = bst.insert(bst.root, i, 0);
		}
		// find first
		Node n1 = bst.search(bst.root, node1, l1);
		// find second
		Node n2 = bst.search(bst.root, node2, l2);
		// if one of them do not exist, return -1
		if (n1 == null || n2 == null)
		{
			return -1;
		} 
		else if (n1 == n2)
		{
			return 0;
		}
		
		//distance = findDist(l1.toArray(), x, y)
		
		List<Integer> list = new ArrayList<>();
		bst.inorder(bst.root, list);
		boolean countDist = false;
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) == node1)
			{
				countDist = true;
			}
			if (list.get(i) == node2)
			{
				break;
			}
			if (countDist)
			{
				distance++;
			}
		}
		
		return distance;
	}
	
	static int findDist(int []a, int x, int y)
	{
		int dist = 0;
		boolean countDist = false;
		for (int i: a)
		{
			if (i == x)
			{
				countDist = true;
			}
			if (i == y)
			{
				break;
			}
			if (countDist)
			{
				dist++;
			}
			
		}
		return dist;
	}
	
}
