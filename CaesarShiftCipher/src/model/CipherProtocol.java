package model;

public class CipherProtocol {

	private String[] alpha = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
			  				  "n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private String[] ALPHA = {"A","B","C","D","E","F","G","H","I","J","K","L","M",
							  "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	StringBuilder resultText = new StringBuilder();
	String textSplit;

// 入力されたテキストのencode処理
	public void encode(Text text){
		String clearText = text.getClearText();
		int key = text.getKey();

		resultText = protocolLogic(clearText,key);

		// スコープに保存
		text.setEncodeText(resultText);
	}

// 入力されたテキストのdecode処理
	public void decode(Text text){
		String clearText = text.getClearText();
		int key = text.getKey();

		key = alpha.length - key;

		resultText = protocolLogic(clearText,key);

		// スコープに保存
		text.setDecodeText(resultText);
	}

// encodeとdecodeメソッドから送られてきた、textとkeyでresultText（変換後の文章）を作るメソッド
	public StringBuilder protocolLogic(String clearText,int key){

		for(int j = 0; j < clearText.length(); j++ ){
			// clearTextから一文字ずつ取り出す。
			textSplit = clearText.substring(j, j+1);

			if(textSplit.matches("[a-z]")){
				for(int i = 0; i < alpha.length; i++){

					// 切り出したtextSplitsをalpha[i]で比較。
					if(textSplit.equals(alpha[i])){

						// マッチした場合、alpha[i + key]かalpha[i + key-26]でstrに加える。
						if(i + key >= alpha.length){

							// keyの指定によって、通常のアルファベット順の表にに対して、変換先の表は右にずれるイメージ。
							// .ex) 通常のアルファベット    : ABC...Z
							//      key=1の変換先の表 : BCD...A
							resultText.append(alpha[(i + key)%26]);
						}else{
							resultText.append(alpha[i + key]);
						}
						break;
					// 切り出したtextSplitsが空白なら空白をstrに加える。
					}
				}

			}else if(textSplit.matches("[A-Z]")){
				for(int i = 0; i < ALPHA.length; i++){
					// 切り出したtextSplitsをalpha[i]で比較。
					if(textSplit.equals(ALPHA[i])){
						// マッチした場合、alpha[i + key]かalpha[i + key-26]でstrに加える。
						if(i + key >= ALPHA.length){
							resultText.append(ALPHA[(i + key)%26]);
						}else{
							resultText.append(ALPHA[i + key]);
						}
						break;
					}
				}

			}else if(textSplit.matches("\n")){

				// 切り出したtextSplitsが改行なら改行をstrに加える。
				// \r\nやSystem.lineSeparator()をやってみたが、反応なし（.appendが認識していない？）。
				resultText.append("<br>");

			}else{
				// 切り出したtextSplitsが空白なら空白をstrに加える。
				resultText.append(textSplit);
			}

//str.append(textSplit);
		}
		return resultText;
	}


}
