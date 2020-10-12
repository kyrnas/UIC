#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "fastFoodRest.h"

void doAdd (node** hd)
{
 /* get number of burgers ordered from input */
 int NumBurgers = getPosInt();
 if (NumBurgers < 0)
   {
    printf ("Error: Add command requires an integer value of at least 0\n");
    printf ("Add command is of form: a <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }

  /* get number of salads ordered from input */
 int NumSalads = getPosInt(); 
 if (NumSalads < 0)
   {
    printf ("Error: Add command requires an integer value of at least 0\n");
    printf ("Add command is of form: a <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }  

 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Add command requires a name to be given\n");
    printf ("Add command is of form: a <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }

 if(doesNameExist(*hd, name)){
   printf("The name is already on the waiting list\n");
   return;
 }
 printf ("Adding In-restaurant order for \"%s\": %d burgers and %d salads\n", name,NumBurgers ,NumSalads);
  
 // add code to perform this operation here
 addToList(hd, name, NumBurgers, NumSalads, YES);
}


void doCallAhead (node** hd)
{
   /* get number of burgers ordered from input */
 int NumBurgers = getPosInt();
 if (NumBurgers < 0)
   {
    printf ("Error: Call-ahead command requires an integer value of at least 0\n");
    printf ("Call-ahead command is of form: c <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }

  /* get number of salads ordered from input */
 int NumSalads = getPosInt(); 
 if (NumSalads < 0)
   {
    printf ("Error: Call-ahead command requires an integer value of at least 0\n");
    printf ("Call-ahead command is of form: c <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }  


 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Call-ahead command requires a name to be given\n");
    printf ("Call-ahead command is of form: c <# burgers> <# salads> <name>\n");
    printf ("  where:<# burgers> is the number of ordered burgers\n");
    printf ("        <# salads> is the number of ordered salads\n");
    printf ("        <name> is the name of the person putting the order\n");
    return;
   }

 if(doesNameExist(*hd, name)){
   printf("The name is already on the waiting list\n");
   return;
 }

 printf ("Adding Call-ahead order for \"%s\": %d burgers and %d salads\n", name,NumBurgers ,NumSalads);

 // add code to perform this operation here
 addToList(hd, name, NumBurgers, NumSalads, NO);
}

void doWaiting (node* hd)
{
 /* get order name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Waiting command requires a name to be given\n");
    printf ("Waiting command is of form: w <name>\n");
    printf ("  where: <name> is the name of the group that is now waiting\n");
    return;
   }

 // add code to perform this operation here

 if(!updateStatus(hd, name)){
   printf("Status was not updated\n");
   return;
 }
 else{
   printf ("Call-ahead order \"%s\" is now waiting in the restaurant\n", name);
 } 
}

void doRetrieve (node** hd)
{
 /* get info of prepared food ready on the counter from input */
 int PreparedBurgers = getPosInt();
 if (PreparedBurgers < 0)
   {
    printf ("Error: Retrieve command requires an integer value of at least 0\n");
    printf ("Retrieve command is of form: r <# burgers> <# salads>\n");
    printf ("  where: <# burgers> is the number of burgers waiting on the counter for pick up\n");
    printf ("         <# saladss> is the number of salads waiting on the counter for pick up\n");
    return;
   }

 int PreparedSalads = getPosInt();
 if (PreparedSalads < 0)
   {
    printf ("Error: Retrieve command requires an integer value of at least 0\n");
    printf ("Retrieve command is of form: r <# burgers> <# salads>\n");
    printf ("  where: <# burgers> is the number of burgers waiting on the counter for pick up\n");
    printf ("         <# saladss> is the number of salads waiting on the counter for pick up\n");
    return;
   }   

 clearToEoln();
 printf ("Retrieve (and remove) the first group that can pick up the order of %d burgers and %d salads\n", PreparedBurgers ,PreparedSalads);

 // add code to perform this operation here
 char* pickUpName;
 pickUpName = retrieveAndRemove(hd, PreparedBurgers, PreparedSalads);
 printf("Pick up for %s is ready. Thank You!", pickUpName);
}

void doList (node* hd)
{
 /* get order name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: List command requires a name to be given\n");
    printf ("List command is of form: l <name>\n");
    printf ("  where: <name> is the name of the order to inquire about\n");
    return;
   }

 int noOrdersAhead = countOrdersAhead(hd, name);
 printf ("There are %d order(s) ahead. ", noOrdersAhead);
 printf ("Order for \"%s\" is behind the following orders\n", name);

 // add code to perform this operation here
 displayOrdersAhead(hd, name);
}
 
void doDisplay (node* hd)
{
 clearToEoln();
 printf ("Display information about all orders\n");

 // add code to perform this operation here
 displayListInformation(hd);
}


void doEstimateTime(node* hd)
{
  /* get order name from input */
 
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: List command requires a name to be given\n");
    printf ("List command is of form: t <name>\n");
    printf ("  where: <name> is the name of the order to inquire about\n");
    return;
   }


  // add code to perform this operation here  
  int waitTime = displayWaitingTime(hd, name);
  printf("The estimated wait time for %s is %d minutes\n", name, waitTime);
}