using namespace std;

class MyStack{
private:
    int size;
    int inuse;
    int* arr;

public:
    //
    // defualt constructor
    //
    MyStack();

    //
    // destructor
    //
    ~MyStack();

    void reset();
    bool isEmpty();
    void push(int value);
    void pop();
    int top();
};