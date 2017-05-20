import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class BottomUp_FastOrLowCalorie {
	//array to store the input 
	private static int diet[][]=new int[100][5];
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
		PrintWriter pw = new PrintWriter("table.txt");
		
		for(int i=1;i<=k;i++){
			//finding calories consumed for each choice
			f[i][1]=Math.max(Math.max(f[i-1][1]+diet[i][1],f[i-1][2]+diet[i][1]),f[i-1][3]+diet[i][1]);
			f[i][2]=Math.max(Math.max(f[i-1][1]+diet[i][2],f[i-1][2]+diet[i][2]),f[i-1][3]+diet[i][2]);
			f[i][3]=Math.max(f[i-1][1]+diet[i][3],f[i-1][2]+diet[i][3]);
			if(i==1){
				f[i][3]=0;
			}
			
			//writing optimal sequence to file
			pw.println(f[i][1]+" "+f[i][2]+" "+f[i][3]);
			if(f[i][1]>f[i][2] && f[i][1]>f[i][3]){
				seq[i]="Fasting";
			}
			else if(f[i][2]>f[i][1] && f[i][2]>f[i][3]){
				seq[i]="Low calorie";
			}
			else{
				seq[i]="High calorie";
				if(f[i-1][1]+diet[i][3]>f[i-1][2]+diet[i][3]){
					seqOld[i-1]=seq[i-1];
					seq[i-1]="Fasting";
				}
				else{
					seqOld[i-1]=seq[i-1];
					seq[i-1]="Low calorie";
				}
				int l=i-2;
				while(l>=0 && seqOld[i-1].equals("High calorie")){	
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
		}
		pw.close();
		
		//finding optimal value for the calories consumed
		int total=Math.max(Math.max(f[k][1],f[k][2]),f[k][3]);
		
		//displaying optimal sequence
		System.out.println("***Food Schedule***");
		for(int i=1;i<=k;i++){
			System.out.println("Day "+i+" - "+seq[i]);
		}
		
		//displaying optimal value
		System.out.println("Total calories = "+total);
	}
}
