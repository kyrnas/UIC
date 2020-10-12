#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

int DebugMode;

typedef struct mazeStruct
{
 char** arr;  /* allows for a maze of any size plus outer walls */
 int xsize, ysize;
 int xstart, ystart;
 int xend, yend;
} maze;

//
// The stack that takes in two int variables
//
typedef struct Stack{
	int** arr;
	int size;
	int top;
}stack;

//
// initializes a stack
// has an initial size of four
// allocated the array is allocated on the heap
//
stack* init(){
	stack* myStack = (stack*)malloc(sizeof(stack));
	myStack->size = 4;
	myStack->top = -1;
	myStack->arr = (int**)malloc(sizeof(int*)*4);
	int i;
	for (i = 0; i < myStack->size; i++){
		myStack->arr[i] = (int*)malloc(sizeof(int) * 2);
	}
	return myStack;
}

//
// checks if the stack is empty.
// returns ture if empty
// false if not empty
//
int isEmpty(stack* myStack){
	if(myStack->top == -1)
		return TRUE;
	else
		return FALSE;
}

//
// pushes x and y coordinate couple onto a stack
// increases a stack by four each time max capaicity is reached
// use -d in command promt to track the growing and inserting
//
void push(stack* myStack, int ypos, int xpos){
	if(myStack->size - 1 == myStack->top){
		int** tmp;
		tmp = myStack->arr;
		if(DebugMode == TRUE){
			printf("Growing the stack. Old size %d. New size %d.\n", myStack->size, myStack->size+4);
		}
		myStack->arr = (int**)malloc(sizeof(int*)*(myStack->size + 4));
		
		int i;
		int j;
		for (i = 0; i < myStack->size + 4; i++){
			myStack->arr[i] = (int*)malloc(sizeof(int) * 2);
		}	
		for (i = 0; i < myStack->size; i++){
			for (j = 0; j < 2; j++){
				myStack->arr[i][j] = tmp[i][j];
			}
		}
		
		for (i = 0; i <myStack->size; i++){
			free(tmp[i]);
		}	
		free(tmp);

		if(DebugMode == TRUE){
			printf("No. of elements copied: %d\n", i);
		}

		myStack->size = myStack->size + 4;
	}

	myStack->top = myStack->top + 1;
	myStack->arr[myStack->top][0] = ypos;
	myStack->arr[myStack->top][1] = xpos;

	if(DebugMode == TRUE)
		printf("%d, %d pushed to the stack\n", ypos, xpos);
}

//
// removes he top element from the stack
// by decreasing the top index value
//
void pop(stack* myStack){
	if(isEmpty(myStack)){
		if(DebugMode == TRUE){
			printf("Stack is empty, nothing to pop\n");
		}
	}
	else{
		if(DebugMode == TRUE)
			printf("%d, %d is poped of the stack\n", myStack->arr[myStack->top][0], myStack->arr[myStack->top][1]);

		//free(myStack->arr[myStack->top]);
		myStack->top = myStack->top - 1;
	}
}

//
// returns an array with two integers (y, x)
// returns zero if the stack is empty
// use -d in command prompt for more informaton
//
void Top(stack* myStack, int* x, int* y){
	if(isEmpty(myStack)){
		if(DebugMode == TRUE){
			printf("Stack is empty, nothing to return\n");
		}
		return;
	}
	else{
		if(DebugMode == TRUE)
			printf("Returning top\n");

		*x = myStack->arr[myStack->top][0];
		*y = myStack->arr[myStack->top][1];
	}
}

//
// clears the stack
// freeing the memory
// sets the size to zero
// sets top to negative one
//
void clear(stack* myStack){
	free(myStack->arr);
	free(myStack);

	myStack->size = 0;
	myStack->top = -1;
}

