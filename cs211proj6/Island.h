#include <cstdio>
#include <cstring>
#include <cstdlib>

#include "MyList.h"

using namespace std;

class Island{
private:
    MyList* AdjList;
    bool isVisited;
    int prevLoc;

public:
    Island();
    //
    // destructor
    //
    //~Island();

    //
    // getList
    //
    MyList* getList();
    //
    // getVisited
    //
    bool getVisited();

    int getPrev();

    //
    // setList
    //
    void setList(MyList* l);

    //
    // setVisited
    //
    void setVisited(bool val);

    void setPrev(int i);
};