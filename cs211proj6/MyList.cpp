#include <cstdio>
#include "MyList.h"


//  Code for the methods is the MyLNode class
//    These are the Node Level operations
//    Contains: 
//      2 constructors
//      Getters and Setters for the 2 data members
MyLNode::MyLNode (int v1)
{
  elem = v1;
  next = nullptr;
}
  
MyLNode::MyLNode (int v1, MyLNode* n)
{
  elem = v1;
  next = n;
}

void MyLNode::setElem (int e)
{
  elem = e;
}

int MyLNode::getElem ()
{
  return elem;
}

void MyLNode::setNext (MyLNode* n)
{
  next = n;
}

MyLNode* MyLNode::getNext()
{
  return next;
}



//  Methods for the MyList class
//    These are the List Level operations
MyList::MyList()
{
  head = nullptr;
}

MyList::~MyList()
{
  MyLNode* cur = head;
  MyLNode* prev;
  while(cur != nullptr){
    prev = cur;
    cur = cur->getNext();
    delete prev;
  }
}

void MyList::init()
{
  head = nullptr;
}
 
void MyList::show()
{
  MyLNode* cur = this->head;
  while(cur != nullptr){
    printf("%d ", cur->getElem());
    cur = cur->getNext();
  }
  printf("\n");
}

void MyList::insert (int v1)
{
  MyLNode* tmp = new MyLNode (v1);
  
  if(search(v1)){
    return;
  }

  if(head == nullptr){
	  head = tmp;
	  return;
  }

  MyLNode* cur = this->head;
  MyLNode* prev;

  if(v1 < cur->getElem()){
	  tmp->setNext(head);
	  head = tmp;
	  return;
  }
  else{
    while(cur != nullptr){
	    if(v1 > cur->getElem()){
	      prev = cur;
	      cur = cur->getNext();
	    }
	    else{
	      break;
	    }
    }
    if(cur == nullptr){
	prev->setNext(tmp);
    }
    else{
	tmp->setNext(cur);
	prev->setNext(tmp);
    }
  }
}

void MyList::remove (int v1)
{

 if(!this->search(v1)){
   return;
 }
 if(head == nullptr)
	 return;

 MyLNode* cur = head;
 MyLNode* prev;
 if(head->getElem() == v1){
  if(head->getNext() == nullptr){
    head = nullptr;
  }
  else{
    head = head->getNext();
  }
  delete cur;
  return;
 }
 while((cur->getNext()!=nullptr) && (cur->getElem()!=v1)){
  prev = cur;
  cur = cur->getNext();
 }
 if(cur->getElem() == v1){
  if(cur->getNext() != NULL){ 
    prev->setNext(cur->getNext());
  }
  else{
    prev->setNext(nullptr);
  }
  delete cur;
 }
}

bool MyList::isEmpty(){
  if(this->head == nullptr){
	  return true;
  }
  else{
	  return false;
  }
}

int MyList::getListLength(){
  int count = 0;
  if(head == NULL){
    return 0;
  }
  else{
    count++;
  }
  MyLNode* cur = head;
  while(cur->getNext() != nullptr){
	  count++;
	  cur = cur->getNext();
  }
  return count;
}

int MyList::getNthElem(int n){
  int count = 0;
  MyLNode* cur = head;
  while(count < n){
    cur = cur->getNext();
    count++;
  }
  return cur->getElem();
}  

void MyList::removeAll(){
  MyLNode* cur = head;
  MyLNode* prev = nullptr;
  while(cur != nullptr){
   if(prev != nullptr){
    delete prev;
   }
   prev = cur;
   cur = cur->getNext();
  }
  delete prev;
  head = nullptr;
}

bool MyList::search(int val){
  MyLNode* cur = head;
  while(cur != nullptr){
    if(cur->getElem() == val){
      return true;
    }
    else{
      cur = cur->getNext();
    }
  }
  return false;
}

void MyList::push_queue(int val){
  MyLNode* temp = new MyLNode(val);
  if(head == nullptr){
    head = temp;
    return;
  }
  MyLNode* cur = head;
  while(cur->getNext() != nullptr){
    cur = cur->getNext();
  }
  cur->setNext(temp);
}
int MyList::pop_queue(){
  int value = head->getElem();
  MyLNode* temp = head;
  head = head->getNext();
  delete temp;
  return value;
}
void MyList::push_stack(int val){
  MyLNode* temp = new MyLNode(val);
  if(head == nullptr){
    head = temp;
    return;
  }
  MyLNode* cur = head;
  while(cur->getNext() != nullptr){
    cur = cur->getNext();
  }
  cur->setNext(temp);
}
int MyList::pop_stack(){
  if(head == nullptr){
    return -1;
  }
  MyLNode* cur = head;
  MyLNode* prev;
  while(cur->getNext() != nullptr){
    prev = cur;
    cur = cur->getNext();
  }
  int value = cur->getElem();
  prev->setNext(nullptr);
  delete cur;
  return value;
}

void MyList::addToFront(int val){
  MyLNode* temp = new MyLNode(val);
  if(head == nullptr){
    head = temp;
  }
  else{
    temp->setNext(head);
    head = temp;
  }
}