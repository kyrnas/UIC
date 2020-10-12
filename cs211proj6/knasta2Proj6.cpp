#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <sstream>

#include "Island.h"

bool DebugMode = false;

class ArchipelagoExpedition
{
 private:
   // Create the Data Members for the Archipelago Network here
   Island* arr;
   int size;
   MyList files;
  
 public:
  
 // Use a constructor to initialize the Data Members for your expedition
 ArchipelagoExpedition()
 {
   size = 11;
   arr = new Island[size];
 }

 void resetFiles(){
   files.~MyList();
   files.init();
 }
  
  
 // The main loop for reading in input
 void processCommandLoop (FILE* inFile)
 {
  char  buffer[300];
  char* input;

  input = fgets ( buffer, 300, inFile );   // get a line of input
    
  // loop until all lines are read from the input
  while (input != NULL)
  {
    // process each line of input using the strtok functions 
    char* command;
    command = strtok (input , " \n\t");

    //printf ("*%s*\n", command);
    
    if ( command == NULL )
      printf ("Blank Line\n");
 
    else if ( strcmp (command, "q") == 0) 
      exit(1);
     
    else if ( strcmp (command, "?") == 0) 
      showCommands();
     
    else if ( strcmp (command, "t") == 0) 
      doShortestPath();
     
    else if ( strcmp (command, "r") == 0) 
      doResize();

    else if ( strcmp (command, "i") == 0) 
      doInsert();

    else if ( strcmp (command, "d") == 0) 
      doDelete();

    else if ( strcmp (command, "l") == 0)
      doList();

    else if ( strcmp (command, "f") == 0) 
      doFile();

    else if ( strcmp (command, "#") == 0) 
      ;
     
    else
      printf ("Command is not known: %s\n", command);
     
    input = fgets ( buffer, 300, inFile );   // get the next line of input
  }
 }
 
 void showCommands()
 {
   printf ("The commands for this project are:\n");
   printf ("  q \n");
   printf ("  ? \n");
   printf ("  # \n");
   printf ("  t <int1> <int2> \n");
   printf ("  r <int> \n");
   printf ("  i <int1> <int2> \n");
   printf ("  d <int1> <int2> \n");
   printf ("  l \n");
   printf ("  f <filename> \n");
 }
 
 void doShortestPath ( ) 
 {
   int val1 = 0;
   int val2 = 0;

   // get an integer value from the input
   char* next = strtok (NULL, " \n\t");
   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val1 = atoi ( next );
   if ( val1 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }
   
   // get another integer value from the input
   next = strtok (NULL, " \n\t");

   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val2 = atoi ( next );
   if ( val2 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }
   
   
   printf ("Performing the Travel Command from %d to %d\n", 
            val1, val2);

   MyList path = breadthFirstSearch(val1, val2);
   path.show();
 }
 
 void doResize()
 {
  // first determine the size of new array
  int val1 = 0;
  
  char* next = strtok (NULL, " \n\t");
  val1 = atoi(next);

  if(val1 > 100 || val1 < 1){
    printf("Invalid size\n");
    return;
  }

  if(DebugMode)  
    printf ("Performing the Resize Command with %d\n", val1);
  
  delete[] arr;

  size = val1+1;
  arr = new Island[size];
 }
 
 void doInsert()
 {
   int val1 = 0;
   int val2 = 0;

   // get an integer value from the input
   char* next = strtok (NULL, " \n\t");
   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val1 = atoi ( next );
   if ( val1 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }
   
   // get another integer value from the input
   next = strtok (NULL, " \n\t");

   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val2 = atoi ( next );
   if ( val2 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }

   if (val2 >= size || val1 >= size){
     printf("Out of bounds\n");
     return;
   }
   if(DebugMode)
    printf("Inserting a new egde from %d to %d\n", val1, val2);
  
   MyList* newList = arr[val1].getList();
   newList->insert(val2);
   
 }
 
