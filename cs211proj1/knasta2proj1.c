#include <stdio.h>
#include <stdlib.h>


// this is a dynamically growing array
typedef struct DynamicArray{
 int *darr; // pointer to location on the heap
 int allocated; // keeps track of the size
 int inUse; // keeps track of number of data points
}dyarr;


void insertValue(dyarr *da, int value);
void makeArrayCopy(dyarr *fromArray, dyarr *toArray, int size);
void swap (int *a, int *b);
void bubbleSort(dyarr *arr, int size);
int TwoSumFunction(dyarr arr, int target, int *low, int *high);

int main (int argc, char** argv)
{
 int val;
 // create a dynamically growing array of size 10
 dyarr arr1;
 arr1.darr = (int*)malloc(sizeof(int)*10);
 arr1.allocated = 10;
 arr1.inUse = 0;

 /* prompt the user for input */
 printf ("Enter in a list of numbers ito be stored in a dynamic array.\n");
 printf ("End the list with the terminal value of -999\n");
 
 /* loop until the user enters -999 */
 scanf ("%d", &val);
 while (val != -999)
   {
    /* store the value into an array */
    insertValue(&arr1, val);
    /* get next value */
    scanf("%d", &val);
   }

 /* call function to make a copy of the array of values */
 // create a new array 
 dyarr arr2;
 //arr2.darr = (int*)malloc(sizeof(int)*arr1.allocated);
 //arr2.allocated = arr1.allocated;
 //arr2.inUse = arr1.inUse;
 makeArrayCopy(&arr1, &arr2, arr1.allocated);
 /* call function to sort one of the arrays */
 bubbleSort(&arr2, arr2.inUse);

 /* loop until the user enters -999 */
 printf ("Enter in a list of numbers to use for searching.  \n");
 printf ("End the list with a terminal value of -999\n");
 scanf ("%d", &val);
 while (val != -999)
   {
    /* call function to perform target sum operation */
    int low;
    int high;
    int result;
    result = TwoSumFunction(arr2, val, &low, &high);
    /* print out info about the target sum results  */
    printf("The target value is: %d\n", val);
    if(result == 1){
     printf("The Two Sum evaluation was successful.\n");
     printf("The lower index is %d. The higher index is %d.\n", low, high);
    }
    else{
     printf("The Two Sum Evaluation was not succesful.\n");
    }
   

    /* get next value */
    scanf("%d", &val);
   }


 printf ("Good bye\n");
 return 0;
}


// this function inserts a new value ino a given dynamic array, and increases its capacity if there in not enough space for new insertion
void insertValue(dyarr *da, int value){
 // check if we have space to inser a new element
 if (da->inUse >= da->allocated){
  // set temp to refer to the location of old array
  int *tmp;
  tmp = da->darr;

  // create a bigger array and copy in the elements from older array, then free the memory and update the size of a new array
  da->darr  = (int*)malloc(sizeof(int)*(da->allocated)*2);
  //makeArrayCopy(&tmp, &da, tmp->allocated);
  for (int i=0; i<da->allocated; i++){
   da->darr[i] = tmp[i];
  }
  free(tmp);
  da->allocated = da->allocated * 2;

 }
 da->darr[da->inUse] = value;
 da->inUse++;
}

// creates a copy of a given array
void makeArrayCopy(dyarr *fromArray, dyarr *toArray, int size){
 toArray->darr = (int*)malloc(sizeof(int)*fromArray->allocated);
 toArray->allocated = fromArray->allocated;
 toArray->inUse = fromArray->inUse;
 for (int i=0; i < size; i++){
  toArray->darr[i] = fromArray->darr[i];
 } 
}

// swaps two elements in array. Created to use in bubbleSort
void swap(int *a, int *b){
 int temp = *a;
 *a = *b;
 *b = temp;
}

// sorts the array in ascending order
void bubbleSort(dyarr *arr, int size){
 for(int i = 0; i < size-1; i++){
  for(int j=0; j < size-i-1; j++){
   if(arr->darr[j] > arr->darr[j+1]){
    swap(&arr->darr[j], &arr->darr[j+1]);
   }
  }
 }
}

// finds if two elements in given array can be sumed to a target value
// returns "1" if there are 2 elements that add up to target 
// returns "-1" if there are no such 2 elements that add up to target
int TwoSumFunction (dyarr arr, int target, int *low, int *high){
 *low = 0;
 *high = arr.inUse - 1;
 
 // keeps runing until low and high are at the same index
 while(*low != *high){
  if((arr.darr[*low] + arr.darr[*high]) == target){
   return 1;
  }
  else if((arr.darr[*low] + arr.darr[*high]) > target){
   *high = *high -1;
  }
  else if((arr.darr[*low] + arr.darr[*high]) < target){
   *low = *low + 1;
  }
 }
 return -1;
}

