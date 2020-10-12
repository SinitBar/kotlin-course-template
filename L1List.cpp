#include "L1List.h"
using namespace std;

element::element()
{
	data = "";
	next = nullptr;
}
element* element::get_next() { return next; }
string element::get_data() { return data; }
void element::set_next(element* e) { next = e; }
void element::set_data(string s) { data = s; }
element::~element() { }

L1List::L1List()
{
	head = nullptr;
	cur = nullptr;
	tail = nullptr;
}
element* L1List::get_head() { return head; }
element* L1List::get_cur() { return cur; }
element* L1List::get_tail() { return tail; }
void L1List::set_head(element* e) { head = e; }
void L1List::set_cur(element* e) { cur = e; }
void L1List::set_tail(element* e) { tail = e; }
void L1List::set_next(element* e) { cur->set_next(e); }
void L1List::set_data(string data) { cur->set_data(data); }
element* L1List::get_next() { return (cur->get_next()); }
string L1List::get_data() { return (cur->get_data()); }

bool L1List::isEmpty()
{
	if (head != nullptr)
		return false; // list is not empty
	return true;
}

void L1List::push_back(string data)
{
	element* e = new element;
	e->set_data(data);
	if (isEmpty())
		head = cur = tail = e;
	else
	{
		tail->set_next(e);
		tail = tail->get_next();
		cur = tail;
	}
}

void L1List::push_front(string data)
{
	element* e = new element;
	e->set_data(data);
	if (isEmpty())
		head = cur = tail = e;
	else
	{
		cur = e; // cur = new elem
		cur->set_next(head); // cur->next = head
		head = cur; // head = cur
	}
}

void L1List::pop_back()
{
	if (!isEmpty())
	{
		set_cur(get_head()); // cur = head
		if (get_next() == nullptr) // delete head
		{
			element* del = head;
			cur = head = tail = nullptr;
			delete del;
		}
		else
		{
			while (get_next()->get_next() != nullptr) // while cur->next->next exists
			{
				set_cur(get_next()); // cur = cur->next
			} // cur = the element before the last existing element
			element* del = get_next();
			cur->set_next(nullptr);
			tail = cur;
			delete del;
		}
	}
	else
		throw out_of_range("The List is empty");
}

void L1List::pop_front()
{
	if (!isEmpty())
	{
		cur = head;
		if (get_next() == nullptr) // delete head
		{
			element* del = head;
			head = cur = tail = nullptr;
			delete del;
		}
		else
		{
			element* del = head;
			set_cur(head->get_next());// cur = head->next
			set_head(cur); // head = cur
			delete del;
		}
	}
	else
		throw out_of_range("The List is empty");
}
size_t L1List::get_size()
{
	size_t Lsize = 0;
	if (!isEmpty())
	{
		Lsize = 1;
		set_cur(get_head()); // now = head
		while (get_next() != nullptr) // while cur->next exists
		{
			set_cur(get_next()); // cur = cur->next
			Lsize++;
		} // cur = last existing element
	}
	return Lsize;
}
void L1List::insert(string data, size_t index) // first index = 0
{
	if (index == get_size())
		push_back(data);
	else if (index == 0)
		push_front(data);
	else if (index > get_size())
		throw out_of_range("Invalid index");
	else
	{
		set_cur(get_head()); // now = head
		while (index > 1)
		{
			index--;
			set_cur(get_next()); // cur = cur->next
		} // cur is the element before the future new element
		element* e = new element;
		e->set_data(data);
		e->set_next(get_next()); //e->next = cur->next
		set_next(e); // cur->next = e
		set_cur(e);
	}
}

element* L1List::at(size_t index) // first index = 0
{
	if (index >= get_size())
		throw out_of_range("Invalid index");
	else if (index == 0)
		return head;
	else
	{
		cur = head;
		while (index > 0)
		{
			index--;
			set_cur(get_next()); // cur = cur->next
		} // cur is the element with data index
		return cur;
	}
}

void L1List::remove(size_t index)
{
	if (index >= get_size())
		throw out_of_range("Invalid index");
	else if (index == 0) // delete head
		pop_front();
	else if (index == get_size() - 1)
		pop_back();
	else
	{
		cur = head;
		while (index > 1)
		{
			index--;
			set_cur(get_next()); // cur = cur->next
		} // cur is the element before the deleting element
		element* e = new element;
		e = get_next();
		set_next(e->get_next()); //cur->next = cur->next->next
		if (e == nullptr)
			tail = cur;
		delete e;
	}
}

void L1List::print_to_console()
{
	set_cur(get_head()); // now = head
	while (get_next() != nullptr)
	{
		cout << get_data() << endl;
		set_cur(get_next()); // cur = cur->next
	}
	cout << get_data() << endl;
}

void L1List::clear()
{
	if (!isEmpty())
	{
		set_cur(get_head()); // cur = head
		while (get_next() != nullptr) // while next exists
		{
			set_cur(get_next()); // cur = cur->next
			delete get_head();
			set_head(get_cur()); // head = cur

		} //cur - the last in the list
		element* e = get_head();
		head = cur = tail = nullptr;
		delete e;
	}
	else
		throw exception("The List is empty");
}

void L1List::set(size_t index, string data) // change data on index element
{
	if (index >= get_size())
		throw out_of_range("Invalid index");
	else if (index == 0)
		head->set_data(data);
	else if (index == get_size() - 1)
		tail->set_data(data);
	else
	{
		set_cur(get_head()); // now = head
		while (index > 0)
		{
			index--;
			set_cur(get_next()); // cur = cur->next
		} // cur is the element with data index
		cur->set_data(data);
	}
}

void L1List::push_front(L1List* L) // insertion another list into the begining of data
{
	if (!L->isEmpty())
	{
		push_front(L->get_head()->get_data()); // first in this is the same as the first in L now
		element* h = head; // now head is a new element because that's how my push_front works
		L->set_cur(L->get_head());
		while (L->get_next() != nullptr) // while cur->next exists
		{
			L->set_cur(L->get_next());
			insert(L->get_cur()->get_data(), 1);
			head = cur; // head is an inserted element - this is made for next iteration
		}
		set_head(h);
	}
}

L1List::~L1List()
{
	if (!isEmpty())
		clear();
}

int main()
{
	L1List a;
	L1List b;
	b.push_back("hey");
	b.push_front("hmmm");
	b.push_back("have you ever thought that...");
	a.push_back("maybe");
	a.push_back("today");
	a.push_back("is");
	a.push_back("a very");
	a.push_front(&b);
	a.push_back("good");
	a.push_back("day?");
	a.print_to_console();
	a.clear();
	cout << a.isEmpty() << endl;
	return 0;
}