//
// the algorithm for traversing the maze
// (Right, Down, Left, Up)
// returns the stack with coordinates
// the stack step by step shows the path
// from end to finish
// (needs to be reversed)
//
stack* depthFirstSearch(maze m){
	// create and initiate the stack to return
	stack* path = init();
	// push the start onto the stack
	push(path, m.xstart, m.ystart);
	// set the start position as visited
	m.arr[m.ystart][m.xstart] = '*';
	// create a cur varible to keep track of where we are currnetly in the maze
	int cur[2];
	// initialize cur to start
	cur[0] = m.ystart;
	cur[1] = m.xstart;
	// iterate until the stack is empty (there is nowhere to go)
	while(!isEmpty(path)){
		if(DebugMode){
			printf("Cur: %d, %d\n", cur[0], cur[1]);
		}
		// if reached the end return
		if(cur[0] == m.xend && cur[1] == m.yend){
			return path;
		}
		// go right if can
		else if(m.arr[cur[0]][cur[1]+1] != '*'){
			// push the cur coordinates onto the stack
			push(path, cur[0], cur[1]+1);
			// update cur to go right
		       	cur[1] = cur[1] + 1;
			// set cur as visited
			m.arr[cur[0]][cur[1]] = '*';
		}
		// go down if can
		else if(m.arr[cur[0]+1][cur[1]] != '*'){
			// push the cur coordinates onto the stack
			push(path, cur[0]+1, cur[1]);
			// update cur to go down
			cur[0] = cur[0] + 1;
			// set cur as visited
			m.arr[cur[0]][cur[1]] = '*';
		}
		// go left if can
		else if(m.arr[cur[0]][cur[1]-1] != '*'){
			push(path, cur[0], cur[1]-1);
			cur[1] = cur[1] - 1;
			m.arr[cur[0]][cur[1]] = '*';
		}
		// go up if can
		else if(m.arr[cur[0]-1][cur[1]] != '*'){
			push(path, cur[0]-1, cur[1]);
			cur[0] = cur[0] - 1;
			m.arr[cur[0]][cur[1]] = '*';
		}
		// if we cant go anywhere, go back
		else{
			pop(path);
			// set cur one back
			if(!isEmpty(path)){
				int x;
				int y;
				Top(path, &x, &y);
				cur[0] = x;
				cur[1] = y;
			}
		}
	}
	return path;
}

