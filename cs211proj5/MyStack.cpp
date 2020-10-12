# include "MyStack.h"

using namespace std;

MyStack::MyStack(){
    this->size = 2;
    this->inuse = 0;
    this->arr = new int[size];
}

MyStack::~MyStack(){
    delete[] this->arr;
}

void MyStack::reset(){
    delete this->arr;
    this->inuse = 0;
    this->size = 2;
    this->arr = new int[size];
}

bool MyStack::isEmpty(){
    if(inuse == 0){
        return true;
    }
    else{
        return false;
    }
}

void MyStack::push(int value){
    if(inuse == size){ // dynamically grow the array
        int* temp = new int[size+2];
        for(int i = 0; i < size; i++){
            temp[i] = arr[i];
        }
        delete[] this->arr;
        this->size = this->size + 2;
        this->arr = temp;
    }
    arr[inuse] = value;
    inuse++;
}

void MyStack::pop(){
    inuse--;
}

int MyStack::top(){
    return arr[inuse-1];
}
