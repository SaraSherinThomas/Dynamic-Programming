import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class TopDownMemoizedMain {
	public static int k=1,q=0;
	//array to store optimal sequence of choices
	private static String seq[];
	private static String seqOld[];
	//array to store the input 
	private static int diet[][]=new int[100][5];
	public static void main(String[] args) throws IOException {
		//reading input file
		FileInputStream in = new FileInputStream(new File("DietMenu.txt"));
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		String s =reader.readLine();
		while(s!=null){
			String temp[]= s.split(" ");
			for(int i=0;i<temp.length;i++){
				diet[k][i] =Integer.parseInt(temp[i]);
			}	
			k++;
			s =reader.readLine();
		}
		k=k-1;
		seq=new String[k+1];
		seqOld=new String[k+1];
		//array to store result of sub problems
		int r[]=new int[k+1];
		//initializing to 0
		for(int i=0;i<=k;i++){
			r[i]=0;
		}
		//method call for the top down solution for scheduling the food
		int result=FoodScheduleWithSeq(diet,k,r);
		//displaying optimal sequence
		System.out.println("***Food Schedule***");
		for(int i=1;i<=k;i++){
			System.out.println("Day "+i+" - "+seq[i]);
		}
		//displaying optimal value
		System.out.println("Total calories = "+result);
	}
	
	//recursive method to find the optimal sequence and optimal value
	public static int FoodScheduleWithSeq(int diet[][],int n,int r[]){
		if(n>=0 && r[n]>0){
			//returning stored value of subproblem
			return r[n];
		}
		if(n<1){
			q = 0;
		}
		else{
			//finding calories consumed for each choice and finding the optimal value
			if(diet[n][1]+FoodScheduleWithSeq(diet, n-1,r)>diet[n][2]+FoodScheduleWithSeq(diet, n-1,r)){
				if(diet[n][1]+FoodScheduleWithSeq(diet, n-1,r)>diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2,r)){
					q=diet[n][1]+FoodScheduleWithSeq(diet, n-1,r);
					seq[n]="Fasting";
				}
				else{
					q=diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2,r);
					seq[n]="High Calorie";
					seqOld[n-1]=seq[n-1];
					seq[n-1]="Fasting";
					int l=n-2;
					while(l>=0){	
						String t = seq[l];
						seq[l]=seqOld[l];
						seqOld[l]=t;
						l=l-1;
					}
					
					
				}
			}
			else if(diet[n][2]+FoodScheduleWithSeq(diet, n-1,r)<diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2,r)){
				q=diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2,r);
				seq[n]="High Calorie";
				seqOld[n-1]=seq[n-1];
				seq[n-1]="Fasting";
				int l=n-2;
				while(l>=0){	
					if(seq[l]!=null && seqOld[l]!=null){
						String t = seq[l];
						seq[l]=seqOld[l];
						seqOld[l]=t;
					}
					else{
						break;
					}
					l=l-1;
				}
			}
			else{
				q=diet[n][2]+FoodScheduleWithSeq(diet, n-1,r);
				seq[n]="Low Calorie";
			}
		}
		
		//storing solutions of subproblems
		if(n>=0){
			r[n]=q;
		}
		
		return q;
		
	}
	
	//recursive method to find the optimal value
	public static int FoodSchedule(int diet[][],int n,int r[]){
		if(n>=0 && r[n]>0){
			//returning stored value of subproblem
			return r[n];
		}
		if(n<1){
			q = 0;
		}
		else{
			q = Math.max(Math.max(diet[n][1]+FoodSchedule(diet, n-1,r), diet[n][2]+FoodSchedule(diet, n-1,r)),diet[n][3]+diet[n-1][1]+FoodSchedule(diet, n-2,r));
		}
		if(n>=0){
			r[n]=q;
		}
		
		return q;
	}
}
