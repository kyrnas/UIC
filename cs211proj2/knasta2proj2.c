#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

int DebugMode;

//
// The stack data structure
//
typedef struct Stack{
	char* arr;
	int size;
	int top;
}stack;

//
// initializes the stack of size four, on the heap
//
stack* init(){
	stack* myStack = (stack*)malloc(sizeof(stack));
	myStack->size = 4;
	myStack->top = -1;
	myStack->arr = (char*)malloc(sizeof(char) * 4);
	return myStack;
}

//
// checks if the stack is empty. Returns TRUE if empty, or FALSE if not empty
//
int isEmpty(stack* myStack){
	if(myStack->top == -1)
		return 1;
	else
		return 0;
}

//
// pushes a new elemet on top of the stack. If the stack is full grows it by four.
//
void push(stack* myStack, char val){
	if(myStack->size - 1  == myStack->top){
		char* tmp;
		tmp = myStack->arr;

		if(DebugMode == TRUE){
			printf("Growing the stack. Old size %d. New size %d. ", myStack->size, myStack->size + 4);
		}

		myStack->arr = (char*)malloc(sizeof(char)*(myStack->size + 4));

		int i;
		for (i = 0; i < myStack->size; i++){
			myStack->arr[i] = tmp[i];
		}
		free(tmp);
		if(DebugMode == TRUE){
			printf("No. of elements copied: %d\n", i);
		}
		myStack->size = myStack->size + 4;
	}
	myStack->top = myStack->top + 1;
	myStack->arr[myStack->top] = val;

	if(DebugMode == TRUE)
		printf("%c pushed to the stack\n", val);
}

//
// removes the top element from the stack.
//
void pop(stack* myStack){
	if(isEmpty(myStack)){
		if(DebugMode == TRUE)
			printf("Stack is empty, nothing to pop\n");
	}
	else{
		if(DebugMode == TRUE)
			printf("%c is poped of the stack\n", myStack->arr[myStack->top]);

		myStack->top = myStack->top - 1;
	}
}

//
// returns the character at the top of the stack
//
char top(stack* myStack){
	if(isEmpty(myStack)){
		if(DebugMode == TRUE){
			printf("Stack is empty, nothing to return\n");
		}
		return '#';
	}
	else
		return myStack->arr[myStack->top];
}

//
// clears the stack and frees the memory
//
void clear(stack* myStack){
	free(myStack->arr);
	free(myStack);
}

// END STACK METHODS


//
// this function takes in a string and checks if it is a valid word to decode
// or if it is meaningless and should be ignored
//
// returns TRUE or FALSE
//

int isValid(char *word){
	// create a stack that will be used to store chars from L1
	stack* myStack = init();
	int k;

	// iterate through each char in the string. If the char is from L1
	// push it to the top of the stach, if the char is from L2
	// and is mapepd to the char on top of the stack remove the char from the
	// top of the stack
	for(k=0; k<strlen(word); k++){

	if(word[k] == 'a')
		push(myStack, word[k]);
	else if(word[k] == 'b')
		push(myStack, word[k]);
	else if(word[k] == 'c')
		push(myStack, word[k]);
	else if(word[k] == 'd')
		push(myStack, word[k]);
	else if(word[k] == 'm' && top(myStack) == 'a')
		pop(myStack);
	else if(word[k] == 'n' && top(myStack) == 'b')
		pop(myStack);
	else if(word[k] == 'o' && top(myStack) == 'c')
		pop(myStack);
	else if(word[k] == 'p' && top(myStack) == 'd')
		pop(myStack);

	}

	// if the stack is empty than the word is valid, therefore return TRUE
	if(isEmpty(myStack))
		return TRUE;
	
	return FALSE;
}

//
// this takes in a string that is already confimed as valid
// and removes all the chars except for abcd, updating the string
//

void removeExtraLetters(char* word){
	int i;
	stack* myStack = init();
	// push the chars of interest on top of the stack
	for(i=0; i<strlen(word); i++){
		if(word[i] == 'a' || word[i] == 'b' || word[i] == 'c' || word[i] == 'd')
			push(myStack, word[i]);
	}
	// set the end of the string equal to \O cutting the unwanted elements off
	int cur = myStack->top;
	word[cur+1] = '\0';
	// going from back to front add the chars to the string, while poping
	// them from the stack
	for(cur; cur>=0; cur--){
		word[cur] = top(myStack);
		pop(myStack);
	}
}

int main(int argc, char const *argv[])
{

	// determine the condition for DebugMode
	int i;
	DebugMode = FALSE;
	for (i=0; i <argc; i++){
		if(strcmp(argv[i], "-d")==0){
			DebugMode = TRUE;
		}
	}
	
	char input[300];
	
	
	 /* set up an infinite loop */
 while (1)
 {
   /* get line of input from standard input */
   printf ("\nEnter input to check or q to quit\n");
   fgets(input, 300, stdin);

   /* remove the newline character from the input */
   i = 0;
   while (input[i] != '\n' && input[i] != '\0')
   {
      i++;
   }
   input[i] = '\0';


   /* check if user enter q or Q to quit program */
   if ( (strcmp (input, "q") == 0) || (strcmp (input, "Q") == 0) )
     break;
 
   printf ("%s\n", input);
   /*Start tokenizing the input into words separated by space
    We use strtok() function from string.h*/
   /*The tokenized words are added to an array of words*/
   
   char delim[] = " ";
   char *ptr = strtok(input, delim);
   int j = 0 ; 
   char *wordList[15];
   

	while (ptr != NULL)
	{	
		wordList[j++] = ptr;
		ptr = strtok(NULL, delim);

	}



   /*Run the algorithm to decode the message*/


	// check all words for validity and output them in the right
	// format if they are valid
	for(i=0; i<j; i++){
		char* word = wordList[i];
		if(isValid(word)){
			removeExtraLetters(word);
			printf("%s ", word);
		}
	}
 }

 printf ("\nGoodbye\n");





	return 0;
}
