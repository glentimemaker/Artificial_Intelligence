/*
*****************************PROGRAM GOAL*****************************
The robot can move from the start cell to a specified destination cell 
without hitting any obstacle, and to make as few moves as possible.
**********************************************************************

*/


#include<stdio.h>
#include<stdlib.h>
//The initial environment,setting a closed environment.
//The size of the major area where the robot can walk is 30*30.
//Randomly select some elements to be obstacles.
int m[32][32]={{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,1,1,0,0,0,1,0},
               {0,1,1,0,0,0,0,0,0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,0},
               {0,1,0,1,0,1,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,0,0},
               {0,1,1,1,1,0,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0},
               {0,1,0,0,0,1,1,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0},
			   {0,1,1,1,1,1,0,1,1,0,0,0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0},
               {0,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0},
               {0,1,0,0,1,1,1,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0},
               {0,1,1,0,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0,1,1,0},
               {0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0},
			   {0,1,1,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,0,0,1,1,1,0,1,1,0,1,1,1,1,0},
               {0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,0,1,1,1,1,0,0,0,0},
               {0,1,1,1,1,1,0,0,0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
               {0,1,0,0,0,0,0,0,1,1,1,0,0,0,1,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
               {0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0},
			   {0,1,1,1,1,0,0,0,0,0,1,1,1,0,1,1,1,1,0,0,0,0,1,0,0,1,1,0,1,1,1,0},
               {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0},
               {0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,1,0,0,0,0,1,1,1,1,0,1,1,1,0},
               {0,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,0,1,1,1,1,0,1,1,1,0},
               {0,1,0,1,1,1,1,1,1,1,0,0,0,1,1,1,0,1,1,1,1,0,0,0,1,1,1,0,0,1,1,0},
			   {0,1,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,0},
               {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,0,0,0,0,1,1,1,1,1,1,0,1,1,0},
               {0,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,1,1,0},
               {0,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,0,0,0},
               {0,1,1,1,1,1,1,0,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,0},
			   {0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,0,1,1,1,0},
               {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,0},
               {0,1,1,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,1,0,1,1,1,1,1,1,1,0},
               {0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,0,0},
               {0,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0,1,1,1,1,1,1,1,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

int visit1(int,int); //The move function of robot
int result(int,int);//Result function,display the moving route and the length of the route
int turn_function(int,int);//The function which records the rotation of robot
int Si,Sj,Ei,Ej;//(Si,Sj) is start point, (Ei,Ej) is end point
int success;//A return value
int stack_num;//The number refers to the num_th value of the data stack
int move;//The number of rotation robot makes
int ri[10000000],rj[10000000];//Data stack to record the movements of the robot
int min=10000;//The minimum steps the robot walks, to make sure the route is the shortest route
static int path=1;
int choice; //The value users can import to make the choice of the menu

// Main function
int main(){
    int row,col;
    Si=1;Sj=1;Ei=30;Ej=30;//Set the initial value
    success=0;
    stack_num=0; 
    
    printf("The robot moving program\n");
    printf("************\n");
    printf("Start point(1,1), Exit point(30,30)\n"); 
	//Print the map
    for(row=0;row<32;row++)
    {
       for(col=0;col<32;col++){
       if(m[row][col]==0)
          printf(" 0 ");
          else
			  printf(" 1 ");
        }//end for
     printf("\n");
	}//end for

	printf("...Menu...\n"
           "1.Run the robot!\n"
           "2.Exit\n"
           "Please select one option¡G");
    scanf("%d",&choice);
    
	//In the case chosing "Run the robot"
	if (choice==1)
	{
          printf("The plot of the route¡G\n");
          if(visit1(Si,Sj)==0){
          printf("Didn't find the exit,sorry\n");
          }
          printf("The step number:%d\n\n",min); 
          path=1; 
		  printf("...Menu...\n"
			  "1.Run the robot!\n"
			  "2.Exit\n"
			  "Please select one option¡G");
		  scanf("%d",&choice);
		  system("cls");   
	}
	else{system("cls");}

    printf("Bye bye!\n");
    system("pause");
    return 0;
    }
//Dispalay the final route
int result(int i,int j){
    printf("The moving route¡G\n");
	for(i=0;i<32;i++)
	{
		for(j=0;j<32;j++){
			if(m[i][j]==0)
				printf(" 0 ");//The obstacle
			else if(m[i][j]==2)
				printf(" 5 ");//The step
			else
				printf(" 1 ");//The left available point
		}//end for
		printf("\n");
	}//end for 
}

//The move function
int visit1(int i,int j){
    int count=0;
    int k; 
	int i_pre, j_pre;
	i_pre=i; j_pre=j;
    m[i][j]=2;//Set the present point to be one step
    ri[stack_num]=i;//Record the present point in the database of movement
    rj[stack_num]=j;
    
    stack_num++;//Point to the next term in the database
    
	//When the robot gets the exit point
    if(i==Ei && j==Ej){
		//Get the totall number of the movement
		for(k=0;k<stack_num;k++){
			count++;
		}
		//Print every position the robot has visited
		if(count<min){
			min=count;
			for(k=0;k<stack_num;k++){
				printf("(%d,%d)\n",ri[k],rj[k]);
			}
			printf("Total number of steps %d \n",count+move);
			result(i,j);//Print every step in the map
		}
		success=1;
    }
	
	//Use recursion to decide the next step or rotation
	//The initial orientation is the direction from the start point to the exit point
	
	//Situation 1: 
	//from initial orientation, the robot can detect the environment of 
	//3 orientations: m[i+1][j+1],m[i][j+1],m[i+1][j].
	//If there's no obstacle in either of these orientations,
	//The robot moves forward without detecting other environments of other orientations.
	if(m[i+1][j+1]==1||m[i][j+1]==1||m[i+1][j]==1)
	{
		if(m[i+1][j+1]==1){visit1(i+1,j+1);move++;}
		else if(m[i][j+1]==1){visit1(i,j+1);move=move+2;}
		else {visit1(i+1,j);move=move+2;}
	}

	//Situation 2:
	//There are obstacle in all the 3 orientations(m[i+1][j+1],m[i][j+1],m[i+1][j]),
	//The robot has to rotate to detect other environments.
	else{
			move=move+2;//Make the turn towards upper right
		if(m[i-1][j+1]==1|| m[i-1][j]==1 ){
			if(m[i-1][j+1]==1)
			{visit1(i-1,j+1);move++;}
			else 
			{visit1(i-1,j);move++;}
		}
		else{
			move=move+3;//Make the turn towards lower left
			if(m[i+1][j-1]==1)
			{visit1(i+1,j-1);move++;}
			else {
				if(m[i][j-1]==1)
				{visit1(i,j-1);move++;}
				else{
					//m[i][j]=0;
					if (i != Ei||j!= Ej){
					visit1(i-1,j-1);//Go back to the previous point
					m[i][j]=0;
					stack_num--;
					move++;
					}
					else visit1(i,j);
				}
			}
		}
	}
    m[i][j]=1; 
    return success;
}



