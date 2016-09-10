package org.yihang.core.essence;

public class TestFibonacci {
	
	public static int f(int n){
		if(n==1){
			return 0;
		}
		if(n==2) {
			return 1;
		}
		return f(n-1) + f(n-2);
	}
	
	public static void main(String[] args) {
		for(int i = 1;i <= 10;i++){
			System.out.print(f(i) + " ");
		}
	}

}
