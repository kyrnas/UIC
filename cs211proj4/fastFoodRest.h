#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef enum {FALSE = 0, TRUE, NO = 0, YES} boolean;

boolean DebugMode;

typedef struct Node{
	char* name;
	int noBurgers;
	int noSalads;
	boolean status;
	struct Node* next;
}node;

boolean doesNameExist(node* hd, char* searchName);
node* newNode(char* Name, int burgers, int salads);
void addToList(node** head, char* Name, int burgers, int salads, boolean newStatus);
boolean updateStatus(node* hd, char* Name);
char* retrieveAndRemove(node** hd, int burgers, int salads);
int countOrdersAhead(node* hd, char* searchName);
int displayWaitingTime(node* hd, char* searchName);
void displayOrdersAhead(node* hd, char* searchName);
void displayListInformation(node* hd);


void doAdd(node** hd);
void doCallAhead(node** hd);
void doWaiting(node* hd);
void doRetrieve(node** hd);
void doList(node* hd);
void doDisplay(node* hd);
void doEstimateTime(node* hd);

void clearToEoln();
int getNextNWSChar();
int getPosInt();
char* getName();
void printCommands();