package prime_number_webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 素数ウェブアプリのサーブレットクラス
 */
@WebServlet("/PrimeNumberWebAplServlet")
public class PrimeNumberWebAplServlet extends HttpServlet {

	static boolean primeNumberSearchFlg = true;

	/**
	 * フォームからデータを受け取る。<br>
	 * 結果をブラウザに返却する。
	 * 
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 小さい素数から何番目と検索する為、
		// 重複なし、昇順になる TreeSet を使用
		TreeSet<Integer> primeNumbers = new TreeSet<Integer>();

		setPrimeNumberSearchFlg(true);

		// Content Typeを設定
		response.setContentType("text/html; charset=Shift_JIS");

		PrintWriter out = response.getWriter();

		// 入力されたフォームデータを取得
		String item1 = request.getParameter("item1");
//		try {
		int num = Integer.parseInt(item1);
//		} catch (NumberFormatException e) {
//			System.out.println(e);
//		}

		new Thread() {
			@Override
			public void run() {
				try {
					int from = 2;
					int to = 102;
					while (primeNumberSearchFlg) {
						ArrayNode primeNumberResults = getPrimeNumber("172.31.44.222", "aws-webapp/PrimeNumber",
								String.valueOf(from), String.valueOf(to));
						if (primeNumberResults == null) {
							continue;
						}
						for (Object primeNumberResult : primeNumberResults) {
							primeNumbers.add(Integer.parseInt(primeNumberResult.toString()));
						}
						if (primeNumbers.size() >= num * 2) {
							setPrimeNumberSearchFlg(false);
						}
						from += 202;
						to += 202;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				try {
					int from = 103;
					int to = 203;
					while (primeNumberSearchFlg) {
						ArrayNode primeNumberResults = getPrimeNumber("172.31.6.103", "Aws-0.0.1-SNAPSHOT/PrimeNumber",
								String.valueOf(from), String.valueOf(to));
						if (primeNumberResults == null) {
							continue;
						}
						for (Object primeNumberResult : primeNumberResults) {
							primeNumbers.add(Integer.parseInt(primeNumberResult.toString()));
						}
						if (primeNumbers.size() >= num * 2) {
							setPrimeNumberSearchFlg(false);
						}
						from += 202;
						to += 202;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		while (primeNumberSearchFlg) {
			out.println("<html><head></head><body>");
			if (primeNumbers.size() >= num * 2) {
				if (num < 1) {
					out.println("<p style=\"font-weight: bold;\"><font size=\"+4\">1以上を入力してください。</font></p>");
					out.println("</body></html>");
				} else {
					int count = 1;
					for (Integer primeNumber : primeNumbers) {
						if (count == num) {
							out.println("<p><font size=\"+3\">その素数は・・・</font></p>");
							out.println("<p style=\"font-weight: bold;\"><font size=\"+4\">"
									+ primeNumber + "</font></p>");
							out.println("</body></html>");
							break;
						}
						count++;
					}
				}
				setPrimeNumberSearchFlg(false);
			}
		}
	}

	/**
	 * 指定された範囲の素数を返却する。
	 * 
	 * @param url1 計算サーバのホスト名 or IPアドレス
	 * @param url2 計算サーバのサブディレクトリ
	 * @param from 計算開始範囲
	 * @param to 計算終了範囲
	 * @return Json形式の配列にした素数
	 * @throws IOException 
	 */
	private ArrayNode getPrimeNumber(String url1, String url2,
			String from, String to) throws IOException {
		String result = "";
		HttpURLConnection connection = null;
		BufferedReader in = null;
		ArrayNode primeNumberJsonResult = null;

		try {
			String getUrl = "http://" + url1 + ":8080/" + url2 + "?from=" + from + "&to=" + to;
			// URL に対して openConnection メソッドを呼び出す、接続オブジェクトを生成
			URL url = new URL(getUrl);
			connection = (HttpURLConnection) url.openConnection();
			//リクエストのメソッドを指定
			connection.setRequestMethod("GET");
			//通信開始
			connection.connect();
			// レスポンスコードを判断する、OKであれば、進める
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 通信に成功した
				// テキストを取得する
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String tmp = "";

				while ((tmp = in.readLine()) != null) {
					result += tmp;
				}

				// ObjectMapperを利用し JSON文字列 をJavaオブジェクトに変換する
				ObjectMapper mapper = new ObjectMapper();
				primeNumberJsonResult = (ArrayNode) mapper.readTree(result);
			}

		} finally {
			// クローズ
			if (in != null) {
				in.close();
			}
			connection.disconnect();
		}

		return primeNumberJsonResult;
	}

	private static void setPrimeNumberSearchFlg(boolean flg) {
		primeNumberSearchFlg = flg;
	}
}
