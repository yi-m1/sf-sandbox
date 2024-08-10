package aws_study_session_2024;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 2024年度AWS勉強会活動の<br>
 * 課題1<br>
 * -じゃんけんをするプログラムを作る（１）-
 */
public class KadaiFirst2024 {
	private static final Logger LOGGER = LogManager.getLogger(KadaiFirst2024.class);
	private static final String[] HANDS = { "グー", "チョキ", "パー" };
	private static final Scanner SCANNER = new Scanner(System.in);
	private static final Random RANDOM = new Random();

	public KadaiFirst2024() {
	};

	public static void main(String[] args) {
		LOGGER.info("じゃんけんゲームを開始します。");

		KadaiFirst2024 kF2024 = new KadaiFirst2024();

		boolean isPlayAgain;
		do {
			String userHand = kF2024.getUserHand();
			String computerHand = kF2024.getComputerHand();

			LOGGER.info("ユーザーの手: " + userHand);
			LOGGER.info("コンピュータの手: " + computerHand);

			String result = kF2024.getJankenOutcome(userHand, computerHand);
			LOGGER.info("対戦結果: " + result);

			kF2024.saveResult(userHand, computerHand, result);

			isPlayAgain = kF2024.askToPlayAgain();
		} while (isPlayAgain);

		kF2024.displayPastResults();

		LOGGER.info("じゃんけんゲームを終了します。");
		SCANNER.close();
	}

	/**
	 * ユーザーに入力された手を返却する
	 * @return ユーザーの手（グー、チョキ、パー）
	 */
	private String getUserHand() {
		String userHand = "";
		while (true) {
			LOGGER.info("ユーザーが手を入力");
			LOGGER.info("あなたの手を選んでください (グー or チョキ or パー): ");
			userHand = SCANNER.nextLine();

			if ("グー".equals(userHand) || "チョキ".equals(userHand) || "パー".equals(userHand)) {
				LOGGER.info("有効な入力です: " + userHand);
				break;
			} else {
				LOGGER.warn("無効な入力です: " + userHand);
			}
		}
		return userHand;
	}

	/**
	 * ランダムに選ばれたコンピュータの手を返却する
	 * @return コンピュータの手（グー、チョキ、パー）
	 */
	private String getComputerHand() {
		int computerIndex = RANDOM.nextInt(3);
		String computerHand = HANDS[computerIndex];
		LOGGER.info("コンピュータの手です: " + computerHand);
		return computerHand;
	}

	/**
	 * ユーザーとコンピュータの手を比較し勝敗を判定する
	 * @param userHand ユーザーの手
	 * @param computerHand コンピュータの手
	 * @return 勝敗結果の文字列
	 */
	private String getJankenOutcome(String userHand, String computerHand) {
		if (userHand.equals(computerHand)) {
			return "あいこ！";
		} else if (("グー".equals(userHand) && "チョキ".equals(computerHand)) ||
				("チョキ".equals(userHand) && "パー".equals(computerHand)) ||
				("パー".equals(userHand) && "グー".equals(computerHand))) {
			return "勝ち！";
		} else {
			return "負け！";
		}
	}

	/**
	 * 対戦結果をCSVファイルに保存
	 * @param userHand ユーザーの手
	 * @param computerHand コンピュータの手
	 * @param result 勝敗結果
	 */
	private void saveResult(String userHand, String computerHand, String result) {
		try (FileWriter fw = new FileWriter("janken_results.csv", true);
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
			pw.println(userHand + "," + computerHand + "," + result);
			LOGGER.info("対戦結果をCSVファイルに保存しました。");
		} catch (IOException e) {
			LOGGER.error("ファイル書き込み中にエラーが発生しました", e);
		}
	}

	/**
	 * ユーザーに再プレイの意思を確認する
	 * @return true: 再プレイする, false: やめる
	 */
	private boolean askToPlayAgain() {
		LOGGER.info("ユーザーに再プレイの意思を確認します。");
		LOGGER.info("もう一度プレイしますか？ (はい/いいえ): ");
		String response = SCANNER.nextLine();
		boolean isPlayAgain = response.equalsIgnoreCase("はい");
		LOGGER.info("再プレイの意思: " + (isPlayAgain ? "はい" : "いいえ"));
		return isPlayAgain;
	}

	/**
	 * 過去の対戦結果を表示する
	 */
	private void displayPastResults() {
		LOGGER.info("過去の対戦結果を表示します。");
		try (FileReader fr = new FileReader("janken_results.csv");
				BufferedReader br = new BufferedReader(fr)) {
			String line;
			while ((line = br.readLine()) != null) {
				LOGGER.info(line);
			}
		} catch (IOException e) {
			LOGGER.error("ファイル読み込み中にエラーが発生しました", e);
		}
	}

}
