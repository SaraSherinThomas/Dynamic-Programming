import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class RuntimeComparison {
	//array to store the input 
	private static int diet[][]=new int[100][5];
	private static int bottomUpCount=0;
	private static int topDownCount=0;
	private static int topDownMemoizedCount=0;
	private static int q=0;
	public static void main(String[] args) throws IOException {
		int k=1;
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
		
		bottomUp(diet,k);
		q=0;
		TopDown(diet, k);
		
		q=0;
		//array to store result of sub problems
		int r[]=new int[k+1];
		//initializing to 0
		for(int i=0;i<=k;i++){
			r[i]=0;
		}
		TopDownMemoized(diet, k, r);
		System.out.println("Runtime by Top Down Memoized method= "+topDownMemoizedCount);
		System.out.println("Runtime by Top Down method= "+topDownCount);
	}
	public static void bottomUp(int[][] diet,int k) throws IOException{
		
		int f[][] = new int[k+1][4];
		//array to store optimal sequence
		String seq[]=new String[k+1];
		String seqOld[]=new String[k+1];
		
		//setting row 0 and column 0 values to 0
		for(int i=0;i<4;i++){
			f[0][i]=0;
		}
		for(int i=0;i<=k;i++){
			f[i][0]=0;
		}
		
		//file to store the resultant table
		
		for(int i=1;i<=k;i++){
			bottomUpCount+=1;
			//finding calories consumed for each choice
			f[i][1]=Math.max(Math.max(f[i-1][1]+diet[i][1],f[i-1][2]+diet[i][1]),f[i-1][3]+diet[i][1]);
			f[i][2]=Math.max(Math.max(f[i-1][1]+diet[i][2],f[i-1][2]+diet[i][2]),f[i-1][3]+diet[i][2]);
			f[i][3]=f[i-1][1]+diet[i][3];
			if(i==1){
				f[i][3]=0;
			}
			
		}
		
		//finding optimal value for the calories consumed
		int total=Math.max(Math.max(f[k][1],f[k][2]),f[k][3]);
		
		//displaying optimal value
		System.out.println("Runtime by Bottom Up method= "+bottomUpCount);
	}
	public static int TopDown(int diet[][],int n){
		topDownCount+=1;
		if(n<1){
			return 0;
		}
		else{
			//finding optimal value for the calories consumed
			q = Math.max(Math.max(diet[n][1]+TopDown(diet, n-1), diet[n][2]+TopDown(diet, n-1)),diet[n][3]+diet[n-1][1]+TopDown(diet, n-2));
			
		}
		return q;
	}
	public static int TopDownMemoized(int diet[][],int n,int r[]){
		topDownMemoizedCount+=1;
		if(n>=0 && r[n]>0){
			//returning stored value of subproblem
			return r[n];
		}
		
		
		if(n<1){
			q = 0;
		}
		else{
			//finding optimal value for the calories consumed
			q = Math.max(Math.max(diet[n][1]+TopDownMemoized(diet, n-1,r), diet[n][2]+TopDownMemoized(diet, n-1,r)),diet[n][3]+diet[n-1][1]+TopDownMemoized(diet, n-2,r));
		}
		if(n>=0){
			r[n]=q;
		}
		
		return q;
	}
}