int main (int argc, char **argv)
{
  maze m1;

  int xpos, ypos;
  int i,j;

  m1.xsize = 0;
  m1.ysize = 0;
  m1.xstart = 0;
  m1.ystart = 0;
  m1.xend = 0;
  m1.yend = 0;

  FILE *src;

  DebugMode = FALSE;
  for (i=0; i <argc; i++){
	  if(strcmp(argv[i], "-d")==0){
		  DebugMode = TRUE;
	  }
  }

  /* verify the proper number of command line arguments were given */
  if(argc < 2) {
     printf("Usage: %s <input file name>\n", argv[0]);
     exit(-1);
  }
  else if(argc > 2 && DebugMode == FALSE){
     printf("The command line contains more names than expected, evaluating only first case.\n");
  }
  else if(argc == 2 && DebugMode == TRUE){
     printf("Usage: %s <input file name>\n", argv[0]);
     exit(-1);
  }
  else if(argc > 3 && DebugMode == FALSE){
     printf("The command line contains more names than expected, evaluating only first case.\n");
  }
   
  /* Try to open the input file. */
  if ( ( src = fopen( argv[1], "r" )) == NULL )
  {
	  if (( src = fopen(argv[2], "r" )) == NULL){
   		printf ( "Can't open input file: %s", argv[1] );
    		exit(-1);
	  }
  }

  /* read in the size, starting and ending positions in the maze */
  while (fscanf (src, "%d %d", &m1.xsize, &m1.ysize) != EOF){
	  if(m1.xsize > 1){ // check x
		  if(m1.ysize > 1){ // check y
			break;
		  }
	  }
  }
  while (fscanf (src, "%d %d", &m1.xstart, &m1.ystart) != EOF){
	if(m1.xstart > 0 && m1.xstart < m1.xsize){ // check x first
		if(m1.ystart > 0 && m1.ystart < m1.ysize){ // check y second
			break;
		}
		else{
			if(DebugMode)
			printf("Start out of bounds\n");
		}
	}
	else{
		if(DebugMode)
		printf("Start out of bounds\n");
	}
  }
  while (fscanf (src, "%d %d", &m1.xend, &m1.yend) != EOF){
	if(m1.xend > 0 && m1.xend < m1.xsize + 2){
		if(m1.yend > 0 && m1.yend < m1.ysize + 2){
			break;
		}
		else{
			if(DebugMode)
			printf("End out of bounds\n");
		}
	}
	else{
		if(DebugMode)
		printf("End out of bounds\n");
	}
  }

  if(m1.xsize == 0 || m1.ysize == 0 || m1.xstart == 0 || m1.ystart == 0 || m1.xend == 0 || m1.yend == 0){
  	 printf("Invalid input file. Not enough lines in txt file.\n");
  	 exit(-1);	
  }

  /* print them out to verify the input */
  printf ("size: %d, %d\n", m1.xsize, m1.ysize);
  printf ("start: %d, %d\n", m1.xstart, m1.ystart);
  printf ("end: %d, %d\n", m1.xend, m1.yend);

  m1.arr = (char**)malloc(sizeof(char*) * (m1.xsize + 2));

  for(i = 0; i < m1.xsize+2; i++){
	m1.arr[i] = (char*)malloc(sizeof(char) * (m1.ysize + 2));
  }

  
  /* initialize the maze to empty */
  for (i = 0; i < (m1.xsize+2); i++){
     for (j = 0; j < (m1.ysize+2); j++){
        m1.arr[i][j] = '.';
     }
  }


  /* mark the borders of the maze with *'s */
  for (i=0; i < m1.xsize+2; i++)
    {
     m1.arr[i][0] = '*';
     m1.arr[i][m1.ysize+1] = '*';
    }
  for (i=0; i < m1.ysize+2; i++)
    {
     m1.arr[0][i] = '*';
     m1.arr[m1.xsize+1][i] = '*';
    }

  
  /* mark the starting and ending positions in the maze */
  m1.arr[m1.xstart][m1.ystart] = 's';
  m1.arr[m1.xend][m1.yend] = 'e';
		  
  /* mark the blocked positions in the maze with *'s */
  while (fscanf (src, "%d %d", &xpos, &ypos) != EOF)
    {
     if(xpos > m1.xsize || xpos < 1 || ypos > m1.ysize || ypos < 1){
	if(DebugMode)
	printf("Point %d, %d is out of bounds. Ignoring\n", xpos, ypos); 
     }
     else if(xpos == m1.xstart && ypos == m1.ystart){
	if(DebugMode)
	printf("Cannot block the start\n");
     }
     else if(xpos == m1.xend && ypos == m1.yend){
	if(DebugMode)
	printf("Cannot block the end\n");
     }
     else{
        m1.arr[xpos][ypos] = '*';
     }
    }

  /* print out the initial maze */
  for (i = 0; i < m1.xsize+2; i++)
    {
     for (j = 0; j < m1.ysize+2; j++)
       printf ("%c", m1.arr[i][j]);
     printf("\n");
    }

    fclose(src);

    // run the algorithm for finding a path in maze
    stack* result = depthFirstSearch(m1);
    // prepare to reverse the result path
    stack* reverseResult = init();

    // if the returned stack is empty the maze has no solutions
    if(isEmpty(result)){
	printf("Maze has no solutions\n");
    }
    // if maze has a solution
    else{
	// push the coordinates from result to reverseResult
	while(!isEmpty(result)){
		int x;
		int y;
		Top(result, &x, &y);
		pop(result);
		push(reverseResult, x, y);
	}
	// print out the path from start to finish
	printf("The final path is: \n");
	while(!isEmpty(reverseResult)){
		int x;
		int y;
		Top(reverseResult, &x, &y);
		pop(reverseResult);
		printf("%d, %d\n", x, y);
	}
    }

   free(result);
   free(reverseResult);

    for(i = 0; i < m1.xsize+2; i++){
	free(m1.arr[i]);
    }

    free(m1.arr);
}
