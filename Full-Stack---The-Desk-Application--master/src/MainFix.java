import java.util.ArrayList;
import java.util.Scanner;

public class MainFix 
{
    public static void main(String[] args) 
    {
    	//Initial declaration should be declared outside of optionSelection so expenses isn't constantly reset
        ArrayList<Integer> expenses = new ArrayList<Integer>();
        expenses.add(1000);
        expenses.add(2300);
        expenses.add(45000);
        expenses.add(32000);
        expenses.add(110);
       
        System.out.println("\n**************************************\n");
        System.out.println("\tWelcome to TheDesk \n");
        System.out.println("**************************************");
        optionsSelection(expenses);
    }
    
    //ArrayList passed in to ensure that they are up to date
    private static void optionsSelection(ArrayList<Integer> expenses) 
    {
        String[] arr = {"1. I wish to review my expenditure",
                "2. I wish to add my expenditure",
                "3. I wish to delete my expenditure",
                "4. I wish to sort the expenditures",
                "5. I wish to search for a particular expenditure",
                "6. Close the application"};
        
        int[] arr1 = {1,2,3,4,5,6};
        int  slen = arr1.length;
        for(int i=0; i<slen;i++)
        {
            System.out.println(arr[i]);
            // display all the Strings mentioned in the String array
        }
        
        System.out.println("\nEnter your choice:\t");
        Scanner sc = new Scanner(System.in);
        int  options =  sc.nextInt();
        for(int j=1;j<=slen;j++)
        {
            if(options==j)
            {
                switch (options)
                {
                    case 1:
                        System.out.println("Your saved expenses are listed below: \n");
                        System.out.println(expenses+"\n");
                        optionsSelection(expenses);
                        break;
                    case 2:
                        System.out.println("Enter the value to add your Expense: \n");
                        int value = sc.nextInt();
                        expenses.add(value);
                        System.out.println("Your value is updated\n");
                        System.out.println(expenses+"\n");
                        optionsSelection(expenses);

                        break;
                    case 3:
                        System.out.println("You are about the delete all your expenses! \nConfirm again by selecting the same option...\n");
                        int con_choice = sc.nextInt();
                        if(con_choice==options)
                        {
                               expenses.clear();
                            System.out.println(expenses+"\n");
                            System.out.println("All your expenses are erased!\n");
                        } 
                        else 
                        {
                            System.out.println("Oops... try again!");
                        }
                        optionsSelection(expenses);
                        break;
                    case 4:
                        sortExpenses(expenses);
                        System.out.println("Your expenses have been sorted\n");
                        System.out.println(expenses+"\n");
                        optionsSelection(expenses);
                        break;
                    case 5:
                        searchExpenses(expenses);
                        optionsSelection(expenses);
                        break;
                    case 6:
                        closeApp();
                        break;
                    default:
                        System.out.println("You have made an invalid choice!");
                        break;
                }
            }
        }
    }
    
    private static void closeApp() {System.out.println("Closing your application... \nThank you!");}
    
    static int binarySearch(ArrayList<Integer> arrayList, int left, int right, int target) 
    { 
        if (right >= left) 
        { 
            int middle = left + (right - left) / 2; 
  
            if (arrayList.get(middle) == target)
            {
            	return middle;
            }
  
            if (arrayList.get(middle) > target)
            {
            	//search left side of list
            	return binarySearch(arrayList, left, middle - 1, target); 
            }
            else
            {
            	//search right side of list
            	return binarySearch(arrayList, middle + 1, right, target); 
            } 
        } 
        
        return -1; 
    } 
    
    static int exponentialSearch(ArrayList<Integer> arrayList, int leng, int target)
    {
    	if (arrayList.get(0) == target)
    	{
    		return 0;
    	}
    	
    	int i = 1;
    	while (i < leng && arrayList.get(i) <= target)
    	{
    		i = i * 2;
    	}

    	// Call binary search for the found range.
    	return binarySearch(arrayList, i/2,  Math.min(i, leng - 1), target);
    }
    
    private static void searchExpenses(ArrayList<Integer> arrayList) 
    {
    	//make sure list is sorted before searching
    	sortExpenses(arrayList);
    	System.out.println("EXPENSES HAVE BEEN SORTED\n");
        int leng = arrayList.size();
        System.out.println("Enter the expense you want to search:\t");
        @SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
        int target =  scan.nextInt();
        
        int result = exponentialSearch(arrayList, leng, target);
        if(result < 0)
        {
        	System.out.println("Expense was not found\n");
        }
        else
        {
        	System.out.println("Expense was found at position: " + result + "\n");
        }
    }
    
    static void merge(ArrayList<Integer> arrayList, int left, int middle, int right)
    {
        int n1 = middle - left + 1;
        int n2 = right - middle;
 
        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
        {
        	L[i] = arrayList.get(left + i);
        }
            
        for (int j = 0; j < n2; ++j)
        {
        	R[j] = arrayList.get(middle + 1 + j);
        }
            
        int i = 0;
        int j = 0;
        int k = left;
        
        while(i < n1 && j < n2) 
        {
            if(L[i] <= R[j]) 
            {
                arrayList.set(k, L[i]);
                i++;
            }
            else 
            {
                arrayList.set(k, R[j]);
                j++;
            }
            k++;
        }
 
      //Replacing elements of L[] into the arrayList
        while (i < n1) 
        {
        	arrayList.set(k, L[i]);
            i++;
            k++;
        }
 
        //Replacing elements of R[] into the arrayList
        while (j < n2) 
        {
        	arrayList.set(k, R[j]);
            j++;
            k++;
        }
    }

    static void sort(ArrayList<Integer> arrayList, int left, int right)
    {
        if(left < right) 
        {
            int middle = left + (right - left) / 2;
 
            //Divide array list
            sort(arrayList, left, middle);
            sort(arrayList, middle + 1, right);

            merge(arrayList, left, middle, right);
        }
    }

    private static void sortExpenses(ArrayList<Integer> arrayList) 
    {
        int arrlength =  arrayList.size();
        //Starts a merge sort
        sort(arrayList, 0, arrlength - 1);
    }
    
}
