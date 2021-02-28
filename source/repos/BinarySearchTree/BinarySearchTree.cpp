#include "/Users/varka/source/repos/BinarySearchTree/BinarySearchTree.h"

BinarySearchTree::BinarySearchTree()
{ root = nullptr; }

BinarySearchTree::BinarySearchTree(int key_number)
{set_root(key_number); }

bool BinarySearchTree::is_empty() { return (root == nullptr); }

Node* BinarySearchTree::set_root(int root_key) 
{
	Node* new_node = new Node(root_key);
	new_node->key = root_key;
	root = new_node;
	return root;
}
Node* BinarySearchTree::get_root() { return root; }

void BinarySearchTree::insert(int vertex_key) 
{
	if (is_empty())
		set_root(vertex_key);
	else
	{
		Node* now = get_root();
		while (now != nullptr) // while did not set element
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

bool BinarySearchTree::contains(int vertex_key) 
{
	if (is_empty())
		throw out_of_range("search in the empty tree");
	Node* now = get_root();
	while (vertex_key != now->get_key())
	{
		if (vertex_key < now->get_key())
		{ // search in the left branch
			if (now->get_left_child() == nullptr)
				return false; // false case, element doesn't exist
			now = now->get_left_child();
		}
		else
		{ // search in the right branch
			if (now->get_right_child() == nullptr)
				return false; // false case, element doesn't exist	
			now = now->get_right_child();
		}
	}
	return true;
}

void BinarySearchTree::remove(int vertex_key) 
{
	if (is_empty())
		throw out_of_range("remove from empty tree");
	// we should find this element
	Node* to_delete = get_root(); 
	Node* parent_of_to_delete = get_root();
	while (vertex_key != to_delete->get_key())
	{
		if (vertex_key < to_delete->get_key())
		{ // search in the left branch
			if (to_delete->get_left_child() == nullptr)
			{
				to_delete = nullptr;
				break;
			}// false case, element doesn't exist
			parent_of_to_delete = to_delete;
			to_delete = to_delete->get_left_child();
		}
		else
		{ // search in the right branch
			if (to_delete->get_right_child() == nullptr)
			{
				to_delete = nullptr;
				break;
			}// false case, element doesn't exist	
			parent_of_to_delete = to_delete;
			to_delete = to_delete->get_right_child();
		}
	}
	if (to_delete == nullptr)
		throw out_of_range("remove not-existing element");
	if (to_delete->get_left_child() == nullptr)
	{
		if (to_delete->get_right_child() == nullptr)
		{//case 1: a leaf
			if (to_delete == get_root())
			{ // root is the only element in the tree
				delete root;
				root = nullptr;
				return;
			}
			if (parent_of_to_delete->get_left_child() == to_delete)
			{
				delete parent_of_to_delete->left_child;
				parent_of_to_delete->left_child = nullptr;
				return;
			}
			if (parent_of_to_delete->get_right_child() == to_delete)
			{
				delete parent_of_to_delete->right_child;
				parent_of_to_delete->right_child = nullptr;
				return;
			}
		}
		else
		{// case 2: to_delete has only right child
			if (to_delete == get_root())
				root = to_delete->get_right_child();
			else if (parent_of_to_delete->get_left_child() == to_delete)
				parent_of_to_delete->left_child = to_delete->get_right_child();
			else if (parent_of_to_delete->get_right_child() == to_delete)
				parent_of_to_delete->right_child = to_delete->get_right_child();
			to_delete = nullptr;
			return;
		}
	}
	else if (to_delete->get_right_child() == nullptr)
	{ // case 3: to_delete has only left child
		if (to_delete == get_root())
			root = to_delete->get_left_child();
		else if (parent_of_to_delete->get_left_child() == to_delete)
			parent_of_to_delete->left_child = to_delete->get_left_child();
		else if (parent_of_to_delete->get_right_child() == to_delete)
			parent_of_to_delete->right_child = to_delete->get_left_child();
		to_delete = nullptr;
		return;
	}
	else
	{// case 4: to_delete has both childs
		Node* change_leaf = to_delete->get_right_child();
		Node* change_leaf_parent = to_delete;
		while (change_leaf->get_left_child() != nullptr)
		{ // go to the left leaf
			change_leaf_parent = change_leaf;
			change_leaf = change_leaf->get_left_child();
		}
		if (to_delete == get_root())
			set_root(change_leaf->get_key());
		else
			to_delete->set_key(change_leaf->get_key());
		if (change_leaf != to_delete->get_right_child())
		{
			delete change_leaf_parent->get_left_child();
			change_leaf_parent->left_child = nullptr;
		}
		else
		{
			delete change_leaf_parent->right_child;
			change_leaf_parent->right_child = nullptr;
		}
	}
}

BinarySearchTree::~BinarySearchTree()
{
	while (get_root() != nullptr)
		remove(get_root()->get_key());
}



Iterator* BinarySearchTree::create_breadth_first_traverse_iterator()
{ return new BreadthFirstTraverse_Iterator(get_root()); }

BinarySearchTree::BreadthFirstTraverse_Iterator::BreadthFirstTraverse_Iterator(Node* start)
{
	current = start;
	queue.push(current);
}

bool BinarySearchTree::BreadthFirstTraverse_Iterator::has_next()
{ return (!queue.is_empty()); }

Node* BinarySearchTree::BreadthFirstTraverse_Iterator::next()
{
	if (queue.is_empty())
		return current = nullptr;
	current = queue.pop()->get_data();
	if (current->get_left_child() != nullptr)
		queue.push(current->get_left_child());
	if (current->get_right_child() != nullptr)
		queue.push(current->get_right_child());
	return current;
}



Iterator* BinarySearchTree::create_pre_ordered_depth_first_traverse_iterator()
{ return new PreOrderedDepthFirstTraverse_Iterator(get_root()); }

BinarySearchTree::PreOrderedDepthFirstTraverse_Iterator::PreOrderedDepthFirstTraverse_Iterator(Node* start)
{
	current = start;
	stack.push(current);
}

bool BinarySearchTree::PreOrderedDepthFirstTraverse_Iterator::has_next()
{ return (!stack.is_empty()); }

Node* BinarySearchTree::PreOrderedDepthFirstTraverse_Iterator::next()
{
	if (stack.is_empty())
		return current = nullptr;
	Node* to_return = new Node;
	to_return = current;
	if (current->get_right_child() != nullptr)
		stack.push(current->get_right_child());
	if (current->get_left_child() != nullptr)
		current = current->get_left_child();
	else if (!stack.is_empty())
		current = stack.pop()->get_data();
	return to_return;
}



Iterator* BinarySearchTree::create_in_ordered_depth_first_traverse_iterator()
{ return new InOrderedDepthFirstTraverse_Iterator(get_root()); }

BinarySearchTree::InOrderedDepthFirstTraverse_Iterator::InOrderedDepthFirstTraverse_Iterator(Node* start)
{ current = start; }

bool BinarySearchTree::InOrderedDepthFirstTraverse_Iterator::has_next()
{ return (current != nullptr); }

Node* BinarySearchTree::InOrderedDepthFirstTraverse_Iterator::next()
{
	Node* to_return = new Node;
	to_return = nullptr;
	if (!stack.is_empty())
	{
		if (stack.peek()->get_data() != current) // else we skip getting down procedure
		{
			stack.push(current);
			while (current->get_left_child() != nullptr)
			{
				current = current->get_left_child();
				stack.push(current);
			}
		}
		to_return = stack.pop()->get_data();
	}
	else if (current != nullptr) // when reach the root stack is empty but we have right branch
	{
		stack.push(current);
		while (current->get_left_child() != nullptr)
		{
			current = current->get_left_child();
			stack.push(current);
		}
		to_return = stack.pop()->get_data();
	}
	else
		return nullptr;
	if (current->get_right_child() != nullptr)
		current = current->get_right_child(); // the next is right-child node
	else if (!stack.is_empty())
		current = stack.peek()->get_data(); // the next is the top from the stack
	else
		current = nullptr;
	return to_return;
}



Iterator* BinarySearchTree::create_post_ordered_depth_first_traverse_iterator()
{ return new PostOrderedDepthFirstTraverse_Iterator(get_root()); }

BinarySearchTree::PostOrderedDepthFirstTraverse_Iterator::PostOrderedDepthFirstTraverse_Iterator(Node* start)
{
	current = start;
	stack.push(current);
	while ((current->get_left_child() != nullptr) || (current->get_right_child() != nullptr))
	{
		if (current->get_left_child() != nullptr)
		{
			if (current->get_right_child() != nullptr)
			{
				stack.push(current->get_right_child());
				stack.push(current);
			}
			current = current->get_left_child();
			stack.push(current);
		}
		else if (current->get_right_child() != nullptr)
		{
			current = current->get_right_child();
			if (((current->get_left_child() != nullptr) && (current->get_right_child() == nullptr))|| ((current->get_right_child() != nullptr) && (current->get_left_child() == nullptr)))
				stack.push(current); // if has only one child
		}
	}
}

bool BinarySearchTree::PostOrderedDepthFirstTraverse_Iterator::has_next()
{ return (current != nullptr); }

Node* BinarySearchTree::PostOrderedDepthFirstTraverse_Iterator::next()
{
	Node* to_return = new Node;
	to_return = nullptr;
	if (!stack.is_empty())
	{
		if ((current->get_right_child() == stack.peek()->get_data()) || (current->get_left_child() == stack.peek()->get_data()))
		{ // if current has childs
			current = stack.peek()->get_data(); // take the one that wasn't considered
			while ((current->get_left_child() != nullptr)||(current->get_right_child() != nullptr))
			{
				if (current->get_left_child() != nullptr)
				{ // if left exists add right and go to left
					if (current->get_right_child() != nullptr)
					{
						stack.push(current->get_right_child());
						stack.push(current);
					}
					current = current->get_left_child();
					stack.push(current);
				} // if left doesn't exist, but right exist
				else if (current->get_right_child() != nullptr)
				{
					current = current->get_right_child();
					if (((current->get_left_child() != nullptr) && (current->get_right_child() == nullptr)) || ((current->get_right_child() != nullptr) && (current->get_left_child() == nullptr)))
						stack.push(current);
				}
			}
			to_return = stack.pop()->get_data();
		}
		else 
		{
			if (current == stack.peek()->get_data())
				to_return = stack.pop()->get_data();
			else
				to_return = current;
		}
	}
	else if (current != nullptr)
	{
		to_return = current;
		current = nullptr;
	}
	if (!stack.is_empty())
		current = stack.pop()->get_data(); // the next is the top from the stack
	return to_return;
}