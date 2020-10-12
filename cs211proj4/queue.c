#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "fastFoodRest.h"

// THE STRUCT IS DEFINED IN HEADER FILE

boolean doesNameExist(node* hd, char* searchName);

//
// this create a new node with given name and numbers
// the status is set to "YES" by default
//
node* newNode(char* Name, int burgers, int salads){
	node* tmp = (node*)malloc(sizeof(node));
	tmp->name = Name;
	tmp->noBurgers = burgers;
	tmp->noSalads = salads;
	tmp->status = YES;
	tmp->next = NULL;

	return tmp;
}

//
// this pushes the given information to the back of a queue, crating a new node
//
void addToList(node** head, char* Name, int burgers, int salads, boolean newStatus){
	if(doesNameExist(*head, Name) == TRUE){ // check if the name already exists
		printf("The name %s is already in the queue\n", Name);
		return;
	}
	// create a new node with given information
	node* tmp = newNode(Name, burgers, salads);
	// if the queue is empty insert at the front updating the head
	if(*head == NULL){
		*head = tmp;
		if(newStatus == NO){
			node* cur = *head;
			cur->status = NO;
		}
		return;
	}
	// iterate through the queue untill reach the end
	node* cur = *head;
	while(cur->next != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		cur = cur->next;
	}
	if(newStatus == NO){ // check if its a call ahead order
		tmp->status = NO; 
	}
	cur->next = tmp;
}

//
// checks the queue for given name
// if the name is already in the queue returns true
// else returns false
//
boolean doesNameExist(node* hd, char* searchName){
	node* cur = hd;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG > doesNameExist > Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		if(strcmp(cur->name, searchName) == 0){
			return TRUE;
		}
		else{
			cur = cur->next;
		}
	}
	return FALSE;
}

//
// finds the given name in the queue and changes the status to yes
// returns true if the status was changed
// if the name is not found returns false and error message
// if the order is already set to yes returns false and error message
//
boolean updateStatus(node* hd, char* Name){
	if(!doesNameExist(hd, Name)){
		printf("No %s on the call-ahead list\n", Name);
		return FALSE;
	}
	node* cur = hd;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		if(strcmp(cur->name, Name) == 0){
			if(cur->status == YES){
				printf("The order for %s is aldeady set for \"waiting\"\n", Name);
				return FALSE;
			}
			else{
				cur->status = YES;
				return TRUE;
			}
		}
		cur = cur->next;
	}
	return FALSE;
}

//
// this removes the first node that fist the description (noBurgers and noSalads)
// from the queue, updating the next pointers
// returns the name that has to pick up the order
//
char* retrieveAndRemove(node** hd, int burgers, int salads){
	node* cur = *hd;
	node* prev = NULL;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		if(cur->noBurgers <= burgers && cur->noSalads <= salads && cur->status == YES){ // if the order mathces
			if(prev == NULL){ // if we remove the head of the list
				*hd = cur->next; // change the head of the linked list
			}
			else{
				prev->next = cur->next; // link prev to point to node after cur
			}
			return cur->name;
		}
		else{ // if the order doesn't match
			prev = cur;
			cur = cur->next;
		}
	}
	printf("No matching order for %d burgers and %d salads\n", burgers, salads);
	return NULL;
}

//
// counts the orders ahead of the given name
//
int countOrdersAhead(node* hd, char* searchName){
	int count = 0;
	node* cur = hd;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		if(strcmp(cur->name, searchName) == 0){
			return count;
		}
		else{
			count++;
			cur = cur->next;
		}
	}
	return -1; // name not found
}

//
// calculatetes the estimated time until the person
// with given name gets their order
//
int displayWaitingTime(node* hd, char* searchName){
	int time = 0;
	node* cur = hd;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		time += (cur->noBurgers * 10);
		time += (cur->noSalads * 5);
		if(strcmp(cur->name, searchName) == 0){
			return time;
		}
		else{
			cur = cur->next;
		}
	}
	return -1; // name not found
}

//
// this prints out the orders ahead of the certain name
//
void displayOrdersAhead(node* hd, char* searchName){
	node* cur = hd;
	if(!doesNameExist(hd, searchName)){
		printf("Name \"%s\" is not on the List\n", searchName);
		return;
	}
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		if(strcmp(cur->name, searchName) == 0){
			return;
		}
		else{
			printf("Order for %s: %d burgers, %d salads\n", cur->name, cur->noBurgers, cur->noSalads);
			cur = cur->next;
		}
	}
}

//
// prints out all the orders in the queue
//
void displayListInformation(node* hd){
	node* cur = hd;
	while(cur != NULL){
		if(DebugMode == TRUE){
			printf("DEBUG> Name: %s, Burgers: %d, Salads %d\n", cur->name, cur->noBurgers, cur->noSalads);
		}
		printf("Order for %s: %d burgers, %d salads, status: ", cur->name, cur->noBurgers, cur->noSalads);
		if(cur->status == YES){
			printf("waiting\n");
		}
		else{
			printf("call ahead, not in the restaurant\n");
		}
		cur = cur->next;
	}
}