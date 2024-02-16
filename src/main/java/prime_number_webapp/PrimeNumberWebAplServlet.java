package prime_number_webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 素数ウェブアプリのサーブレットクラス
 */
@WebServlet("/PrimeNumberWebAplServlet")
public class PrimeNumberWebAplServlet extends HttpServlet {

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

		// Content Typeを設定
		response.setContentType("text/html; charset=Shift_JIS");

		// 入力されたフォームデータを取得
		String item1 = request.getParameter("item1");

		PrintWriter out = response.getWriter();

		new Thread() {
			@Override
			public void run() {
				try {
					int from = 2;
					int to = 102;
					boolean primeNumberCheckFlg = true;
					while (primeNumberCheckFlg) {
						String primeNumberResult = primeNumberCheck(item1, "18.183.82.46", "aws-webapp/PrimeNumber",
								String.valueOf(from), String.valueOf(to));
						if (!primeNumberResult.equals("-")) {
							primeNumberCheckFlg = false;
							out.println("<p>素数：" + primeNumberResult + "</p>");
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
					boolean primeNumberCheckFlg = true;
					while (primeNumberCheckFlg) {
						String primeNumberResult = primeNumberCheck(item1, "35.78.185.41",
								"Aws-0.0.1-SNAPSHOT/PrimeNumber",
								String.valueOf(from), String.valueOf(to));
						if (!primeNumberResult.equals("-")) {
							primeNumberCheckFlg = false;
							out.println("<p>素数：" + primeNumberResult + "</p>");
						}
						from += 202;
						to += 202;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		//		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>");
		out.println("<p>その素数は・・・</p>");
		//		out.println("<p>素数：" + primeNumber + "</p>");
		out.println("</body></html>");
	}

	/**
	 * 指定された番目の素数を返却する。
	 * 
	 * @param number 何番目
	 * @param url1 計算サーバのホスト名 or IPアドレス
	 * @param url2 計算サーバのサブディレクトリ
	 * @param from 計算開始範囲
	 * @param to 計算終了範囲
	 * @return 求めた素数 または "-" を返却
	 * @throws IOException 
	 */
	private String primeNumberCheck(String number, String url1, String url2,
			String from, String to) throws IOException {
		String result = "";
		String primeNumberResult = "-";
		HttpURLConnection connection = null;
		BufferedReader in = null;
		JsonNode root = null;

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
				root = mapper.readTree(result);
				try {
					int num = Integer.parseInt(number);
					JsonNode primeNumberJsonResult = root.path("primeNumberList").get(num - 1);
					if (primeNumberJsonResult == null) {
						return primeNumberResult;
					}
					primeNumberResult = primeNumberJsonResult.asText();
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
			}

		} finally {
			// クローズ
			in.close();
			connection.disconnect();
		}

		return primeNumberResult;
	}
}
