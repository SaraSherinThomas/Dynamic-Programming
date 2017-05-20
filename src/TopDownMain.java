import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class TopDownMain {
	public static int k=1,q=0;;
	//array to store the input 
	private static int diet[][]=new int[100][5];
	//array to store optimal sequence of choices
	private static String seq[];
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
		//array to store result of sub problems
		seq=new String[k+1];
		//method call for the top down solution for scheduling the food
		int result=FoodScheduleWithSeq(diet,k);
		
		//displaying optimal sequence
		System.out.println("***Food Schedule***");
		for(int i=1;i<=k;i++){
			System.out.println("Day "+i+" - "+seq[i]);
		}
		//displaying optimal value
		System.out.println("Total calories = "+result);
		
	}
	
	//recursive method to find the optimal sequence and optimal value
	public static int FoodScheduleWithSeq(int diet[][],int n){
		if(n<1){
			return 0;
		}
		else{
			//finding calories consumed for each choice and finding the optimal value
			if(diet[n][1]+FoodScheduleWithSeq(diet, n-1)>diet[n][2]+FoodScheduleWithSeq(diet, n-1)){
				if(diet[n][1]+FoodScheduleWithSeq(diet, n-1)>diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2)){
					q=diet[n][1]+FoodScheduleWithSeq(diet, n-1);
					seq[n]="Fasting";
				}
				else{
					q=diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2);
					seq[n]="High Calorie";
					seq[n-1]="Fasting";
				}
			}
			else if(diet[n][2]+FoodScheduleWithSeq(diet, n-1)<diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2)){
				q=diet[n][3]+diet[n-1][1]+FoodScheduleWithSeq(diet, n-2);
				seq[n]="High Calorie";
				seq[n-1]="Fasting";
			}
			else{
				q=diet[n][2]+FoodScheduleWithSeq(diet, n-1);
				seq[n]="Low Calorie";
			}
		}
		return q;
	}
	
	//recursive method to find the optimal sequence and optimal value
	public static int FoodSchedule(int diet[][],int n){
		if(n<1){
			return 0;
		}
		else{
			q = Math.max(Math.max(diet[n][1]+FoodSchedule(diet, n-1), diet[n][2]+FoodSchedule(diet, n-1)),diet[n][3]+diet[n-1][1]+FoodSchedule(diet, n-2));
			
		}
		return q;
	}
}
