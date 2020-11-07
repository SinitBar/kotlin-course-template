#include "/Users/varka/source/repos/BinarySearchTree/BinarySearchTree.h"
#include "/Users/varka/source/repos/BinarySearchTree/QueueAndStack.h"

Node::Node()
{
	key = 0;
	left_child = nullptr;
	right_child = nullptr;
}

Node::Node(int key_number)
{
	key = key_number;
	left_child = nullptr;
	right_child = nullptr;
}

Node* Node::set_left_child(int child_key)
{
	Node new_node(child_key);
	left_child = &new_node;
	return left_child;
}

Node* Node::set_right_child(int child_key)
{
	Node new_node(child_key);
	right_child = &new_node;
	return right_child;
}

void Node::set_key(int key_number) { key = key_number;}
Node* Node::get_left_child() { return left_child; }
Node* Node::get_right_child() { return right_child; }
int Node::get_key() { return key; }
Node::~Node() 
{ 
	delete left_child; 
	delete right_child; 
	left_child = right_child = nullptr; 
}


BinarySearchTree::BinarySearchTree()
{ root = nullptr; }

BinarySearchTree::BinarySearchTree(int key_number)
{
	Node new_node(key_number);
	root = &new_node;
}

bool BinarySearchTree::is_empty() { return (root == nullptr); }

Node* BinarySearchTree::set_root(int root_key) 
{
	Node new_node(root_key);
	root = &new_node;
	return root;
}

Node* BinarySearchTree::get_root() { return root; }

Iterator* BinarySearchTree::create_bft_iterator()
{ return new BFT_Iterator(root); }

BinarySearchTree::BFT_Iterator::BFT_Iterator(Node* start)
{ current = start; }

bool BinarySearchTree::BFT_Iterator::has_next() // ?????????
{
	if ((current->get_left_child() == nullptr) && (current->get_right_child() == nullptr))
		return false;
	return true;
}

Node* BinarySearchTree::BFT_Iterator::next()
{
	Node* to_return = current;

	return to_return;
}

void BinarySearchTree::insert(int vertex_key) // добавление элемента в дерево по ключу. Должен работать за O(logN)
{
	if (is_empty())
		root = &Node(vertex_key);
	else
	{
		Node* now = root;
		while (true) // while did not set element
		{
			if (vertex_key <= now->get_key())
			{ // search for the place in the left branch
				if (now->get_left_child() == nullptr)
				{
					now->set_left_child(vertex_key);
					break;
				}
				now = now->get_left_child();
			}
			else
			{ // search for the place in the right branch
				if (now->get_right_child() == nullptr)
				{
					now->set_right_child(vertex_key);
					break;
				}
				now = now->get_right_child();
			}
		}
	}
}

Node* BinarySearchTree::contains(int vertex_key) // поиск элемента в дереве по ключу
{
	if (is_empty())
		throw out_of_range("search in the empty tree");
	if (root->get_key() == vertex_key)
		return root; // true case, element exists
	Node* now = root;
	while (now->get_key() != vertex_key)
	{
		if (vertex_key <= now->get_key())
		{ // search in the left branch
			if (now->get_left_child() == nullptr)
			{
				now = nullptr; // false case, element doesn't exist
				break;
			}
			now = now->get_left_child();
		}
		else
		{ // search in the right branch
			if (now->get_right_child() == nullptr)
			{
				now = nullptr; // false case, element doesn't exist
				break;
			}
			now = now->get_right_child();
		}
	}
	return now;
}

void BinarySearchTree::remove(int vertex_key) // удаление элемента дерева по ключу
{
	Node* to_delete = contains(vertex_key);
	if (to_delete == nullptr)
		throw out_of_range("remove from the empty tree");
	if ((to_delete->get_left_child() == nullptr) && (to_delete->get_right_child() == nullptr))
	{ // a leaf
		delete to_delete;
		to_delete = nullptr;
	}
	else if (to_delete->get_left_child() != nullptr) 
	{ // only left child
		Node* child = to_delete->get_left_child();
		swap(child, to_delete); // child goes on the place of his parent
		delete child; // now child is a node to delete
	}
	else if (to_delete->get_right_child() != nullptr)
	{// only right child
		Node* child = to_delete->get_right_child();
		swap(child, to_delete); // child goes on the place of his parent
		delete child; // now child is a node to delete
	}
	else
	{// both childs
		Node* left_leaf_from_right_branch = to_delete->get_right_child();
		// |^| went to the right branch
		while (left_leaf_from_right_branch->get_left_child() != nullptr)
		{
			left_leaf_from_right_branch = left_leaf_from_right_branch->get_left_child();
		}
		swap(left_leaf_from_right_branch, to_delete); // to_delete has right parent and key, but not childs
		to_delete->left_child = left_leaf_from_right_branch->get_left_child();
		to_delete->right_child = left_leaf_from_right_branch->get_right_child();
		delete left_leaf_from_right_branch;
	}

	
}

BinarySearchTree::~BinarySearchTree()
{
	root = nullptr; // сделать бы обход итератором с конца, дабы поудалять все
}