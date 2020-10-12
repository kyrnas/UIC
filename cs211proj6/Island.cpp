#include "Island.h"


//
// default constructor
//
Island::Island(){
    AdjList = new MyList();
    isVisited = false;
    prevLoc = -1;
}

//
// destructor
//
//Island::~Island(){
//   delete[] AdjList;
//}

//
// getList
//
MyList* Island::getList(){
    return AdjList;
}
//
// getVisited
//
bool Island::getVisited(){
    return isVisited;
}
//
// getPrev
//
int Island::getPrev(){
    return prevLoc;
}

//
// setList
//
void Island::setList(MyList* l){
    delete AdjList;
    AdjList = l;
}
//
// setVisited
//
void Island::setVisited(bool val){
    isVisited = val;
}
//
// setPrev
//
void Island::setPrev(int i){
    prevLoc = i;
}