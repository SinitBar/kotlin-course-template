#pragma once
#include <iostream>
#include <stdexcept>
using namespace std;
class element
{
	element* next;
	string data;
public:
	element();
	element* get_next();
	string get_data();
	void set_next(element*);
	void set_data(string);
	~element();
};

class L1List
{
	element* head = nullptr;
	element* cur = nullptr;
	element* tail = nullptr;
public:
	L1List();
	void set_head(element*);
	void set_cur(element*);
	void set_tail(element*);
	element* get_head();
	element* get_cur();
	element* get_tail();
	void set_next(element*); // change next regarding to cur 
	void set_data(string); // chage cur data
	string get_data(); // get cur data
	element* get_next(); // get next regarding to cur
	bool isEmpty(); // check for empty list
	void push_back(string); // adding to the end of the list
	void push_front(string); // adding to the begining of the list
	void pop_back(); // delete last element
	void pop_front(); // delete first element
	size_t get_size(); // get size of the list
	void insert(string, size_t); // adding element on index (before the element, that had this index lately)
	element* at(size_t); // return element on index
	void remove(size_t); // delete element on index
	void print_to_console(); // print elements to console without using at()
	void clear(); // delete all elements of the list
	void set(size_t, string); // change data of element on index
	void push_front(L1List*); // insertion another list into begining of the data-list
	~L1List();
};