 void doDelete()
 {
   int val1 = 0;
   int val2 = 0;

   // get an integer value from the input
   char* next = strtok (NULL, " \n\t");
   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val1 = atoi ( next );
   if ( val1 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }
   
   // get another integer value from the input
   next = strtok (NULL, " \n\t");

   //printf("%s\n", next);
   if ( next == NULL )
   {
     printf ("Integer value expected\n");
     return;
   }
   val2 = atoi ( next );
   if ( val2 == 0 && strcmp (next, "0") != 0)
   {
     printf ("Integer value expected\n");
     return;
   }

   if (val2 >= size || val1 >= size){
     printf("Out of bounds\n");
     return;
   }
   if(DebugMode)
    printf("Deleting an egde from %d to %d\n", val1, val2);

   MyList* newList = arr[val1].getList();
   if(!newList->search(val2)){
     printf("Edge not found\n");
     return;
   }
   newList->remove(val2);
   
 }
 
 void doList()
 {
   printf("Displaying all possible trips by island:\n");
   for(int i = 1; i < size; i++){
     printf("Island %d: ", i);
     MyList* cur = arr[i].getList();
     cur->show();
   }
   printf("\n");
   
 }
 
 void doFile()
 {
   // get a filename from the input
   char* fname = strtok (NULL, " \r\n\t");
   if ( fname == NULL )
   {
     printf ("Filename expected\n");
     return;
   }
   if(DebugMode)
    printf ("Performing the File command with file: %s\n", fname);
  
   int filename = 5381;
   for(int i = 0; i < fname[i] != '\0'; i++){
    filename = (filename * 33) + fname[i];
   }
   
   // next steps:  (if any step fails: print an error message and return ) 
   //  1. verify the file name is not currently in use
   if(files.search(filename)){ 
     printf("File already in use...\n");
     return;
   }
   //  2. open the file using fopen creating a new instance of FILE*
   files.push_stack(filename);
   FILE* newFile = fopen(fname, "r");
   if(newFile == NULL){
     printf("Could not open file\n");
     files.remove(filename);
     return;
   }
   //  3. recursively call processCommandLoop() with this new instance of FILE* as the parameter
   processCommandLoop(newFile);
   //  4. close the file when processCommandLoop() returns
   fclose(newFile);
   files.remove(filename);
  
   // just a precaution
   if(files.isEmpty()){
     resetFiles();
   }
 }

  MyList breadthFirstSearch(int x, int y){
    for(int i = 1; i < size; i++){
      arr[i].setVisited(false);
      arr[i].setPrev(-1);
    }
    MyList pathList;
    MyList IslandQueue;
    IslandQueue.push_queue(x);
    if(!bfs(y, IslandQueue)){
      printf("You can NOT get from island %d to island %d in one or more ferry rides\n", x, y);
      return pathList;
    }
    else{
      printf("You can get from island %d to island %d in one or more ferry rides\n", x, y);
      Island cur = arr[y];
      int curInt = y;
      pathList.addToFront(y);
      while(curInt != x){
        pathList.addToFront(cur.getPrev());
        curInt = cur.getPrev();
        cur = arr[cur.getPrev()];
      }
      Island garbage;
      cur = garbage;
      return pathList;
    }
  }

 bool bfs (int b, MyList& IslandQueue){
   while(!IslandQueue.isEmpty()){
    int a = IslandQueue.pop_queue();
    for(int i = 0; i < arr[a].getList()->getListLength(); i++){
      int c = arr[a].getList()->getNthElem(i);
      if(!arr[c].getVisited()){
        arr[c].setVisited(true);
        arr[c].setPrev(a);
        if(c == b){
          return true;
        }
        IslandQueue.push_queue(c);
      }
    }
   }
   return false;
 }
};// end class


int main (int argc, char** argv)
{
  for(int i=0; i < argc; i++){
	 if(strcmp(argv[i], "-d")==0){
	  DebugMode = true;
   }
	}

  // set up the variable inFile to read from standard input
  FILE* inFile = stdin;

  // set up the data needed for the island adjcency list
  ArchipelagoExpedition islandData;
   
  // call the method that reads and parses the input
  islandData.showCommands();
  printf ("\nEnter commands at blank line\n");
  printf ("    (No prompts are given because of f command)\n");
  islandData.processCommandLoop (inFile);

  printf ("Goodbye\n");
  return 1;
 }
