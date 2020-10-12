//#include "pch.h"
#include "CppUnitTest.h"
#include "/Users/varka/source/repos/AlgoLaba1_OneWayLinkedList/L1List.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTestForOneWayLinkedList
{
	TEST_CLASS(UnitTestForOneWayLinkedList)
	{
	public:
		TEST_METHOD(IsEmptyListWhenEmpty)
		{
			L1List a;
			Assert::AreEqual(a.isEmpty(), true);
		}
		TEST_METHOD(IsEmptyListWhenNotEmpty)
		{
			L1List a;
			a.push_back((string)"bla");
			Assert::AreEqual(a.isEmpty(), false);
		}
		TEST_METHOD(ListSizeZero)
		{
			L1List a;
			Assert::AreEqual(a.get_size(), (size_t)0);
		}
		TEST_METHOD(ListSizeNotZero)
		{
			L1List a;
			a.push_back("bla");
			a.push_back("ha");
			a.push_front("hehe");
			Assert::AreEqual(a.get_size(), (size_t)3);
		}
		TEST_METHOD(PushBackOneElement)
		{
			L1List a;
			a.push_back("bla");
			Assert::AreEqual(a.get_head()->get_data(), (string)"bla");
		}
		TEST_METHOD(PushBackElements)
		{
			L1List a;
			a.push_back("bla");
			a.push_back("car");
			Assert::AreEqual(a.get_data(), (string)"car");
		}
		TEST_METHOD(PopBackFromEmptyList)
		{
			L1List a;
			try
			{
				a.pop_back();
			}
			catch (const exception& message)
			{
				Assert::AreEqual(message.what(), "The List is empty");
			}
		}
		TEST_METHOD(PopBackFromListWithOneElement)
		{
			L1List a;
			a.push_back("hello");
			a.pop_back();
			Assert::AreEqual(a.isEmpty(), true);
		}
		TEST_METHOD(PopBackFromList)
		{
			L1List a;
			a.push_back("!!!");
			a.push_back("hello");
			a.push_back("bro");
			a.pop_back();
			Assert::AreEqual(a.get_data(), (string)"hello");
		}
		TEST_METHOD(PushFrontInEmptyList)
		{
			L1List a;
			a.push_front("hey");
			Assert::AreEqual(a.get_data(), (string)"hey");
			Assert::AreEqual(a.get_next() == nullptr, true);
			Assert::AreEqual(a.get_head() == a.get_tail(), true);
		}
		TEST_METHOD(PushFrontInNotEmptyList)
		{
			L1List a;
			a.push_front("a good day");
			a.push_front("i wish you");
			a.push_front("hey"); 
			Assert::AreEqual(a.get_data(), (string)"hey");
		}
		TEST_METHOD(PopFrontFromEmptyList)
		{
			L1List a;
			try
			{
				a.pop_front();
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "The List is empty");
			}
		}
		TEST_METHOD(PopFrontFromList)
		{
			L1List a;
			a.push_front("a good day");
			a.push_front("i wish you");
			a.push_front("hey");
			a.pop_front();
			Assert::AreEqual(a.get_data(), (string)"i wish you");
		}
		TEST_METHOD(PopFrontFromOneElementList)
		{
			L1List a;
			a.push_front("hey");
			a.pop_front();
			Assert::AreEqual(a.isEmpty(), true);
		}
		TEST_METHOD(AtInEmptyList)
		{
			L1List a;
			try
			{
				a.at(0);
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "Invalid index");
			}
		}
		TEST_METHOD(AtInListWhenPushFronted)
		{
			L1List a;
			a.push_front("a good day");
			a.push_front("i wish you");
			a.push_front("hey");
			Assert::AreEqual(a.at(2)->get_data(), (string)"a good day");
			Assert::AreEqual(a.at(1)->get_data(), (string)"i wish you");
			Assert::AreEqual(a.at(0)->get_data(), (string)"hey");
		}
		TEST_METHOD(AtInListWhenPushBacked)
		{
			L1List a;
			a.push_back("hey");
			a.push_back("i wish you");
			a.push_back("a good day");
			Assert::AreEqual(a.at(2)->get_data(), (string)"a good day");
			Assert::AreEqual(a.at(1)->get_data(), (string)"i wish you");
			Assert::AreEqual(a.at(0)->get_data(), (string)"hey");
		}
		TEST_METHOD(AtInListWhenPushed)
		{
			L1List a;
			a.push_back("i wish you");
			a.push_front("hey");
			a.push_back("a good day");
			Assert::AreEqual(a.at(2)->get_data(), (string)"a good day");
			Assert::AreEqual(a.at(1)->get_data(), (string)"i wish you");
			Assert::AreEqual(a.at(0)->get_data(), (string)"hey");
		}
		TEST_METHOD(InsertToListBegin)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 0);
			Assert::AreEqual(a.at(0)->get_data(), (string)"Oh no!");
		}
		TEST_METHOD(InsertToListEnd)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 3);
			Assert::AreEqual(a.at(3)->get_data(), (string)"Oh no!");
		}
		TEST_METHOD(InsertToListMiddle)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 2);
			Assert::AreEqual(a.at(2)->get_data(), (string)"Oh no!");
		}
		TEST_METHOD(InsertToListInvalidIndex)
		{
			L1List a;
			a.push_front("khe");
			try
			{
				a.insert("bla", 2);
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "Invalid index");
			}
		}
		TEST_METHOD(RemoveFromListInvalidIndex)
		{
			L1List a;
			a.push_front("khe");
			try
			{
				a.remove(2);
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "Invalid index");
			}
		}
		TEST_METHOD(RemoveFromOneElementList)
		{
			L1List a;
			a.push_front("khe");
			a.remove(0);
			Assert::AreEqual(a.isEmpty(), true);
		}
		TEST_METHOD(RemoveFromList)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 2);
			a.remove(2);
			Assert::AreEqual(a.at(2)->get_data(), (string)"we are going to die");
		}
		TEST_METHOD(RemoveFromListEnd)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 2);
			a.remove(3);
			Assert::AreEqual(a.at(2) == a.get_tail(), true);
		}
		TEST_METHOD(RemoveFromListBegin)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.insert("Oh no!", 2);
			a.remove(0);
			Assert::AreEqual(a.at(0)->get_data(), (string)"is this a coronavirus???");
		}
		TEST_METHOD(SetInListBegin)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.set(0, "apchi!");
			Assert::AreEqual(a.at(0)->get_data(), (string)"apchi!");
		}
		TEST_METHOD(SetInListEnd)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.set(2, "apchi!");
			Assert::AreEqual(a.at(2)->get_data(), (string)"apchi!");
		}
		TEST_METHOD(SetInEmptyList)
		{
			L1List a;
			try
			{
				a.set(0, "apchi!");
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "Invalid index");
			}
		}
		TEST_METHOD(SetInListInvalidIndex)
		{
			L1List a;
			a.push_back("khe-khe");
			try
			{
				a.set(1, "apchi!");
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "Invalid index");
			}
		}
		TEST_METHOD(SetInList)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.set(1, "apchi!");
			Assert::AreEqual(a.at(1)->get_data(), (string)"apchi!");
		}
		TEST_METHOD(ClearEmptyList)
		{
			L1List a;
			try
			{
				a.clear();
			}
			catch (const exception & message)
			{
				Assert::AreEqual(message.what(), "The List is empty");
			}
		}
		TEST_METHOD(ClearList)
		{
			L1List a;
			a.push_front("khe");
			a.push_back("is this a coronavirus???");
			a.push_back("we are going to die");
			a.set(1, "apchi!");
			a.clear();
			Assert::AreEqual(a.isEmpty(), true);
			Assert::AreEqual(a.get_head() == nullptr, true);
			Assert::AreEqual(a.get_tail() == nullptr, true);
			Assert::AreEqual(a.get_cur() == nullptr, true);
			Assert::AreEqual(a.get_size(), (size_t)0);
		}
		TEST_METHOD(PushFrontAList)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			Assert::AreEqual(two.at(1)->get_data(), (string)"my name is Barbara");
			Assert::AreEqual(one.get_size(), (size_t)2);
			Assert::AreEqual(two.get_size(), (size_t)5);
		}
		TEST_METHOD(PushFrontAListChangeOne)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			one.pop_front();
			Assert::AreEqual(two.at(0)->get_data(), (string)"Hello");
			Assert::AreEqual(one.get_size(), (size_t)1);
			Assert::AreEqual(two.get_size(), (size_t)5);
		}
		TEST_METHOD(PushFrontAListClearOne)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			one.clear();
			Assert::AreEqual(two.at(0)->get_data(), (string)"Hello");
			Assert::AreEqual(one.get_size(), (size_t)0);
			Assert::AreEqual(two.get_size(), (size_t)5);
		}
		TEST_METHOD(PushFrontAListClearTwo)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			two.clear();
			Assert::AreEqual(two.isEmpty(), true);
			Assert::AreEqual(one.get_size(), (size_t)2);
			Assert::AreEqual(one.at(0)->get_data(), (string)"Hello");
		}
		TEST_METHOD(PushFrontAListChangeTwo)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			two.set(1, "I'm Bond. James Bond.");
			Assert::AreEqual(one.at(1)->get_data(), (string)"my name is Barbara");
			Assert::AreEqual(two.at(1)->get_data(), (string)"I'm Bond. James Bond.");
		}
		TEST_METHOD(PushFrontAListClearBoth)
		{
			L1List one, two;
			one.push_back("Hello");
			one.push_back("my name is Barbara");
			two.push_back("I'm to become a programmer");
			two.push_back("And this is good");
			two.push_back("What do you think?");
			two.push_front(&one);
			one.clear();
			two.clear();
			Assert::AreEqual(two.isEmpty(), true);
			Assert::AreEqual(one.isEmpty(), true);
		}
		TEST_METHOD(PushFrontAListSecondEmpty)
		{
			L1List a, b;
			a.push_back("maybe"); // 0
			a.push_back("today"); // 1
			a.push_back("is"); // 2
			a.push_back("a very"); // 3
			a.push_back("good"); // 4
			a.push_back("day?"); // 5
			b.push_front(&a);
			Assert::AreEqual(b.at(0)->get_data(), (string)"maybe");
			Assert::AreEqual(b.at(1)->get_data(), (string)"today");
			Assert::AreEqual(b.at(2)->get_data(), (string)"is");
			Assert::AreEqual(b.at(3)->get_data(), (string)"a very");
			Assert::AreEqual(b.at(4)->get_data(), (string)"good");
			Assert::AreEqual(b.at(5)->get_data(), (string)"day?");
			Assert::AreEqual(b.get_size(), (size_t)6);
		}
	};
}
