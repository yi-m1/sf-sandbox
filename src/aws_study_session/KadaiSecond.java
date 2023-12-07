package aws_study_session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AWS勉強会活動の<br>
 * 課題2
 */
public class KadaiSecond {

	public static void main(String[] args) {
		KadaiSecond kadaisecond = new KadaiSecond();
		Scanner sc = new Scanner(System.in);
		System.out.print("求める素数の数を入力してください > ");
		int inputTarget = sc.nextInt();
		List<Integer> primeNumbers = kadaisecond.getPrimeNumber(inputTarget);
		System.out.println("素数: " + primeNumbers);
		//TODO System.out.println → ログ log4j 外部ライブラリを使用できると良い
		sc.close();
	}

	//TODO 後で、List を Set(順序修正してくれるクラスを使用) にするかな

	public List<Integer> getPrimeNumber(int inputTarget) {
		List<Integer> list = new ArrayList<>();
		int n = 2;
		int i = 0;
		int calcCount = 0; //TODO 計算量出力用 一時的

		// 2から標準入力から受け取った求める素数の数までを繰り返し処理
		while (list.size() != inputTarget) {
			// 素数判定
			for (i = 2; i < n; i++) {
				if (n % i == 0) {
					// 自身の数以外(自身より小さい自然数)で割り切れると素数ではないので、ループ終了
					break;
				}
				calcCount++;
			}
			if (n == i) {
				// どの数でも割り切れることなく、自身の数まできたときは素数
				list.add(n);
			}
			n++;
		}
		System.out.println("計算量: " + calcCount + " 回"); //TODO 一時的
		return list;
	}

}
